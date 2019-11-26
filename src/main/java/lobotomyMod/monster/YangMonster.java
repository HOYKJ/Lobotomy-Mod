package lobotomyMod.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.uncommonCard.Yin;
import lobotomyMod.power.YangHealPower;
import lobotomyMod.power.YinYangPower;
import lobotomyMod.relic.ApostleMask;
import lobotomyMod.relic.Yang;
import lobotomyMod.vfx.YinYangMergeEffect;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class YangMonster extends AbstractMonster {
    public static final String ID = "YangMonster";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("YangMonster");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private float vX;
    public boolean canDie;

    public YangMonster(float x, float y) {
        super(NAME, "YangMonster", 50, 0.0F, -20.0F, 400.0F, 160.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Yang/skeleton.atlas", "lobotomyMod/images/monsters/Yang/skeleton.json", 1.75F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "move", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("move", "dead", 0.9F);
        this.stateData.setMix("dead", "dead_cool", 0.9F);
        this.stateData.setMix("dead_cool", "dead_revive", 0.9F);
        this.stateData.setMix("dead_cool", "dead_real", 0.9F);
        this.damage.add(new DamageInfo(this, 12));
        this.flipHorizontal = true;
        this.canDie = false;
        if(Settings.FAST_MODE){
            this.vX = Settings.WIDTH / 100.0F;
        }
        else {
            this.vX = Settings.WIDTH / 200.0F;
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new YinYangPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new YangHealPower(this, 3), 3));
    }

    protected void getMove(int num) {
        setMove((byte) 0, Intent.NONE);
    }

    public void takeTurn() {
        if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REVIVE"));
            this.halfDead = false;
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new YinYangPower(this)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new YangHealPower(this, 3), 3));
            LobotomyMod.logger.info("apply power done");
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if ((this.currentHealth <= 0) && (!this.halfDead))
        {
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DIE"));
            this.halfDead = true;
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                r.onMonsterDeath(this);
            }
            this.powers.clear();

            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((m.id.equals("YinMonster")) && (!m.halfDead)) {
                    allDead = false;
                }
            }
            if (!allDead)
            {
                if (this.nextMove != 2)
                {
                    setMove((byte)2, AbstractMonster.Intent.UNKNOWN);
                    createIntent();
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)2, AbstractMonster.Intent.UNKNOWN));
                }
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    this.canDie = true;
                    this.halfDead = false;
                    for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        if (m instanceof YinMonster) {
                            ((YinMonster) m).canDie = true;
                            m.die();
                            AbstractDungeon.effectList.add(new LatterEffect(()->{
                                AbstractDungeon.player.hand.addToHand(new Yin());
                            }, 1.0F));
                        }
                    }
                    this.die();
                    AbstractDungeon.effectList.add(new LatterEffect(()->{
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.hb.cX, this.hb.cY,  new Yang());
                    }, 1.0F));
                }, 0.33F));
            }
        }
    }

    public void die() {
        if(this.canDie) {
            super.die();
            this.changeState("DIEREAL");
            this.deathTimer += 1.5F;
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "DIE":
                this.state.setAnimation(0, "dead", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "dead_cool", true, 0.0F);
                break;
            case "DIEREAL":
                this.state.setAnimation(0, "dead_real", false);
                this.state.setTimeScale(1F);
                break;
            case "REVIVE":
                this.state.setAnimation(0, "dead_revive", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "move", true, 0.0F);
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        if((!this.halfDead) && (!this.isDying)) {
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof YinMonster) {
                    if (m.drawX - this.drawX > 200) {
                        this.drawX += this.vX * Gdx.graphics.getDeltaTime();
                    }
                }
            }
        }
    }
}
