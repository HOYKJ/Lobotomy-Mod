package lobotomyMod.card.deriveCard.Apostles;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;
import lobotomyMod.card.deriveCard.SilentAdagio;
import lobotomyMod.card.rareCard.WhiteNight;

/**
 * @author hoykj
 */
public class GuardApostle extends AbstractApostleCard {
    public static final String ID = "GuardApostle";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public GuardApostle() {
        super("GuardApostle", GuardApostle.NAME, 1, GuardApostle.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        if(hand){
            if(AbstractDungeon.player.hand.findCardById(WhiteNight.ID) != null){
                if((card instanceof WhiteNight) || (card instanceof AbstractApostleCard)){
                    return;
                }
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            }
        }
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GuardApostle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("GuardApostle");
        NAME = GuardApostle.cardStrings.NAME;
        DESCRIPTION = GuardApostle.cardStrings.DESCRIPTION;
    }
}
