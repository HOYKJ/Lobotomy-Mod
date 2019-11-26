package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Laetitia_ego extends AbstractEgoCard {
    public static final String ID = "Laetitia_ego";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Laetitia_ego() {
        super("Laetitia_ego", Laetitia_ego.NAME, 0, Laetitia_ego.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        }
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            for(AbstractCard card : p.hand.group){
                card.retain = true;
            }
        }));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Laetitia_ego();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Laetitia_ego");
        NAME = Laetitia_ego.cardStrings.NAME;
        DESCRIPTION = Laetitia_ego.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Laetitia_ego.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Laetitia_ego.cardStrings.EXTENDED_DESCRIPTION;
    }
}
