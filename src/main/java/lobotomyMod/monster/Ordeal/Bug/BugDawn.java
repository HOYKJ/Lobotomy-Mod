package lobotomyMod.monster.Ordeal.Bug;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.Ordeal.Machine.MachineNight;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class BugDawn extends AbstractOrdealMonster {
    public static final String ID = "BugDawn";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BugDawn");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public BugDawn(float x, float y) {
        super(NAME, "BugDawn", 20, 0.0F, -20.0F, 160.0F, 100.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Bug/Dawn/Bug1.atlas", "lobotomyMod/images/monsters/Ordeal/Bug/Dawn/Bug1.json", 2.2F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Move", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Move", "Attack", 0.9F);
        this.stateData.setMix("Move", "Dead", 0.9F);
        this.damage.add(new DamageInfo(this, 6));
        this.flipHorizontal = true;
        this.setHp(18, 22);
    }

    public BugDawn(float x, float y, int left) {
        this(x, y);
        if(left == 1){
            this.flipHorizontal = false;
        }
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new VampireDamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.4F));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            if(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof BugNight){
                AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(2, 3, true));
                return;
            }
            if(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof BugMidnight){
                AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(2, 4, true));
                CardCrawlGame.screenShake.rumble(4.0F);
                onBossVictoryLogic();
                onFinalBossVictoryLogic();
                return;
            }
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(2, 1, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
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
