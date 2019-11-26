package lobotomyMod.event;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.LobotomyMod;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.relic.ApostleMask;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.RabbitCall;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class RabbitProtocol extends AbstractEvent {
    public static final String ID = "RabbitProtocol";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("RabbitProtocol");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[0];
    private CurScreen screen = CurScreen.INTRO;

    private static enum CurScreen
    {
        INTRO, INTRO2, INTRO3, NEXT, LEAVE;

        private CurScreen() {}
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        //CardCrawlGame.music.silenceTempBgmInstantly();
        //CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.player.drawX = -10000;
    }

    public RabbitProtocol(){
        this.roomEventText.clear();
        this.roomEventText.show();
        this.body = INTRO_MSG;
        this.roomEventText.show(this.body);
        this.roomEventText.addDialogOption(OPTIONS[0]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = CurScreen.INTRO2;
                this.roomEventText.updateBodyText(DESCRIPTIONS[1]);
                break;
            case INTRO2:
                this.screen = CurScreen.INTRO3;
                this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
                this.roomEventText.updateDialogOption(0, OPTIONS[1]);
                break;
            case INTRO3:
                this.screen = CurScreen.NEXT;
                this.roomEventText.updateBodyText(DESCRIPTIONS[3]);
                break;
            case NEXT:
                this.screen = CurScreen.LEAVE;
                this.roomEventText.updateBodyText(DESCRIPTIONS[4]);
                this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, RelicLibrary.getRelic(RabbitCall.ID).makeCopy());
                LobotomyMod.activeRabbit = true;
                try {
                    LobotomyMod.saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case LEAVE:
                this.openMap();
                break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE.cpy());
        Texture img = LobotomyImageMaster.RABBIT_HALMET;
        sb.draw(img, Settings.WIDTH - 500, -200, img.getWidth() / 2.0F, img.getHeight() / 2.0F);
        this.renderRoomEventPanel(sb);
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg {
        @SpirePostfixPatch
        public static void postfix(TheBottomScene _inst, SpriteBatch sb){
            if(!(AbstractDungeon.getCurrRoom().event instanceof RabbitProtocol)){
                return;
            }
            Texture img = ImageMaster.WHITE_SQUARE_IMG;
            sb.setColor(Color.BLACK.cpy());
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
            if(AbstractDungeon.getCurrRoom().event instanceof RabbitProtocol){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
