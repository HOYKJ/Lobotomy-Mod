package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.FrostShardAction;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class FrostShard extends AbstractEgoCard {
    public static final String ID = "FrostShard";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public FrostShard() {
        super("FrostShard", FrostShard.NAME, 2, FrostShard.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 6;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new FrostShardAction(p, 1, this.block));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
    }

    public AbstractCard makeCopy() {
        return new FrostShard();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
        this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FrostShard");
        NAME = FrostShard.cardStrings.NAME;
        DESCRIPTION = FrostShard.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = FrostShard.cardStrings.EXTENDED_DESCRIPTION;
    }
}
