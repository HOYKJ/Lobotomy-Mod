package lobotomyMod.helper;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.department.*;
import lobotomyMod.card.deriveCard.ExpandDepartment;
import lobotomyMod.character.Angela;
import lobotomyMod.patch.RelicViewScreenPatch;
import lobotomyMod.reward.CogitoReward;
import lobotomyMod.ui.LobotomyFtue;
import lobotomyMod.vfx.action.ChooseEffect;
import lobotomyMod.vfx.action.HireEffect;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.helpers.RelicLibrary.addToTierList;

/**
 * @author hoykj
 */
public class LobotomyUtils {

    public static AbstractCard getRandomCard(CardGroup group1, CardGroup group2, CardGroup group3, AbstractCard source){
        if(group1.contains(source)){
            if(group1.size() > 1){
                AbstractCard card = group1.getRandomCard(true);
                while (card == source){
                    card = group1.getRandomCard(true);
                }
                return card;
            }
            else if(group2.size() > 0){
                return group2.getRandomCard(true);
            }
            else if(group3.size() > 0){
                return group3.getRandomCard(true);
            }
        }
        else if(group2.contains(source)){
            if(group1.size() > 0){
                return group1.getRandomCard(true);
            }
            else if(group2.size() > 1){
                AbstractCard card = group2.getRandomCard(true);
                while (card == source){
                    card = group2.getRandomCard(true);
                }
                return card;
            }
            else if(group3.size() > 0){
                return group3.getRandomCard(true);
            }
        }
        else if(group3.contains(source)){
            if(group1.size() > 0){
                return group1.getRandomCard(true);
            }
            else if(group2.size() > 0){
                return group2.getRandomCard(true);
            }
            else if(group3.size() > 1){
                AbstractCard card = group3.getRandomCard(true);
                while (card == source){
                    card = group3.getRandomCard(true);
                }
                return card;
            }
        }
        else {
            if(group1.size() > 0){
                return group1.getRandomCard(true);
            }
            else if(group2.size() > 0){
                return group2.getRandomCard(true);
            }
            else if(group3.size() > 0){
                return group3.getRandomCard(true);
            }
        }
        return null;
    }

    public static AbstractCard getRandomCard(CardGroup group1, AbstractCard source){
        if(group1.contains(source)){
            if(group1.size() > 1){
                AbstractCard card = group1.getRandomCard(true);
                while (card == source){
                    card = group1.getRandomCard(true);
                }
                return card;
            }
        }
        return null;
    }

    public static int getRandomCard(CardGroup group1, AbstractCard source, boolean num){
        if(group1.contains(source)){
            if(group1.size() > 1){
                int card = AbstractDungeon.cardRng.random(group1.size() - 1);
                while (group1.group.get(card) == source){
                    card = AbstractDungeon.cardRng.random(group1.size() - 1);
                }
                return card;
            }
        }
        return -1;
    }

    public static AbstractCard getRandomCard(){
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.addAll(AbstractDungeon.player.drawPile.group);
        list.addAll(AbstractDungeon.player.hand.group);
        list.addAll(AbstractDungeon.player.discardPile.group);

        if(list.size() < 1){
            return null;
        }

        return list.get(AbstractDungeon.cardRng.random(list.size() - 1));
    }

    public static ArrayList<AbstractCard> getRandomCard(int num){
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.addAll(AbstractDungeon.player.drawPile.group);
        list.addAll(AbstractDungeon.player.hand.group);
        list.addAll(AbstractDungeon.player.discardPile.group);

        ArrayList<AbstractCard> tmp = new ArrayList<>();
        for (int i = 0; i < num; i ++){
            AbstractCard card = list.get(AbstractDungeon.cardRng.random(list.size() - 1));
            tmp.add(card);
            list.remove(card);
            if(list.size() == 0){
                break;
            }
        }

        return tmp;
    }

    public static void OrdealStart(int code, int difficulty){
        if(!LobotomyMod.enableOrdeal){
            return;
        }

        if(LobotomyMod.activeTutorials[2]) {
            AbstractDungeon.ftue = new LobotomyFtue(2, code, difficulty);
            LobotomyMod.activeTutorials[2] = false;
            return;
        }

        AbstractDungeon.overlayMenu.hideCombatPanels();
        if(difficulty == 4) {
            AbstractDungeon.currMapNode.room = new MonsterRoomBoss();
        }
        else {
            AbstractDungeon.currMapNode.room = new MonsterRoom();
        }
        String key = "";
        switch (code){
            case 1:
                switch (difficulty){
                    case 1:
                        key = "MachineDawn";
                        break;
                    case 2:
                        key = "MachineNoon";
                        break;
                    case 3:
                        key = "MachineNight";
                        break;
                    case 4:
                        key = "MachineMidnight";
                        break;
                }
                break;
            case 2:
                switch (difficulty){
                    case 1:
                        key = "BugDawn";
                        break;
                    case 3:
                        key = "BugNight";
                        break;
                    case 4:
                        key = "BugMidnight";
                        break;
                }
                break;
            case 3:
                switch (difficulty){
                    case 1:
                        key = "OutterGodDawn";
                        break;
                    case 2:
                        key = "OutterGodNoon";
                        break;
                    case 4:
                        key = "OutterGodMidnight";
                        break;
                }
                break;
            case 4:
                switch (difficulty){
                    case 1:
                        key = "CircusDawn";
                        break;
                }
                break;
            case 5:
                switch (difficulty){
                    case 2:
                        key = "Cleaner";
                        break;
                }
                break;
            case 6:
                switch (difficulty){
                    case 1:
                        key = "FixerDawn";
                        CardCrawlGame.music.silenceTempBgmInstantly();
                        CardCrawlGame.music.silenceBGMInstantly();
                        AbstractDungeon.getCurrRoom().playBgmInstantly("emergency01_mast.mp3");
                        break;
                    case 2:
                        key = "FixerNoon";
                        CardCrawlGame.music.silenceTempBgmInstantly();
                        CardCrawlGame.music.silenceBGMInstantly();
                        AbstractDungeon.getCurrRoom().playBgmInstantly("emergency01_mast.mp3");
                        break;
                    case 3:
                        key = "FixerNight";
                        CardCrawlGame.music.silenceTempBgmInstantly();
                        CardCrawlGame.music.silenceBGMInstantly();
                        AbstractDungeon.getCurrRoom().playBgmInstantly("emergency02_mast.mp3");
                        break;
                }
                break;
        }
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(key);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
        AbstractDungeon.getCurrRoom().monsters.init();
        AbstractRoom.waitTimer = 0.1F;
        AbstractDungeon.player.preBattlePrep();
        CardCrawlGame.fadeIn(1.5F);
        AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
        AbstractDungeon.getCurrRoom().rewards.add(new CogitoReward(true));

        AbstractDungeon.effectsQueue.add(new LatterEffect(()->{
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(code, difficulty, false));
        }, (Settings.FAST_MODE? 2.1F: 4.2F)));
    }

    public static void addAbnormalityRelic(AbstractRelic relic){
//        if (UnlockTracker.isRelicSeen(relic.relicId)) {
//            RelicLibrary.seenRelics += 1;
//        }
//        relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
//        addToTierList(relic);
//        RelicLibrary.totalRelicCount += 1;
        RelicLibrary.add(relic);
        RelicViewScreenPatch.abnormalityPool.add(relic);
    }




    public static void openCardRewardsScreen(final ArrayList<AbstractCard> cards, final boolean allowSkip) {
        openCardRewardsScreen(cards, allowSkip, null);
    }

    public static void openCardRewardsScreen(final ArrayList<AbstractCard> cards, final boolean allowSkip, String text) {
        final CardRewardScreen crs = AbstractDungeon.cardRewardScreen;
        crs.rItem = null;
        ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "codex", true);
        ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "draft", false);
        crs.codexCard = null;
        ((SingingBowlButton)ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "bowlButton")).hide();
        if (allowSkip) {
            ((SkipCardButton)ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).show();
        }
        else {
            ((SkipCardButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).hide();
        }
        crs.onCardSelect = true;
        AbstractDungeon.topPanel.unhoverHitboxes();
        crs.rewardGroup = cards;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
        AbstractDungeon.dynamicBanner.appear(text);
        AbstractDungeon.overlayMenu.showBlackScreen();
        final float CARD_TARGET_Y = Settings.HEIGHT * 0.45f;
        try {
            final Method method = CardRewardScreen.class.getDeclaredMethod("placeCards", Float.TYPE, Float.TYPE);
            method.setAccessible(true);
            method.invoke(crs, Settings.WIDTH / 2.0f, CARD_TARGET_Y);
        }
        catch (Exception ignored) {
        }
        for (final AbstractCard c : cards) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }
    }

    public static void hireDepartment(){
//        final ChooseEffect choice = new ChooseEffect(null, null, Angela.TEXT[4], true, 1);
//        final AbstractDepartmentCard[] tmp = {new Malkuth(false)};
//        if(Angela.departments[Malkuth.departmentCode[1]] < 5 && Angela.departments[Malkuth.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Yesod(false);
//        if(Angela.departments[Yesod.departmentCode[1]] < 5 && Angela.departments[Yesod.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Netzach(false);
//        if(Angela.departments[Netzach.departmentCode[1]] < 5 && Angela.departments[Netzach.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Hod(false);
//        if(Angela.departments[Hod.departmentCode[1]] < 5 && Angela.departments[Hod.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Tiphereth(false);
//        if(Angela.departments[Tiphereth.departmentCode[1]] < 5 && Angela.departments[Tiphereth.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Chesed(false);
//        if(Angela.departments[Chesed.departmentCode[1]] < 5 && Angela.departments[Chesed.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Geburah(false);
//        if(Angela.departments[Geburah.departmentCode[1]] < 5 && Angela.departments[Geburah.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Hokma(false);
//        if(Angela.departments[Hokma.departmentCode[1]] < 5 && Angela.departments[Hokma.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        tmp[0] = new Binah(false);
//        if(Angela.departments[Binah.departmentCode[1]] < 5 && Angela.departments[Binah.departmentCode[0]] > 0) {
//            choice.add(tmp[0], tmp[0]::tackAction);
//        }
//        if(Angela.departments[18] >= 5) {
//            choice.add(new ExpandDepartment(), () -> {
//                final ChooseEffect choice2 = new ChooseEffect(null, null, Angela.TEXT[3], true, 1);
//                if (Angela.departments[Malkuth.departmentCode[0]] < 4) {
//                    tmp[0] = new Malkuth(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Yesod.departmentCode[0]] < 4) {
//                    tmp[0] = new Yesod(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Netzach.departmentCode[0]] < 4) {
//                    tmp[0] = new Netzach(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Hod.departmentCode[0]] < 4) {
//                    tmp[0] = new Hod(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Tiphereth.departmentCode[0]] < 4) {
//                    tmp[0] = new Tiphereth(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Chesed.departmentCode[0]] < 4) {
//                    tmp[0] = new Chesed(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Geburah.departmentCode[0]] < 4) {
//                    tmp[0] = new Geburah(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Hokma.departmentCode[0]] < 4) {
//                    tmp[0] = new Hokma(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
//                if (Angela.departments[Binah.departmentCode[0]] < 4) {
//                    tmp[0] = new Binah(true);
//                    choice2.add(tmp[0], tmp[0]::tackAction);
//                }
////            tmp[0] = new Binah(true);
////            if(Angela.departments[Binah.departmentCode[0]] < 4) {
////                choice2.add(tmp[0], tmp[0]::tackAction);
////            }
//                AbstractDungeon.effectsQueue.add(choice2);
//            });
//        }
//        AbstractDungeon.effectsQueue.add(choice);

        AbstractDungeon.effectsQueue.add(new HireEffect());
    }

    public static boolean isAttack(AbstractMonster m){
        return m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_DEFEND
                || m.intent == AbstractMonster.Intent.ATTACK_BUFF || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF;
    }
}
