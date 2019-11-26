package lobotomyMod.monster.Ordeal.Machine;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.unique.CreateMachineAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.relic.AtMidnight;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class MachineNight extends AbstractOrdealMonster {
    public static final String ID = "MachineNight";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("MachineNight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public MachineNight(float x, float y) {
        super(NAME, "MachineNight", 500, 110.0F, -10.0F, 400.0F, 300.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Machine/Night/machineDusk.atlas", "lobotomyMod/images/monsters/Ordeal/Machine/Night/machineDusk.json", 1.5F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Creating", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.changeState("CREATING");
    }

    protected void getMove(int num) {
        if (lastMove((byte)0)){
            setMove((byte) 1, Intent.UNKNOWN);
        }
        else{
            setMove((byte) 0, Intent.UNKNOWN);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 0){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CREATE"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new CreateMachineAction());
                AbstractDungeon.actionManager.addToTop(new CreateMachineAction());
            }, 1.1F));
        }
        else if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CREATING"));
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 75));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(1, 3, true));
        }
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        AbstractDungeon.getCurrRoom().addRelicToRewards(new AtMidnight());
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "CREATING":
                this.state.setAnimation(0, "Creating", false);
                this.state.setTimeScale(1F);
                break;
            case "CREATE":
                this.state.setAnimation(0, "Create", false);
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
