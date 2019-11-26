package lobotomyMod.monster.Ordeal.Fixer;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import lobotomyMod.action.common.LatterAction;

/**
 * @author hoykj
 */
public class BlackFixer extends AbstractFixer{
    public static final String ID = "BlackFixer";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BlackFixer");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turn;

    public BlackFixer(float x, float y) {
        super(NAME, "BlackFixer", 200, 0.0F, -20.0F, 180.0F, 240.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Fixer/Black/body.atlas", "lobotomyMod/images/monsters/Ordeal/Fixer/Black/body.json", 3.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        //this.stateData.setMix("Default", "Attack_01", 0.1F);
        this.stateData.setMix("Default", "Attack_02", 0.2F);
        //this.stateData.setMix("Default", "Attack_03", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
        this.damage.add(new DamageInfo(this, 16));
        this.damage.add(new DamageInfo(this, 8));
        this.damage.add(new DamageInfo(this, 3));
        this.turn = 0;
    }

    protected void getMove(int num) {
        if(this.halfDead){
            return;
        }
        switch (this.turn){
            case 0:
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
                break;
            case 1:
                setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                break;
            case 2:
                setMove((byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(2).base, 6, true);
                break;
        }
        this.turn ++;
        if(this.turn > 2){
            this.turn = 0;
        }
        //setMove((byte) 0, Intent.UNKNOWN, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }, 2.0F));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.9F));
        }
        else if(this.nextMove == 3){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK3"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 1.7F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 1.2F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.9F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.5F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 1.0F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.8F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                if(AbstractDungeon.aiRng.randomBoolean()){
                    if(AbstractDungeon.player.drawPile.size() > 0) {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getRandomCard(true),
                                AbstractDungeon.player.drawPile));
                    }
                    else if(AbstractDungeon.player.discardPile.size() > 0) {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getRandomCard(true),
                                AbstractDungeon.player.discardPile));
                    }
                }
                else{
                    if(AbstractDungeon.player.discardPile.size() > 0) {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getRandomCard(true),
                                AbstractDungeon.player.discardPile));
                    }
                    else if(AbstractDungeon.player.drawPile.size() > 0) {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getRandomCard(true),
                                AbstractDungeon.player.drawPile));
                    }
                }
            }, 1.3F));
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
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
