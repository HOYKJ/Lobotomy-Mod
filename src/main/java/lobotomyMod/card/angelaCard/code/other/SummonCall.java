package lobotomyMod.card.angelaCard.code.other;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.PlayAllCardAction;
import lobotomyMod.card.angelaCard.code.AbstractCodeCard;

/**
 * @author hoykj
 */
public class SummonCall extends AbstractCodeCard {
    public static final String ID = "SummonCall";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SummonCall() {
        super("SummonCall", SummonCall.NAME, 1, SummonCall.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof AbstractCodeCard && ((AbstractCodeCard) card).dep && card.timesUpgraded < 2){
                card.upgrade();
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof AbstractCodeCard && ((AbstractCodeCard) card).dep && card.timesUpgraded < 2){
                card.upgrade();
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof AbstractCodeCard && ((AbstractCodeCard) card).dep && card.timesUpgraded < 2){
                card.upgrade();
            }
        }
    }

    public AbstractCard makeCopy() {
        return new SummonCall();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SummonCall");
        NAME = SummonCall.cardStrings.NAME;
        DESCRIPTION = SummonCall.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SummonCall.cardStrings.EXTENDED_DESCRIPTION;
    }
}
