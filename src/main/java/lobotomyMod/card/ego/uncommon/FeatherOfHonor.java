package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.FeatherOfHonorAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class FeatherOfHonor extends AbstractEgoCard {
    public static final String ID = "FeatherOfHonor";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public FeatherOfHonor() {
        super("FeatherOfHonor", FeatherOfHonor.NAME, 2, FeatherOfHonor.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 6;
        this.isMultiDamage = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new FeatherOfHonorAction(p, 1, this.multiDamage));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
    }

    public AbstractCard makeCopy() {
        return new FeatherOfHonor();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
        this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FeatherOfHonor");
        NAME = FeatherOfHonor.cardStrings.NAME;
        DESCRIPTION = FeatherOfHonor.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = FeatherOfHonor.cardStrings.EXTENDED_DESCRIPTION;
    }
}
