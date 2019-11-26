package lobotomyMod.action.unique;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.deriveCard.BlackArmyDerive;
import lobotomyMod.card.rareCard.BlackArmy;

/**
 * @author hoykj
 */
public class BlackArmyBackAction extends AbstractGameAction {

    public BlackArmyBackAction(){
    }

    public void update(){
        if(AbstractDungeon.player.drawPile.findCardById(BlackArmyDerive.ID) != null){
            return;
        }
        if(AbstractDungeon.player.hand.findCardById(BlackArmyDerive.ID) != null){
            return;
        }
        if(AbstractDungeon.player.discardPile.findCardById(BlackArmyDerive.ID) != null){
            return;
        }

        if(AbstractDungeon.player.drawPile.findCardById(BlackArmy.ID) != null){
            return;
        }
        if(AbstractDungeon.player.hand.findCardById(BlackArmy.ID) != null){
            return;
        }
        if(AbstractDungeon.player.discardPile.findCardById(BlackArmy.ID) != null){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new BlackArmy()));
    }
}
