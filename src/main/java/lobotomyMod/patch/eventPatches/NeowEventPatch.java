package lobotomyMod.patch.eventPatches;

import basemod.helpers.SuperclassFinder;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.Angela;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.event.FirstMeet;
import lobotomyMod.event.RabbitProtocol;
import lobotomyMod.neow.GetBucket;
import lobotomyMod.relic.CogitoBucket;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class NeowEventPatch {

    @SpirePatch(cls="com.megacrit.cardcrawl.neow.NeowEvent", method="buttonEffect")
    public static class buttonEffectPatch
    {
        @SpireInsertPatch(rloc=0)
        public static void Insert(NeowEvent _inst, int buttonPressed) throws NoSuchFieldException, IllegalAccessException {
            Field bossCount = _inst.getClass().getDeclaredField("bossCount");
            bossCount.setAccessible(true);

            if((int) bossCount.get(_inst) < 1) {
                bossCount.set(_inst, 1);
            }
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.neow.NeowEvent", method="blessing")
    public static class blessingPatch1
    {
        @SpireInsertPatch(rloc=12, localvars={"rewards"})
        public static void Insert(NeowEvent _inst, ArrayList<NeowReward> rewards)
        {
            rewards.add(new GetBucket());
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.neow.NeowEvent", method="blessing")
    public static class blessingPatch
    {
        @SpireInsertPatch(rloc=19, localvars={"rewards"})
        public static void Insert(NeowEvent _inst, ArrayList<NeowReward> rewards)
        {
            if(AbstractDungeon.player instanceof Angela){
                return;
            }
            _inst.roomEventText.addDialogOption((rewards.get(4)).optionLabel);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.neow.NeowEvent", method="buttonEffect")
    public static class buttEffectPatch
    {
        @SpireInsertPatch(rloc=63, localvars={"rewards"})
        public static void Insert(NeowEvent _inst, int buttonPressed, ArrayList<NeowReward> rewards) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
            if(AbstractDungeon.player instanceof Angela){
                _inst.roomEventText.addDialogOption(FirstMeet.OPTIONS[2]);
            }
            if (buttonPressed < 4) {
                return;
            }
            rewards.get(buttonPressed).activate();
            Method m = NeowEvent.class.getDeclaredMethod("talk", String.class);
            m.setAccessible(true);
            m.invoke(_inst, "...");
//            if(unlockEnough()) {
//                _inst.roomEventText.addDialogOption(FirstMeet.DESCRIPTIONS[0]);
//            }
            _inst.roomEventText.addDialogOption(FirstMeet.DESCRIPTIONS[0]);
        }

        @SpireInsertPatch(rloc=82)
        public static SpireReturn Insert2(NeowEvent _inst, int buttonPressed) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
            if (buttonPressed > 0) {
                if(AbstractDungeon.player instanceof Angela){
                    LobotomyMod.activeFixer = true;
                    return SpireReturn.Continue();
                }
                if(LobotomyMod.defeatFixer && !LobotomyMod.activeRabbit){
                    AbstractDungeon.topLevelEffects.clear();
                    AbstractDungeon.effectList.clear();
                    AbstractDungeon.getCurrRoom().event = new RabbitProtocol();
                    AbstractDungeon.getCurrRoom().event.onEnterRoom();
                    if(AbstractDungeon.scene instanceof TheBottomScene){
                        try {
                            Field torches = SuperclassFinder.getSuperclassField(AbstractDungeon.scene.getClass(), "torches");
                            torches.setAccessible(true);
                            ((ArrayList)torches.get(AbstractDungeon.scene)).clear();
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    CardCrawlGame.fadeIn(1.5F);
                    AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                    AbstractDungeon.closeCurrentScreen();
                    return SpireReturn.Return(null);
                }
//                AbstractDungeon.currMapNode.room = new EventRoom();
                AbstractDungeon.topLevelEffects.clear();
                AbstractDungeon.effectList.clear();
                AbstractDungeon.getCurrRoom().event = new FirstMeet();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                if(AbstractDungeon.scene instanceof TheBottomScene){
                    try {
                        Field torches = SuperclassFinder.getSuperclassField(AbstractDungeon.scene.getClass(), "torches");
                        torches.setAccessible(true);
                        ((ArrayList)torches.get(AbstractDungeon.scene)).clear();
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                CardCrawlGame.fadeIn(1.5F);
                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                AbstractDungeon.closeCurrentScreen();
                LobotomyMod.logger.info("-----test-----");
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
