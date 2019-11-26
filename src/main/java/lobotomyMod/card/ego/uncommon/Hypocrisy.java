package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ScrapeAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.HypocrisyAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Hypocrisy extends AbstractEgoCard {
    public static final String ID = "Hypocrisy";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Hypocrisy() {
        super("Hypocrisy", Hypocrisy.NAME, 0, Hypocrisy.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ScrapeAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new HypocrisyAction());
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new Hypocrisy();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hypocrisy");
        NAME = Hypocrisy.cardStrings.NAME;
        DESCRIPTION = Hypocrisy.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Hypocrisy.cardStrings.EXTENDED_DESCRIPTION;
    }
}
