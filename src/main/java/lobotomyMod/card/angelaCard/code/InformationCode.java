package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.ReprogramAction;
import lobotomyMod.card.angelaCard.department.Yesod;

/**
 * @author hoykj
 */
public class InformationCode extends AbstractCodeCard {
    public static final String ID = "InformationCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public InformationCode() {
        super("InformationCode", InformationCode.NAME, Yesod.ID, 0, InformationCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 4 + this.timesUpgraded * 2;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 4 + this.timesUpgraded * 2;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReprogramAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseMagicNumber = 4 + this.timesUpgraded * 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public AbstractCard makeCopy() {
        return new InformationCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("InformationCode");
        NAME = InformationCode.cardStrings.NAME;
        DESCRIPTION = InformationCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = InformationCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
