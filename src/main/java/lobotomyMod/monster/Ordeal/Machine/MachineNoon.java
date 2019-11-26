package lobotomyMod.monster.Ordeal.Machine;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class MachineNoon extends AbstractOrdealMonster {
    public static final String ID = "MachineNoon";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("MachineNoon");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private boolean cool;

    public MachineNoon(float x, float y) {
        super(NAME, "MachineNoon", 60, 0.0F, -20.0F, 250.0F, 250.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Machine/Noon/machine_Noon.atlas", "lobotomyMod/images/monsters/Ordeal/Machine/Noon/machine_Noon.json", 1.9F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_01", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
        this.stateData.setMix("Default", "CoolDown_Start", 0.9F);
        this.stateData.setMix("CoolDown_Start", "CoolDown", 0.9F);
        this.stateData.setMix("CoolDown", "Start", 0.9F);
        this.damage.add(new DamageInfo(this, 2));
        this.flipHorizontal = true;
        this.cool = false;
        this.setHp(60, 72);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.cool = true;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if(this.cool){
            if(AbstractDungeon.getMonsters().monsters.lastIndexOf(this) == 0){
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
            }
            else if(AbstractDungeon.getMonsters().monsters.lastIndexOf(this) == 1){
                this.changeState("COOL");
                setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
            }
            else if(AbstractDungeon.getMonsters().monsters.lastIndexOf(this) == 2){
                setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, 9, true);
            }
        }
        else if (lastMove((byte)0)){
            setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        }
        else if (lastMove((byte)1)){
            setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
        }
        else {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, 9, true);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 0){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                for(int i = 0; i < 8; i ++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            }, 0.4F));
        }
        else if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "COOL"));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "START"));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        this.cool = false;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            if(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof MachineNight){
                AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(1, 3, true));
                return;
            }
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(1, 2, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "COOL":
                this.state.setAnimation(0, "CoolDown_Start", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "CoolDown", true, 0.0F);
                break;
            case "START":
                this.state.setAnimation(0, "Start", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
