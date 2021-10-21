package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.RecordCodeAction;
import lobotomyMod.card.angelaCard.department.Hokma;

/**
 * @author hoykj
 */
public class RecordCode extends AbstractCodeCard {
    public static final String ID = "RecordCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public RecordCode() {
        super("RecordCode", RecordCode.NAME, Hokma.ID, 2, RecordCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 2 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 2 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RecordCodeAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseMagicNumber = 2 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
    }

    public AbstractCard makeCopy() {
        return new RecordCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("RecordCode");
        NAME = RecordCode.cardStrings.NAME;
        DESCRIPTION = RecordCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = RecordCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
