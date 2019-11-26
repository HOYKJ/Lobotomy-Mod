package lobotomyMod.action.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class DiscardOtherAction extends AbstractGameAction {
    private AbstractCard card;

    public DiscardOtherAction(AbstractCard card)
    {
        this.card = card;
    }

    public void update()
    {
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card != this.card){
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.player.hand.moveToDiscardPile(card);
                    GameActionManager.incrementDiscard(false);
                    card.triggerOnManualDiscard();
                }, 0));
            }
        }
        this.isDone = true;
    }
}
