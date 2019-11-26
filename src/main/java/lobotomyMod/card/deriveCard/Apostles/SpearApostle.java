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
public class SpearApostle extends AbstractApostleCard {
    public static final String ID = "SpearApostle";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public SpearApostle() {
        super("SpearApostle", SpearApostle.NAME, 1, SpearApostle.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        if((hand) && (card.type == CardType.ATTACK)){
            if((card instanceof WhiteNight) || (card instanceof AbstractApostleCard)){
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpearApostle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SpearApostle");
        NAME = SpearApostle.cardStrings.NAME;
        DESCRIPTION = SpearApostle.cardStrings.DESCRIPTION;
    }
}
