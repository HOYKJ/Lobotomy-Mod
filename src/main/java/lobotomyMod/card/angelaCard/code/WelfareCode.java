package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.ReprogramAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.department.Chesed;
import lobotomyMod.card.angelaCard.department.Yesod;

/**
 * @author hoykj
 */
public class WelfareCode extends AbstractCodeCard {
    public static final String ID = "WelfareCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public WelfareCode() {
        super("WelfareCode", WelfareCode.NAME, Chesed.ID, 1, WelfareCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
    }

    public AbstractCard makeCopy() {
        return new WelfareCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WelfareCode");
        NAME = WelfareCode.cardStrings.NAME;
        DESCRIPTION = WelfareCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = WelfareCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
