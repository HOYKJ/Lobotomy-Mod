package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.MimicryPower;

/**
 * @author hoykj
 */
public class Mimicry extends AbstractEgoCard {
    public static final String ID = "Mimicry";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Mimicry() {
        super("Mimicry", Mimicry.NAME, 1, Mimicry.DESCRIPTION, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MimicryPower(p)));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.isInnate = true;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Mimicry();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Mimicry");
        NAME = Mimicry.cardStrings.NAME;
        DESCRIPTION = Mimicry.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Mimicry.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Mimicry.cardStrings.EXTENDED_DESCRIPTION;
    }
}
