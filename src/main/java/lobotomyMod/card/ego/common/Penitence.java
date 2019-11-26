package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Penitence extends AbstractEgoCard {
    public static final String ID = "Penitence";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Penitence() {
        super("Penitence", Penitence.NAME, 1, Penitence.DESCRIPTION, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 0;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.block += (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) / 2;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.block += (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) / 2;
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);
    }

    public AbstractCard makeCopy() {
        return new Penitence();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Penitence");
        NAME = Penitence.cardStrings.NAME;
        DESCRIPTION = Penitence.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Penitence.cardStrings.EXTENDED_DESCRIPTION;
    }
}
