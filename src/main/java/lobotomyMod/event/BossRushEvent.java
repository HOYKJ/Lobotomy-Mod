package lobotomyMod.event;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import lobotomyMod.LobotomyMod;
import lobotomyMod.room.NewVictoryRoom;

import java.io.IOException;

/**
 * @author hoykj
 */
public class BossRushEvent extends AbstractImageEvent {
    public static final String ID = "BossRushEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("BossRushEvent");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[0];
    private CurScreen screen = CurScreen.INTRO;
    private boolean heal, hovered;
    private int currentImg, previewImg;

    private static enum CurScreen {
        INTRO, INTRO2, INTRO3, NEXT, NEXT2, NEXT3, FINAL, FINAL2, LEAVE, VICTORY
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
    }

    public BossRushEvent(){
        super(NAME, INTRO_MSG, "lobotomyMod/images/events/bossRush_0.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.heal = false;
        this.currentImg = 0;
        this.previewImg = 0;
        if(LobotomyMod.meltdownCode != 0){
            load();
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = CurScreen.INTRO2;
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                break;
            case INTRO2:
                if(buttonPressed == 0){
                    this.screen = CurScreen.INTRO3;
                    this.imageEventText.updateDialogOption(0, OPTIONS[3] + OPTIONS[7]);
                }
                else {
                    this.screen = CurScreen.LEAVE;
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                }
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                this.imageEventText.clearRemainingOptions();
                break;
            case INTRO3:
                this.screen = CurScreen.NEXT;
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Malkuth");
                AbstractDungeon.lastCombatMetricKey = "Malkuth";
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().rewardAllowed = false;
                enterCombatFromImage();
                this.heal = true;
                break;
            case NEXT:
                if(this.heal){
                    AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth * 0.5F));
                    this.heal = false;
                    this.imageEventText.updateDialogOption(0, OPTIONS[3] + OPTIONS[9]);
                    this.imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[8]);
                    this.imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[10]);
                }
                else {
                    this.screen = CurScreen.NEXT2;
                    switch (buttonPressed){
                        case 0:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Netzach");
                            AbstractDungeon.lastCombatMetricKey = "Netzach";
                            break;
                        case 1:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Yesod");
                            AbstractDungeon.lastCombatMetricKey = "Yesod";
                            break;
                        case 2:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Hod");
                            AbstractDungeon.lastCombatMetricKey = "Hod";
                            break;
                    }
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().rewardAllowed = false;
                    enterCombatFromImage();
                    this.heal = true;
                }
                break;
            case NEXT2:
                if(this.heal){
                    AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth * 0.5F));
                    this.heal = false;
                    this.imageEventText.updateDialogOption(0, OPTIONS[3] + OPTIONS[12]);
                    this.imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[11]);
                    this.imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[13]);
                }
                else {
                    this.screen = CurScreen.NEXT3;
                    switch (buttonPressed){
                        case 0:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Chesed");
                            AbstractDungeon.lastCombatMetricKey = "Chesed";
                            break;
                        case 1:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Tiphereth");
                            AbstractDungeon.lastCombatMetricKey = "Tiphereth";
                            break;
                        case 2:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Geburah");
                            AbstractDungeon.lastCombatMetricKey = "Geburah";
                            break;
                    }
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().rewardAllowed = false;
                    enterCombatFromImage();
                    this.heal = true;
                }
                break;
            case NEXT3:
                if(this.heal){
                    AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth * 0.5F));
                    this.heal = false;
                    this.imageEventText.updateDialogOption(0, OPTIONS[3] + OPTIONS[14]);
                    this.imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[15]);
                }
                else {
                    this.screen = CurScreen.FINAL;
                    switch (buttonPressed){
                        case 0:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Hokma");
                            AbstractDungeon.lastCombatMetricKey = "Hokma";
                            break;
                        case 1:
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Binah");
                            AbstractDungeon.lastCombatMetricKey = "Binah";
                            break;
                    }
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().rewardAllowed = false;
                    enterCombatFromImage();
                    this.heal = true;
                }
                break;
            case FINAL:
                if(this.heal){
                    AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth * 0.5F));
                    this.heal = false;
                    this.currentImg = 1;
                    this.imageEventText.loadImage(this.getImgString());
                    this.imageEventText.updateBodyText(DESCRIPTIONS[12]);
                    this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                    this.imageEventText.setDialogOption(OPTIONS[0]);
                    this.imageEventText.setDialogOption(OPTIONS[0]);
                }
                else{
                    this.screen = CurScreen.FINAL2;
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Angela");
                    AbstractDungeon.lastCombatMetricKey = "Angela";
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().rewardAllowed = false;
                    enterCombatFromImage();
                }
                break;
            case FINAL2:
                this.screen = CurScreen.VICTORY;
                this.imageEventText.updateBodyText(DESCRIPTIONS[14]);
                this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                break;
            case LEAVE:
                LobotomyMod.meltdownCode = -1;
                try {
                    LobotomyMod.saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = new VictoryRoom(VictoryRoom.EventType.HEART);
                AbstractDungeon.nextRoom = node;
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.nextRoomTransitionStart();
                break;
            case VICTORY:
//                MapRoomNode node1 = new MapRoomNode(-1, 15);
//                node1.room = new NewVictoryRoom(VictoryRoom.EventType.HEART);
//                AbstractDungeon.nextRoom = node1;
//                AbstractDungeon.closeCurrentScreen();
//                AbstractDungeon.nextRoomTransitionStart();
                AbstractDungeon.player.isDying = true;
                this.hasFocus = false;
                this.roomEventText.hide();
                AbstractDungeon.player.isDead = true;
                AbstractDungeon.deathScreen = new DeathScreen(null);
                break;
        }
    }

    @Override
    public void update() {
        if(!this.heal){
            if(this.imageEventText.optionList.size() < 1){
                return;
            }
            boolean flag = false;
            switch (this.screen){
                case INTRO3:
                    this.imageEventText.optionList.get(0).hb.update();
                    if(this.imageEventText.optionList.get(0).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 2;
                            this.imageEventText.loadImage(this.getPreImgString(2));
                        }
                    }
                    else {
                        if(this.hovered) {
                            flag = true;
                        }
                    }
                    break;
                case NEXT:
                    this.imageEventText.optionList.get(0).hb.update();
                    this.imageEventText.optionList.get(1).hb.update();
                    this.imageEventText.optionList.get(2).hb.update();
                    if(this.imageEventText.optionList.get(0).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 4;
                            this.imageEventText.loadImage(this.getPreImgString(4));
                        }
                        else if(this.previewImg != 4){
                            this.previewImg = 4;
                            this.imageEventText.loadImage(this.getPreImgString(4));
                        }
                    }
                    else if(this.imageEventText.optionList.get(1).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 3;
                            this.imageEventText.loadImage(this.getPreImgString(3));
                        }
                        else if(this.previewImg != 3){
                            this.previewImg = 3;
                            this.imageEventText.loadImage(this.getPreImgString(3));
                        }
                    }
                    else if(this.imageEventText.optionList.get(2).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 5;
                            this.imageEventText.loadImage(this.getPreImgString(5));
                        }
                        else if(this.previewImg != 5){
                            this.previewImg = 5;
                            this.imageEventText.loadImage(this.getPreImgString(5));
                        }
                    }
                    else {
                        if(this.hovered) {
                            flag = true;
                        }
                    }
                    break;
                case NEXT2:
                    this.imageEventText.optionList.get(0).hb.update();
                    this.imageEventText.optionList.get(1).hb.update();
                    this.imageEventText.optionList.get(2).hb.update();
                    if(this.imageEventText.optionList.get(0).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 7;
                            this.imageEventText.loadImage(this.getPreImgString(7));
                        }
                        else if(this.previewImg != 7){
                            this.previewImg = 7;
                            this.imageEventText.loadImage(this.getPreImgString(7));
                        }
                    }
                    else if(this.imageEventText.optionList.get(1).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 6;
                            this.imageEventText.loadImage(this.getPreImgString(6));
                        }
                        else if(this.previewImg != 6){
                            this.previewImg = 6;
                            this.imageEventText.loadImage(this.getPreImgString(6));
                        }
                    }
                    else if(this.imageEventText.optionList.get(2).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 8;
                            this.imageEventText.loadImage(this.getPreImgString(8));
                        }
                        else if(this.previewImg != 8){
                            this.previewImg = 8;
                            this.imageEventText.loadImage(this.getPreImgString(8));
                        }
                    }
                    else {
                        if(this.hovered) {
                            flag = true;
                        }
                    }
                    break;
                case NEXT3:
                    this.imageEventText.optionList.get(0).hb.update();
                    this.imageEventText.optionList.get(1).hb.update();
                    if(this.imageEventText.optionList.get(0).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 9;
                            this.imageEventText.loadImage(this.getPreImgString(9));
                        }
                        else if(this.previewImg != 9){
                            this.previewImg = 9;
                            this.imageEventText.loadImage(this.getPreImgString(9));
                        }
                    }
                    else if(this.imageEventText.optionList.get(1).hb.hovered){
                        if(!this.hovered) {
                            this.hovered = true;
                            this.previewImg = 10;
                            this.imageEventText.loadImage(this.getPreImgString(10));
                        }
                        else if(this.previewImg != 10){
                            this.previewImg = 10;
                            this.imageEventText.loadImage(this.getPreImgString(10));
                        }
                    }
                    else {
                        if(this.hovered) {
                            flag = true;
                        }
                    }
                    break;
            }
            if(this.hovered && flag) {
                this.hovered = false;
                this.imageEventText.loadImage(this.getImgString());
            }
        }
        super.update();
    }

    @Override
    public void reopen() {
        super.reopen();
        AbstractDungeon.resetPlayer();
        AbstractDungeon.player.drawX = (Settings.WIDTH * 0.25F);
        AbstractDungeon.player.preBattlePrep();
        enterImageFromCombat();
        switch (AbstractDungeon.lastCombatMetricKey){
            case "Malkuth":
                this.currentImg = 2;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                this.save(1);
                break;
            case "Yesod":
                this.currentImg = 3;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                this.save(2);
                break;
            case "Netzach":
                this.currentImg = 4;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                this.save(3);
                break;
            case "Hod":
                this.currentImg = 5;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                this.save(4);
                break;
            case "Tiphereth":
                this.currentImg = 6;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.save(5);
                break;
            case "Chesed":
                this.currentImg = 7;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[8]);
                this.save(6);
                break;
            case "Geburah":
                this.currentImg = 8;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[9]);
                this.save(7);
                break;
            case "Hokma":
                this.currentImg = 9;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[10]);
                this.save(8);
                break;
            case "Binah":
                this.currentImg = 10;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.save(9);
                break;
            case "Angela":
                this.currentImg = 1;
                this.imageEventText.loadImage(this.getImgString());
                this.imageEventText.updateBodyText(DESCRIPTIONS[13]);
                this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                this.imageEventText.clearRemainingOptions();
                break;
        }
        if(!AbstractDungeon.lastCombatMetricKey.equals("Angela")){
            this.imageEventText.updateDialogOption(0, OPTIONS[5]);
            this.imageEventText.clearRemainingOptions();
        }
    }

    private String getImgString(){
        return "lobotomyMod/images/events/bossRush_" + this.currentImg + ".png";
    }

    private String getImgString(int code){
        return "lobotomyMod/images/events/bossRush_" + code + ".png";
    }

    private String getPreImgString(int code){
        return "lobotomyMod/images/events/bossRush_p_" + code + ".png";
    }

    private void save(int code){
        SaveHelper.saveIfAppropriate(SaveFile.SaveType.ENTER_ROOM);
        LobotomyMod.meltdownCode = code;
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(){
        this.heal = true;
        switch (LobotomyMod.meltdownCode){
            case 1:
                this.screen = CurScreen.NEXT;
                break;
            case 2: case 3: case 4:
                this.screen = CurScreen.NEXT2;
                break;
            case 5: case 6: case 7:
                this.screen = CurScreen.NEXT3;
                break;
            case 8: case 9:
                this.screen = CurScreen.FINAL;
                break;
        }
        this.currentImg = LobotomyMod.meltdownCode + 1;
        this.imageEventText.loadImage(this.getImgString());
        //this.imageEventText.updateBodyText(DESCRIPTIONS[LobotomyMod.meltdownCode + 2]);
        this.body = DESCRIPTIONS[LobotomyMod.meltdownCode + 2];
        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
        this.imageEventText.clearRemainingOptions();
    }

    @SpirePatch(
            clz= ProceedButton.class,
            method="goToVictoryRoomOrTheDoor"
    )
    public static class goToVictoryRoomOrTheDoor {
        @SpirePrefixPatch
        public static SpireReturn prefix(ProceedButton _inst){
            if(LobotomyMod.activeAngela && LobotomyMod.challengeEvent){
//                CardCrawlGame.music.fadeOutBGM();
//                CardCrawlGame.music.fadeOutTempBGM();
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = new NewVictoryRoom(VictoryRoom.EventType.HEART);
                //node.room.event = new BossRushEvent();
                AbstractDungeon.nextRoom = node;
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.nextRoomTransitionStart();
                _inst.hide();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
