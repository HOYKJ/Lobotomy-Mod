package lobotomyMod.monster.Ordeal.Machine;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.LastHelixLaserEffect;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class MachineMidnight extends AbstractOrdealMonster {
    public static final String ID = "MachineMidnight";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("MachineMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public MachineMidnight(float x, float y) {
        super(NAME, "MachineMidnight", 2200, 0.0F, -24.0F, 720.0F, 460.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Machine/Midnight/machine_Midnight.atlas", "lobotomyMod/images/monsters/Ordeal/Machine/Midnight/machine_Midnight.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(1.3F);
        this.state.addAnimation(0, "Exterminate", false, 0.0F).setTimeScale(Settings.FAST_MODE? 0.6F: 0.4F);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            AbstractDungeon.effectsQueue.add(new LastHelixLaserEffect(this.skeleton, this.skeleton.findBone("bone"), this));
        }, 8.3F));
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.UNKNOWN);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 200));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(1, 4, true));
        CardCrawlGame.screenShake.rumble(4.0F);
        onBossVictoryLogic();
        onFinalBossVictoryLogic();
        for(AbstractGameEffect effect : AbstractDungeon.effectList){
            if(effect instanceof LastHelixLaserEffect){
                effect.isDone = true;
            }
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "RESTART":
                this.state.addAnimation(0, "ReStart", false, 0.0F);
                this.state.addAnimation(0, "Exterminate", false, 0.0F).setTimeScale(Settings.FAST_MODE? 0.6F: 0.4F);
                AbstractDungeon.effectsQueue.add(new LatterEffect(()->{
                    AbstractDungeon.effectsQueue.add(new LastHelixLaserEffect(this.skeleton, this.skeleton.findBone("bone"), this));
                }, 5.0F));
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
