package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.CherryBlossomPower;

/**
 * @author hoykj
 */
public class CherryBlossom extends AbstractEgoCard {
    public static final String ID = "CherryBlossom";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CherryBlossom() {
        super("CherryBlossom", CherryBlossom.NAME, 1, CherryBlossom.DESCRIPTION, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CherryBlossomPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new CherryBlossom();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CherryBlossom");
        NAME = CherryBlossom.cardStrings.NAME;
        DESCRIPTION = CherryBlossom.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CherryBlossom.cardStrings.EXTENDED_DESCRIPTION;
    }
}
