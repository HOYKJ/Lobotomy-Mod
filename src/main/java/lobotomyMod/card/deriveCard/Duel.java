package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class Duel extends AbstractDeriveCard{
    public static final String ID = "Duel";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private AbstractCard kai;

    public Duel(AbstractCard card) {
        super("Duel", Duel.NAME, 0, Duel.DESCRIPTION, CardColor.COLORLESS, CardType.ATTACK, CardTarget.NONE);
        this.isEthereal = false;
        this.kai = card;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final ChooseAction choice = new ChooseAction(this, m, AbstractLobotomyCard.EXTENDED_DESCRIPTION[0],false, 1, true);
        for(AbstractCard card : p.hand.group) {
            if((card != kai) && (card.type == CardType.ATTACK) && (card != this)) {
                choice.add(card, () -> {
                    int battle = AbstractDungeon.cardRng.random(6, 18);
                    if (card.baseDamage > battle){
                        this.kai.modifyCostForCombat(-9);
                        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 3));
                    }
                    else {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this.kai, AbstractDungeon.player.hand));
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this.kai, AbstractDungeon.player.drawPile));
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this.kai, AbstractDungeon.player.discardPile));
                    }
                });
            }
        }
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public void update() {
        super.update();
        this.retain = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard card : p.hand.group) {
            if ((card != kai) && (card.type == CardType.ATTACK) && (card != this)) {
                return super.canUse(p, m);
            }
        }
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Duel(null);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Duel");
        NAME = Duel.cardStrings.NAME;
        DESCRIPTION = Duel.cardStrings.DESCRIPTION;
    }
}
