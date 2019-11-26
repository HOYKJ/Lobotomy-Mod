package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.uncommonCard.QueenBee;

/**
 * @author hoykj
 */
public class WorkerBee extends AbstractDeriveCard{
    public static final String ID = "WorkerBee";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public WorkerBee() {
        super("WorkerBee", WorkerBee.NAME, 0, WorkerBee.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(hand){
            AbstractCard target = null;
            if(AbstractDungeon.player.hand.size() > (AbstractDungeon.player.hand.findCardById(QueenBee.ID) != null? 1: 0)){
                target = AbstractDungeon.player.hand.getRandomCard(true);
                while (target instanceof QueenBee){
                    target = AbstractDungeon.player.hand.getRandomCard(true);
                }
            }
            if(target == null){
                return;
            }

            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(target, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new WorkerBee(), 1, true, false));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WorkerBee();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WorkerBee");
        NAME = WorkerBee.cardStrings.NAME;
        DESCRIPTION = WorkerBee.cardStrings.DESCRIPTION;
    }
}
