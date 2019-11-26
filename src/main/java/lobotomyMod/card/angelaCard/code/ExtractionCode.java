package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.department.Binah;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class ExtractionCode extends AbstractCodeCard {
    public static final String ID = "ExtractionCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public ExtractionCode() {
        super("ExtractionCode", ExtractionCode.NAME, Binah.ID, 2, ExtractionCode.DESCRIPTION, CardTarget.SELF);
        this.cost = 2 - this.timesUpgraded;
        this.expand();
        this.purgeOnUse = true;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.cost = 2 - this.timesUpgraded;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.getCurrRoom().addCardToRewards();
    }

    @Override
    public void onVictory() {
        super.onVictory();

    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.cost = 2 - this.timesUpgraded;
        this.costForTurn = this.cost;
    }

    @Override
    public void expand() {
        super.expand();
//        if(AbstractDungeon.player instanceof Angela && Angela.departments[8] >= 2) {
//            this.rawDescription = EXTENDED_DESCRIPTION[0];
//            initializeDescription();
//        }
    }

    public AbstractCard makeCopy() {
        return new ExtractionCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ExtractionCode");
        NAME = ExtractionCode.cardStrings.NAME;
        DESCRIPTION = ExtractionCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ExtractionCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
