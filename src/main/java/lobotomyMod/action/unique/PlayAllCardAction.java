package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author hoykj
 */
public class PlayAllCardAction extends AbstractGameAction
{
    public PlayAllCardAction()
    {
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                boolean flag = false;
                for (CardQueueItem q : AbstractDungeon.actionManager.cardQueue) {
                    if (q.card == c) {
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    continue;
                }
                c.freeToPlayOnce = true;
                switch (c.target)
                {
                    case SELF_AND_ENEMY:
                    case ENEMY:
                        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c,
                                AbstractDungeon.getRandomMonster()));
                        break;
                    case SELF:
                    case ALL:
                    case ALL_ENEMY:
                    case NONE:
                    default:
                        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, null));
                }
            }
            //AbstractDungeon.actionManager.addToBottom(new EndTurnAction());
        }
        tickDuration();
    }
}

