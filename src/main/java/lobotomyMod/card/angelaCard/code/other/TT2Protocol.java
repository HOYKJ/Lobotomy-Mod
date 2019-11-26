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
public class TT2Protocol extends AbstractCodeCard {
    public static final String ID = "TT2Protocol";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public TT2Protocol() {
        super("TT2Protocol", TT2Protocol.NAME, 3, TT2Protocol.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new PlayAllCardAction());
    }

    public AbstractCard makeCopy() {
        return new TT2Protocol();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("TT2Protocol");
        NAME = TT2Protocol.cardStrings.NAME;
        DESCRIPTION = TT2Protocol.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = TT2Protocol.cardStrings.EXTENDED_DESCRIPTION;
    }
}
