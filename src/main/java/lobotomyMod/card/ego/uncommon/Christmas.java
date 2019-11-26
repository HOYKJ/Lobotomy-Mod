package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.ChristmasPower;

/**
 * @author hoykj
 */
public class Christmas extends AbstractEgoCard {
    public static final String ID = "Christmas";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Christmas() {
        super("Christmas", Christmas.NAME, 2, Christmas.DESCRIPTION, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChristmasPower(p)));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(1);
    }

    public AbstractCard makeCopy() {
        return new Christmas();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Christmas");
        NAME = Christmas.cardStrings.NAME;
        DESCRIPTION = Christmas.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Christmas.cardStrings.EXTENDED_DESCRIPTION;
    }
}
