package lobotomyMod.card.deriveCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;

/**
 * @author hoykj
 */
public class ExpandDepartment extends CustomCard {
    public static final String ID = "ExpandDepartment";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public ExpandDepartment() {
        super("ExpandDepartment", NAME, LobotomyHandler.angelaCardImage("ExpandDepartment"),  -2, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Angela, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {

    }

    public AbstractCard makeCopy() {
        return new ExpandDepartment();
    }

    public void upgrade() {
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ExpandDepartment");
        NAME = ExpandDepartment.cardStrings.NAME;
        DESCRIPTION = ExpandDepartment.cardStrings.DESCRIPTION;
    }
}
