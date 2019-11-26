package lobotomyMod.action.unique;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.EventRoom;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.event.WhiteNightEvent;
import lobotomyMod.relic.DeathAngel;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class RecallAbnormalityAction extends AbstractGameAction {
    private AbstractRelic relic;

    public RecallAbnormalityAction(AbstractRelic relic){
        this.relic = relic;
    }

    public void update(){
        if(this.relic.counter < 100){
            this.isDone = true;
            return;
        }
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[0],false, 1, true);
        if((AbstractDungeon.player.masterDeck.findCardById(WhiteNight.ID) != null) && (!AbstractDungeon.player.hasRelic(DeathAngel.ID))){
            choice.add(AbstractDungeon.player.masterDeck.findCardById(WhiteNight.ID), () -> {
                AbstractDungeon.player.masterDeck.removeCard(WhiteNight.ID);
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.currMapNode.room = new EventRoom();
                    AbstractDungeon.getCurrRoom().event = new WhiteNightEvent(true);
                    AbstractDungeon.getCurrRoom().event.onEnterRoom();
                    CardCrawlGame.fadeIn(1.5F);
                    AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                }, 0.1F));
                this.relic.counter -= 100;
            });
        }
        AbstractDungeon.actionManager.addToBottom(choice);
        this.isDone = true;
    }
}
