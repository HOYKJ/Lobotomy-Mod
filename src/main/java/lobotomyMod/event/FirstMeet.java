package lobotomyMod.event;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.AutoPlayStory;
import lobotomyMod.vfx.action.LatterEffect;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class FirstMeet extends AbstractEvent {
    public static final String ID = "FirstMeet";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("FirstMeet");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[1];
    private CurScreen screen = CurScreen.INTRO;
    private AnimatedNpc npc;

    private static enum CurScreen
    {
        INTRO, INTRO2, INTRO3, NEXT, LEAVE;

        private CurScreen() {}
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("op-theme-ogg.mp3");
        AbstractDungeon.player.drawY -= 20;
        LobotomyMod.activeChampagne = true;
    }

    public FirstMeet(){
        this.roomEventText.clear();
        this.roomEventText.show();
        this.body = INTRO_MSG;
        this.roomEventText.show(this.body);
        this.roomEventText.addDialogOption(OPTIONS[0]);
        this.npc = new AnimatedNpc(1534.0F * Settings.scale, 280.0F * Settings.scale, "lobotomyMod/images/characters/angela/angela_white/idle/angela_white.atlas", "lobotomyMod/images/characters/angela/angela_white/idle/angela_white.json", "newAnimation");
        try {
            Method loadAnimation = this.npc.getClass().getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
            loadAnimation.setAccessible(true);
            loadAnimation.invoke(this.npc, "lobotomyMod/images/characters/angela/angela_white/idle/angela_white.atlas", "lobotomyMod/images/characters/angela/angela_white/idle/angela_white.json", 2.8F);

            this.npc.skeleton.setPosition(1460.0F * Settings.scale, 280.0F * Settings.scale);
            Field state = this.npc.getClass().getDeclaredField("state");
            Field stateData = this.npc.getClass().getDeclaredField("stateData");
            state.setAccessible(true);
            stateData.setAccessible(true);
            ((AnimationState)state.get(this.npc)).setAnimation(0, "newAnimation", true);
            ((AnimationState)state.get(this.npc)).setTimeScale(0.6F);
            ((AnimationStateData)stateData.get(this.npc)).setMix("newAnimation", "smile", 0.2F);
            this.npc.skeleton.setFlip(true, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }

//        CardCrawlGame.music.silenceTempBgmInstantly();
//        CardCrawlGame.music.silenceBGMInstantly();
//        AbstractDungeon.getCurrRoom().playBgmInstantly("op-theme-ogg.mp3");
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = CurScreen.INTRO2;
                this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
                break;
            case INTRO2:
                this.screen = CurScreen.INTRO3;
                this.roomEventText.updateBodyText(DESCRIPTIONS[3]);

                try {
                    Field state = this.npc.getClass().getDeclaredField("state");
                    state.setAccessible(true);
                    ((AnimationState)state.get(this.npc)).setAnimation(0, "smile", true);
                    ((AnimationState)state.get(this.npc)).setTimeScale(0.8F);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case INTRO3:
                this.screen = CurScreen.NEXT;
                this.roomEventText.updateBodyText(DESCRIPTIONS[4]);
//                this.roomEventText.updateDialogOption(0, OPTIONS[1]);
//                this.roomEventText.addDialogOption(OPTIONS[2]);
                this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                if(unlockEnough(0.6F)) {
                    this.roomEventText.addDialogOption(OPTIONS[1]);
                }
                else {
                    this.roomEventText.addDialogOption(OPTIONS[7], true);
                }
                //this.roomEventText.addDialogOption(OPTIONS[1]);
                if(unlockEnough(0.3F)) {
                    this.roomEventText.addDialogOption(OPTIONS[2]);
                }
                else {
                    this.roomEventText.addDialogOption(OPTIONS[6], true);
                }
                break;
            case NEXT:
                if(buttonPressed == 2){
                    LobotomyMod.activeFixer = true;
                }
                else if(buttonPressed == 1){
                    AbstractDungeon.player.loseRelic(CogitoBucket.ID);
                    LobotomyMod.activeAngela = true;
                    try {
                        LobotomyMod.saveData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.screen = CurScreen.LEAVE;
                this.roomEventText.updateBodyText(DESCRIPTIONS[5]);
                this.roomEventText.updateDialogOption(0, OPTIONS[5]);
                this.roomEventText.clearRemainingOptions();
                break;
            case LEAVE:
                if(LobotomyMod.deadTime >= 3) {
                    AbstractDungeon.topLevelEffects.clear();
                    AbstractDungeon.effectList.clear();
                    AbstractDungeon.getCurrRoom().event = new Champagne();
                    AbstractDungeon.getCurrRoom().event.onEnterRoom();
                    if (AbstractDungeon.scene instanceof TheBottomScene) {
                        try {
                            Field torches = SuperclassFinder.getSuperclassField(AbstractDungeon.scene.getClass(), "torches");
                            torches.setAccessible(true);
                            ((ArrayList) torches.get(AbstractDungeon.scene)).clear();
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    CardCrawlGame.fadeIn(1.5F);
                    AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;
                    AbstractDungeon.closeCurrentScreen();
                    return;
                }
                openMap();
                break;
        }
    }

    public void render(SpriteBatch sb)
    {
        //this.npc.render(sb);
        Field state = null;
        try {
            state = this.npc.getClass().getDeclaredField("state");
            state.setAccessible(true);
            ((AnimationState)state.get(this.npc)).update(Gdx.graphics.getDeltaTime());
            ((AnimationState)state.get(this.npc)).apply(this.npc.skeleton);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.npc.skeleton.updateWorldTransform();
        this.npc.skeleton.setColor(Color.WHITE);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.npc.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
        this.renderRoomEventPanel(sb);
    }

    public static boolean unlockEnough(float percent){
        int unlock = 0, sum = 0;
        for(AbstractLobotomyCard card : LobotomyHandler.abnormalityListCommon){
            sum ++;
            if(CogitoBucket.level[card.AbnormalityID] == card.maxInfo){
                unlock ++;
            }
        }
        for(AbstractLobotomyCard card : LobotomyHandler.abnormalityListUncommon){
            sum ++;
            if(CogitoBucket.level[card.AbnormalityID] == card.maxInfo){
                unlock ++;
            }
        }
        for(AbstractLobotomyCard card : LobotomyHandler.abnormalityListRare){
            sum ++;
            if(CogitoBucket.level[card.AbnormalityID] == card.maxInfo){
                unlock ++;
            }
        }

        return unlock / (float) sum >= percent;
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg {
        @SpirePostfixPatch
        public static void postfix(TheBottomScene _inst, SpriteBatch sb){
            if(!(AbstractDungeon.getCurrRoom().event instanceof FirstMeet)){
                return;
            }
            Texture img = LobotomyImageMaster.ANGELA_BACKGROUND;
            //- (img.getHeight() / 1.0F / Settings.HEIGHT * img.getWidth() - Settings.WIDTH) / 2
            //img.getHeight() / 1.0F / Settings.HEIGHT * img.getWidth()
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBottomScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().event instanceof FirstMeet){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
