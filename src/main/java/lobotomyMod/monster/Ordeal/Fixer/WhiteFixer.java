package lobotomyMod.monster.Ordeal.Fixer;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.Ordeal.Bug.BugNight;
import lobotomyMod.power.MutekiPower;
import lobotomyMod.vfx.WhiteFixerFogCore;
import lobotomyMod.vfx.WhiteFixerFogEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class WhiteFixer extends AbstractFixer {
    public static final String ID = "WhiteFixer";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("WhiteFixer");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns;

    public WhiteFixer(float x, float y) {
        super(NAME, "WhiteFixer", 200, 0.0F, -20.0F, 180.0F, 270.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Fixer/White/body.atlas", "lobotomyMod/images/monsters/Ordeal/Fixer/White/body.json", 3.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_01", 0.1F);
        this.stateData.setMix("Default", "Attack_02", 0.1F);
        this.stateData.setMix("Default", "Pray_Start", 0.1F);
        this.stateData.setMix("Pray_Start", "Pray", 0.1F);
        this.stateData.setMix("Pray", "Pray_End", 0.1F);
        this.stateData.setMix("Default", "Dead", 0.1F);
        this.damage.add(new DamageInfo(this, 8));
        this.damage.add(new DamageInfo(this, 4));
    }

    protected void getMove(int num) {
        if(this.halfDead){
            return;
        }
        switch (this.turns){
            case 0:
                setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                break;
            case 1:
                setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base, 3, true);
                break;
            case 2:
                setMove((byte) 3, Intent.UNKNOWN);
                break;
        }
        this.turns ++;
        if(this.turns > 2){
            this.turns = 0;
        }
        //setMove((byte) 3, Intent.UNKNOWN);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.effectList.add(new WhiteFixerFogCore(this.skeleton, this.skeleton.findBone("Effect"), this.skeleton.findBone("Rweapon"), true));
            }, 3.53F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.14F));
        }
        else if(this.nextMove == 2){
            WhiteFixerFogCore e = new WhiteFixerFogCore(this.skeleton, this.skeleton.findBone("Effect"), this.skeleton.findBone("Rweapon"), false);
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.effectList.add(e);
            }, 5.5F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this,
                        new WeakPower(AbstractDungeon.player, 3, true), 3));
                AbstractDungeon.actionManager.addToTop(new LatterAction(e::end));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.23F));
        }
        else if(this.nextMove == 3){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "PRAY"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new MutekiPower(this)));
            }, 2.67F));

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
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
            case "ATTACK2":
                this.state.setAnimation(0, "Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "PRAY":
                this.state.setAnimation(0, "Pray_Start", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Pray", true, 0.0F);
                break;
            case "PRAY_END":
                this.state.setAnimation(0, "Pray_End", false);
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
