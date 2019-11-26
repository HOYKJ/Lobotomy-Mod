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
public class MachineDawn extends AbstractOrdealMonster {
    public static final String ID = "MachineDawn";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("MachineDawn");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public MachineDawn(float x, float y) {
        super(NAME, "MachineDawn", 30, 0.0F, 0.0F, 200.0F, 300.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Machine/Dawn/Machine_Dawn.atlas", "lobotomyMod/images/monsters/Ordeal/Machine/Dawn/Machine_Dawn.json", 2.2F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Standing", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Standing", "Attack_01", 0.9F);
        this.stateData.setMix("Standing", "Attack_02", 0.9F);
        this.stateData.setMix("Standing", "Dead", 0.9F);
        this.damage.add(new DamageInfo(this, 5));
        this.setHp(30, 36);
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            if(MathUtils.randomBoolean()) {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK1"));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            }
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }, 0.7F));
        }
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
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(1, 1, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK1":
                this.state.setAnimation(0, "Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
