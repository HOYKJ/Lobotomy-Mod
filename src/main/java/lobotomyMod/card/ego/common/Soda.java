package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.SodaPower;

/**
 * @author hoykj
 */
public class Soda extends AbstractEgoCard {
    public static final String ID = "Soda";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Soda() {
        super("Soda", Soda.NAME, 1, Soda.DESCRIPTION, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SodaPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);
    }

    public AbstractCard makeCopy() {
        return new Soda();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Soda");
        NAME = Soda.cardStrings.NAME;
        DESCRIPTION = Soda.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Soda.cardStrings.EXTENDED_DESCRIPTION;
    }
}
