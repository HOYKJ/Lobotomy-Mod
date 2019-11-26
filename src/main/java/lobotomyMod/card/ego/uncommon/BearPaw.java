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

/**
 * @author hoykj
 */
public class BearPaw extends AbstractEgoCard {
    public static final String ID = "BearPaw";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BearPaw() {
        super("BearPaw", BearPaw.NAME, 1, BearPaw.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(4);
    }

    public AbstractCard makeCopy() {
        return new BearPaw();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BearPaw");
        NAME = BearPaw.cardStrings.NAME;
        DESCRIPTION = BearPaw.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BearPaw.cardStrings.EXTENDED_DESCRIPTION;
    }
}
