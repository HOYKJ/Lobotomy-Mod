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
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.BloodyDesirePower;

/**
 * @author hoykj
 */
public class BloodyDesire extends AbstractEgoCard {
    public static final String ID = "BloodyDesire";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BloodyDesire() {
        super("BloodyDesire", BloodyDesire.NAME, 1, BloodyDesire.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 6;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BloodyDesirePower(p, 6), 6));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new BloodyDesire();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BloodyDesire");
        NAME = BloodyDesire.cardStrings.NAME;
        DESCRIPTION = BloodyDesire.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BloodyDesire.cardStrings.EXTENDED_DESCRIPTION;
    }
}
