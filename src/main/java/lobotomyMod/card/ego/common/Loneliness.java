package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
public class Loneliness extends AbstractEgoCard {
    public static final String ID = "Loneliness";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Loneliness() {
        super("Loneliness", Loneliness.NAME, 1, Loneliness.DESCRIPTION, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 14;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.hand.size() > 1){
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(4);
    }

    public AbstractCard makeCopy() {
        return new Loneliness();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Loneliness");
        NAME = Loneliness.cardStrings.NAME;
        DESCRIPTION = Loneliness.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Loneliness.cardStrings.EXTENDED_DESCRIPTION;
    }
}
