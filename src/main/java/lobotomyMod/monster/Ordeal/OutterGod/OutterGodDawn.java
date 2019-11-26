package lobotomyMod.monster.Ordeal.OutterGod;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class OutterGodDawn extends AbstractOrdealMonster {
    public static final String ID = "OutterGodDawn";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodDawn");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int counter;

    public OutterGodDawn(float x, float y) {
        super(NAME, "OutterGodDawn", 36, 10.0F, -10.0F, 250.0F, 130.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/OutterGod/Dawn/cosmic_dawn.atlas", "lobotomyMod/images/monsters/Ordeal/OutterGod/Dawn/cosmic_dawn.json", 2.2F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Walk", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Walk", "Dead", 0.9F);
        this.damage.add(new DamageInfo(this, 12, DamageInfo.DamageType.THORNS));
        this.flipHorizontal = true;
        this.counter = 0;
        this.setHp(36, 42);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 4), 4));
    }

    protected void getMove(int num) {
        this.counter ++;
        if(this.counter == 1){
            setMove((byte) 1, Intent.UNKNOWN);
        }
        else if(this.counter == 2){
            setMove((byte) 1, Intent.UNKNOWN);
        }
        else if(this.counter >= 3){
            if(AbstractDungeon.monsterRng.randomBoolean()){
                setMove((byte) 1, Intent.UNKNOWN);
            }
            else {
                setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
            }
        }
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractCard card = LobotomyUtils.getRandomCard();
            if(AbstractDungeon.player.drawPile.contains(card)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
            else if(AbstractDungeon.player.hand.contains(card)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
            else if(AbstractDungeon.player.discardPile.contains(card)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            }
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
            AbstractCard card = AbstractDungeon.player.masterDeck.getRandomCard(true);
            AbstractDungeon.player.masterDeck.removeCard(card);
            for(AbstractCard card1 : AbstractDungeon.player.drawPile.group) {
                if (card.uuid.equals(card1.uuid)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.drawPile));
                    break;
                }
            }
            for(AbstractCard card1 : AbstractDungeon.player.hand.group) {
                if (card.uuid.equals(card1.uuid)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.hand));
                    break;
                }
            }
            for(AbstractCard card1 : AbstractDungeon.player.discardPile.group) {
                if (card.uuid.equals(card1.uuid)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.discardPile));
                    break;
                }
            }

            AbstractDungeon.actionManager.addToBottom(new SuicideAction(this));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die(boolean triggerRelics) {
        super.die(triggerRelics);
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(3, 1, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
