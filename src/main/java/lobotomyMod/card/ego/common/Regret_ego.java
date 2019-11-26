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
import lobotomyMod.power.RegretPower;

/**
 * @author hoykj
 */
public class Regret_ego extends AbstractEgoCard {
    public static final String ID = "Regret_ego";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Regret_ego() {
        super("Regret_ego", Regret_ego.NAME, 2, Regret_ego.DESCRIPTION, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegretPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(1);
    }

    public AbstractCard makeCopy() {
        return new Regret_ego();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Regret_ego");
        NAME = Regret_ego.cardStrings.NAME;
        DESCRIPTION = Regret_ego.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Regret_ego.cardStrings.EXTENDED_DESCRIPTION;
    }
}
