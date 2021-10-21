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
public class Galaxy extends AbstractEgoCard {
    public static final String ID = "Galaxy";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Galaxy() {
        super("Galaxy", Galaxy.NAME, 2, Galaxy.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 28;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(p.hand.getCardsOfType(CardType.SKILL).size() < 2){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(8);
    }

    public AbstractCard makeCopy() {
        return new Galaxy();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Galaxy");
        NAME = Galaxy.cardStrings.NAME;
        DESCRIPTION = Galaxy.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Galaxy.cardStrings.EXTENDED_DESCRIPTION;
    }
}
