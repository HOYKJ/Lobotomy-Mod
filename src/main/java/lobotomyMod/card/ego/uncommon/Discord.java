package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
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
public class Discord extends AbstractEgoCard {
    public static final String ID = "Discord";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Discord() {
        super("Discord", Discord.NAME, 0, Discord.DESCRIPTION, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            for(AbstractCard card : p.drawPile.group){
                if(card.cost >= 0){
                    if(card.cost > 3){
                        card.modifyCostForCombat(-card.cost);
                    }
                    else {
                        card.modifyCostForCombat(3 - card.cost * 2);
                    }
                }
            }
            for(AbstractCard card : p.hand.group){
                if(card.cost >= 0){
                    if(card.cost > 3){
                        card.modifyCostForCombat(-card.cost);
                    }
                    else {
                        card.modifyCostForCombat(3 - card.cost * 2);
                    }
                }
            }
            for(AbstractCard card : p.discardPile.group){
                if(card.cost >= 0){
                    if(card.cost > 3){
                        card.modifyCostForCombat(-card.cost);
                    }
                    else {
                        card.modifyCostForCombat(3 - card.cost * 2);
                    }
                }
            }
        }));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.isInnate = true;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Discord();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Discord");
        NAME = Discord.cardStrings.NAME;
        DESCRIPTION = Discord.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Discord.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Discord.cardStrings.EXTENDED_DESCRIPTION;
    }
}
