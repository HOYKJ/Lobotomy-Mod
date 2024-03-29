package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.Iterator;

/**
 * @author hoykj
 */
public class TurnEndAction extends AbstractGameAction {

    public TurnEndAction(){
    }

    public void update()
    {
        AbstractDungeon.actionManager.cardQueue.clear();
        for (Iterator localIterator = AbstractDungeon.player.limbo.group.iterator(); localIterator.hasNext(); ) { AbstractCard c = (AbstractCard)localIterator.next();
            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        }
        AbstractDungeon.player.limbo.group.clear();
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);

        this.isDone = true;
    }
}
