package lobotomyMod.action.unique;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.department.Binah;
import lobotomyMod.card.angelaCard.department.Geburah;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.event.WhiteNightEvent;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.DeathAngel;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.HashMap;

/**
 * @author hoykj
 */
public class RecallAbnormalityAction extends AbstractGameAction {
    public static class relicAndRunnable{
        public Runnable runnable;

        public relicAndRunnable(Runnable runnable){
            this.runnable = runnable;
        }

        public boolean canUse(AbstractRelic relic){
            return false;
        }

        public void cost(AbstractRelic relic){ }
    }
    public static HashMap<String, relicAndRunnable> recallMap = new HashMap<>();
    private AbstractRelic relic;

    public RecallAbnormalityAction(AbstractRelic relic){
        this.relic = relic;
    }

    public void update(){
//        if(this.relic.counter < 10){
//            this.isDone = true;
//            return;
//        }
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[0],true, 1, true);
        for(String key : recallMap.keySet()){
            if((AbstractDungeon.player.masterDeck.findCardById(key) != null) && recallMap.get(key).canUse(this.relic)){
                choice.add(AbstractDungeon.player.masterDeck.findCardById(key), () -> {
                    recallMap.get(key).runnable.run();
                    recallMap.get(key).cost(this.relic);
                    //this.relic.counter -= 10;
                });
            }
        }
//        if((AbstractDungeon.player.masterDeck.findCardById(WhiteNight.ID) != null) && (!AbstractDungeon.player.hasRelic(DeathAngel.ID))){
//            choice.add(AbstractDungeon.player.masterDeck.findCardById(WhiteNight.ID), () -> {
//                AbstractDungeon.player.masterDeck.removeCard(WhiteNight.ID);
//                AbstractDungeon.effectList.add(new LatterEffect(() -> {
//                    AbstractDungeon.currMapNode.room = new EventRoom();
//                    AbstractDungeon.getCurrRoom().event = new WhiteNightEvent(true);
//                    AbstractDungeon.getCurrRoom().event.onEnterRoom();
//                    CardCrawlGame.fadeIn(1.5F);
//                    AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
//                    AbstractDungeon.overlayMenu.hideCombatPanels();
//                }, 0.1F));
//                this.relic.counter -= 100;
//            });
//        }
//        if(LobotomyMod.activeAngela){
//            choice.add(new Binah(false), "Binah", lobotomyMod.monster.sephirah.Binah.Binah.DIALOG[0], () -> {
//                AbstractDungeon.currMapNode.room = new MonsterRoom();
//                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Binah");
//                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
//                AbstractDungeon.getCurrRoom().monsters.init();
//                AbstractRoom.waitTimer = 0.1F;
//                AbstractDungeon.player.preBattlePrep();
//                CardCrawlGame.fadeIn(1.5F);
//                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
//            });
//            choice.add(new Geburah(false), "Geburah", lobotomyMod.monster.sephirah.Geburah.DIALOG[0], () -> {
//                AbstractDungeon.currMapNode.room = new MonsterRoom();
//                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Geburah");
//                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
//                AbstractDungeon.getCurrRoom().monsters.init();
//                AbstractRoom.waitTimer = 0.1F;
//                AbstractDungeon.player.preBattlePrep();
//                CardCrawlGame.fadeIn(1.5F);
//                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
//            });
//        }
        AbstractDungeon.actionManager.addToBottom(choice);
        ((CogitoBucket)this.relic).clicked = false;
        this.isDone = true;
    }
}
