package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Wingbeat extends AbstractEgoCard {
    public static final String ID = "Wingbeat";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Wingbeat() {
        super("Wingbeat", Wingbeat.NAME, 1, Wingbeat.DESCRIPTION, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 7;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
    }

    public AbstractCard makeCopy() {
        return new Wingbeat();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wingbeat");
        NAME = Wingbeat.cardStrings.NAME;
        DESCRIPTION = Wingbeat.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Wingbeat.cardStrings.EXTENDED_DESCRIPTION;
    }
}
