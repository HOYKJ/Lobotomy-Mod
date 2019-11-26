package lobotomyMod.action.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author hoykj
 */
public class ExhaustRandomCardAction extends AbstractGameAction {

    public ExhaustRandomCardAction(){
    }

    public void update(){
        if(!AbstractDungeon.player.drawPile.isEmpty()){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getRandomCard(true), AbstractDungeon.player.drawPile));
        }
        else if(!AbstractDungeon.player.discardPile.isEmpty()){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getRandomCard(true), AbstractDungeon.player.discardPile));
        }
        else if(!AbstractDungeon.player.hand.isEmpty()){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.hand.getRandomCard(true), AbstractDungeon.player.hand));
        }

        this.isDone = true;
    }
}
