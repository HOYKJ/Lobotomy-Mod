package lobotomyMod.action.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author hoykj
 */
public class RandomDiscardToHandAction extends AbstractGameAction {
    private int num;

    public RandomDiscardToHandAction(int num){
        this.num = num;
    }

    public void update(){
        for(int i = 0; i < num; i ++) {
            if (AbstractDungeon.player.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractCard card = AbstractDungeon.player.discardPile.getRandomCard(true);
            AbstractDungeon.player.hand.moveToHand(card, AbstractDungeon.player.discardPile);
        }
        this.isDone = true;
    }
}
