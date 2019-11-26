package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Gaze extends AbstractEgoCard {
    public static final String ID = "Gaze";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Gaze() {
        super("Gaze", Gaze.NAME, -2, Gaze.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 6;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        this.applyPowers();
        this.calculateCardDamage(null);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
    }

    public AbstractCard makeCopy() {
        return new Gaze();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Gaze");
        NAME = Gaze.cardStrings.NAME;
        DESCRIPTION = Gaze.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Gaze.cardStrings.EXTENDED_DESCRIPTION;
    }
}
