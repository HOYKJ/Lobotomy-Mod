package lobotomyMod.monster.Ordeal.Bug;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.Ordeal.Machine.MachineNoon;
import lobotomyMod.relic.AtMidnight;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class BugNight extends AbstractOrdealMonster {
    public static final String ID = "BugNight";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BugNight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public int left;
    private float[] POSX;
    private float[] POSY;
    private BugDawn[] bugs = new BugDawn[3];

    public BugNight(float x, float y) {
        super(NAME, "BugNight", 200, 0.0F, -10.0F, 340.0F, 180.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Bug/Night/BugDusk.atlas", "lobotomyMod/images/monsters/Ordeal/Bug/Night/BugDusk.json", 1.5F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Move", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Move", "Eat", 0.9F);
        this.stateData.setMix("Move", "Spawn", 0.9F);
        this.damage.add(new DamageInfo(this, 40));
        this.flipHorizontal = true;
        this.left = 0;
        this.POSX = new float[]{this.drawX - 100, this.drawX - 300, this.drawX - 500};
        this.POSY = new float[]{this.drawY, this.drawY + 20, this.drawY};
        for(int i = 0; i < this.POSX.length; i ++){
            this.POSX[i] -= Settings.WIDTH * 0.75F / Settings.scale;
            this.POSY[i] -= AbstractDungeon.floorY / Settings.scale;
        }
    }

    public BugNight(float x, float y, int left) {
        this(x, y);
        this.left = left;
        if(left == 1){
            this.flipHorizontal = false;
            this.POSX = new float[]{this.drawX + 350, this.drawX + 540, this.drawX + 720};
            for(int i = 0; i < this.POSX.length; i ++){
                this.POSX[i] -= Settings.WIDTH * 0.75F / Settings.scale;
            }
        }
        else {
            this.POSX = new float[]{this.drawX - 10, this.drawX - 200, this.drawX - 380};
            for(int i = 0; i < this.POSX.length; i ++){
                this.POSX[i] -= Settings.WIDTH * 0.75F / Settings.scale;
            }
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ShiftingPower(this)));
    }

    protected void getMove(int num) {
        boolean flag = true;
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster instanceof BugDawn && !monster.isDying){
                flag = false;
                break;
            }
        }
        if(flag){
            setMove((byte) 0, Intent.UNKNOWN);
        }
        else {
            setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 0){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SPAWN"));
//            for(int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i --){
//                if(AbstractDungeon.getCurrRoom().monsters.monsters.get(i) == this){
//                    continue;
//                }
//                AbstractDungeon.getCurrRoom().monsters.monsters.remove(i);
//            }
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                if(this.bugs[0] == null || this.bugs[0].isDeadOrEscaped()) {
                    this.bugs[0] = new BugDawn(this.POSX[0], this.POSY[0], this.left);
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.bugs[0], false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                }
            }, 1.4F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                if(this.bugs[1] == null || this.bugs[1].isDeadOrEscaped()) {
                    this.bugs[1] = new BugDawn(this.POSX[1], this.POSY[1], this.left);
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.bugs[1], false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                }
            }, 0.5F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                if(this.bugs[2] == null || this.bugs[2].isDeadOrEscaped()) {
                    this.bugs[2] = new BugDawn(this.POSX[2], this.POSY[2], this.left);
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.bugs[2], false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                }
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            }, 0.4F));
        }
        else if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new VampireDamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10), 10));
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            if(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof BugMidnight){
                AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(2, 4, true));
                CardCrawlGame.screenShake.rumble(4.0F);
                onBossVictoryLogic();
                onFinalBossVictoryLogic();
                return;
            }
            AbstractDungeon.getCurrRoom().addRelicToRewards(new AtMidnight());
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(2, 3, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Eat", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Move", true, 0.0F);
                break;
            case "SPAWN":
                this.state.setAnimation(0, "Spawn", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Move", true, 0.0F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
