package lobotomyMod.card.ego.rare;

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
public class TheSmile extends AbstractEgoCard {
    public static final String ID = "TheSmile";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public TheSmile() {
        super("TheSmile", TheSmile.NAME, 1, TheSmile.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 4;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(int i = 0; i < p.exhaustPile.size(); i ++){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(2);
    }

    public AbstractCard makeCopy() {
        return new TheSmile();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("TheSmile");
        NAME = TheSmile.cardStrings.NAME;
        DESCRIPTION = TheSmile.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = TheSmile.cardStrings.EXTENDED_DESCRIPTION;
    }
}
