package lobotomyMod.monster.Ordeal.Fixer;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
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
import lobotomyMod.monster.Ordeal.Bug.BugNight;
import lobotomyMod.vfx.RedFixerLaserEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class RedFixer extends AbstractFixer {
    public static final String ID = "RedFixer";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("RedFixer");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns;
    private Bone gun, weapon;

    public RedFixer(float x, float y) {
        super(NAME, "RedFixer", 200, 0.0F, -20.0F, 180.0F, 240.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Fixer/Red/body.atlas", "lobotomyMod/images/monsters/Ordeal/Fixer/Red/body.json", 3.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_01", 0.9F);
        this.stateData.setMix("Default", "Attack_02", 0.9F);
        this.stateData.setMix("Default", "Attack_03", 0.9F);
        this.stateData.setMix("Default", "Attack_04", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
        this.gun = this.skeleton.findBone("gun");
        this.weapon = this.skeleton.findBone("Rweapon");
        this.damage.add(new DamageInfo(this, 9));
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 12));
        this.damage.add(new DamageInfo(this, 50));
        this.turns = 1;
    }

    protected void getMove(int num) {
        if(this.halfDead){
            return;
        }
        switch (this.turns) {
            case 1: case 4:
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
                break;
            case 2: case 5:
                setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base, 2, true);
                break;
            case 3:
                setMove((byte) 3, Intent.ATTACK, this.damage.get(2).base, 3, true);
                break;
            case 6:
                setMove((byte) 4, Intent.ATTACK, this.damage.get(3).base);
                break;
        }
        this.turns ++;
        if(this.turns > 6){
            this.turns = 1;
        }
        //setMove((byte) 0, Intent.UNKNOWN, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK4"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.67F));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.6F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.07F));
        }
        else if(this.nextMove == 3){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK3"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SMASH));
            }, 0.87F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SMASH));
            }, 0.2F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SMASH));
            }, 0.03F));
        }
        else if(this.nextMove == 4){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.effectList.add(new RedFixerLaserEffect(this.skeleton.getX() + this.gun.getWorldX(), this.skeleton.getY() + this.gun.getWorldY(),
                        this.skeleton.getX() + this.weapon.getWorldX(), this.skeleton.getY() + this.weapon.getWorldY(), 0.4F));
            }, 2.4F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.FIRE));
            }, 0.13F));
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
            case "ATTACK3":
                this.state.setAnimation(0, "Attack_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK4":
                this.state.setAnimation(0, "Attack_04", false);
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
