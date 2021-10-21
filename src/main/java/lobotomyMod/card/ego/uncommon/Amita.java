package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class Amita extends AbstractEgoCard {
    public static final String ID = "Amita";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Amita() {
        super("Amita", Amita.NAME, 1, Amita.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(p.drawPile.size() == 0 && p.discardPile.size() != 0){
            this.addToTop(new EmptyDeckShuffleAction());
        }
        else if(p.drawPile.size() == 0 && p.discardPile.size() == 0){
            return;
        }
        this.addToBot(new LatterAction(()->{
            AbstractCard card = p.drawPile.getTopCard();
            if(card != null) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.drawPile));
                if (card.cost > 0) {
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(card.cost * 3));
                }
            }
        }));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.exhaust = false;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Amita();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Amita");
        NAME = Amita.cardStrings.NAME;
        DESCRIPTION = Amita.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Amita.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Amita.cardStrings.EXTENDED_DESCRIPTION;
    }
}
