package lobotomyMod.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.vfx.YinLaserEffect;
import lobotomyMod.card.uncommonCard.Yin;
import lobotomyMod.power.YinYangPower;
import lobotomyMod.relic.Yang;
import lobotomyMod.vfx.YinYangMergeEffect;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class YinMonster extends AbstractMonster {
    public static final String ID = "YinMonster";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("YinMonster");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private float vX;
    public Bone eye;
    public boolean canDie;

    public YinMonster(float x, float y) {
        super(NAME, "YinMonster", 50, 60.0F, 0.0F, 400.0F, 160.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Yin/skeleton.atlas", "lobotomyMod/images/monsters/Yin/skeleton.json", 1.75F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Fish_Move", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Fish_Move", "Attack", 0.9F);
        this.stateData.setMix("Fish_Move", "Dead", 0.9F);
        this.stateData.setMix("Dead", "Dead_cool", 0.9F);
        this.stateData.setMix("Dead_cool", "Dead_Revive", 0.9F);
        this.stateData.setMix("Dead_cool", "Dead_Real", 0.9F);
        this.eye = this.skeleton.findBone("bone3");
        this.damage.add(new DamageInfo(this, 12));
        this.canDie = false;
        if(Settings.FAST_MODE){
            this.vX = -Settings.WIDTH / 100.0F;
        }
        else {
            this.vX = -Settings.WIDTH / 200.0F;
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new YinYangPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 4), 4));
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new YinLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY,
                        this.skeleton, this.eye), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }, 0.6F));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REVIVE"));
            this.halfDead = false;
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new YinYangPower(this)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 4), 4));
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
                if ((m.id.equals("YangMonster")) && (!m.halfDead)) {
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
                        if (m instanceof YangMonster) {
                            ((YangMonster) m).canDie = true;
                            m.die();
                            AbstractDungeon.effectList.add(new LatterEffect(()->{
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(m.hb.cX, m.hb.cY,  new Yang());
                            }, 1.0F));
                        }
                    }
                    this.die();
                    AbstractDungeon.effectList.add(new LatterEffect(()->{
                        AbstractDungeon.player.hand.addToHand(new Yin());
                    }, 1.0F));
                }, 1.4F));
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
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Fish_Move", true, 0.0F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Dead_cool", true, 0.0F);
                break;
            case "DIEREAL":
                this.state.setAnimation(0, "Dead_Real", false);
                this.state.setTimeScale(1F);
                break;
            case "REVIVE":
                this.state.setAnimation(0, "Dead_Revive", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Fish_Move", true, 0.0F);
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        if((!this.halfDead) && (!this.isDying)) {
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof YangMonster) {
                    if (this.drawX - m.drawX <= 200) {
                        if(!m.halfDead){
                            this.canDie = true;
                            this.die();
                            this.hideHealthBar();
                            this.dispose();
                            ((YangMonster) m).canDie = true;
                            m.die();
                            m.hideHealthBar();
                            m.dispose();
                            AbstractDungeon.topLevelEffects.add(new YinYangMergeEffect());
                        }
                    }
                    else {
                        this.drawX += this.vX * Gdx.graphics.getDeltaTime();
                    }
                }
            }
        }
    }
}
