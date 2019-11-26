package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.defect.ScrapeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author hoykj
 */
public class HypocrisyAction extends AbstractGameAction
{
    public HypocrisyAction()
    {
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FASTER)
        {
            for (AbstractCard c : ScrapeAction.scrapedCards) {
                if ((c.costForTurn == 0) && (c.freeToPlayOnce))
                {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
            }
            ScrapeAction.scrapedCards.clear();
        }
        tickDuration();
    }
}
