package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class GreenStem extends AbstractEgoCard {
    public static final String ID = "GreenStem";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public GreenStem() {
        super("GreenStem", GreenStem.NAME, 3, GreenStem.DESCRIPTION, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(2);
    }

    public AbstractCard makeCopy() {
        return new GreenStem();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("GreenStem");
        NAME = GreenStem.cardStrings.NAME;
        DESCRIPTION = GreenStem.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = GreenStem.cardStrings.EXTENDED_DESCRIPTION;
    }
}
