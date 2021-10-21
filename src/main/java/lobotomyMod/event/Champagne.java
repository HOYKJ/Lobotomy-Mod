package lobotomyMod.event;

import basemod.helpers.SuperclassFinder;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.LobotomyMod;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.RabbitCall;
import lobotomyMod.vfx.AutoPlayStory;
import lobotomyMod.vfx.action.LatterEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class Champagne extends AbstractImageEvent {
    public static final String ID = "Champagne";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Champagne");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[0];
    private CurScreen screen = CurScreen.INTRO;

    private static enum CurScreen {
        INTRO, INTRO2, INTRO3, INTRO4, NEXT, NEXT2, FINAL
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        if(AbstractDungeon.scene instanceof TheBottomScene){
            try {
                Field torches = SuperclassFinder.getSuperclassField(AbstractDungeon.scene.getClass(), "torches");
                torches.setAccessible(true);
                ((ArrayList)torches.get(AbstractDungeon.scene)).clear();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("story-02_ogg.mp3");
        LobotomyMod.deadTime -= 1;
    }

    public Champagne(){
        super(NAME, INTRO_MSG, "lobotomyMod/images/events/angelaCG_1.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = CurScreen.INTRO2;
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.imageEventText.loadImage("lobotomyMod/images/events/angelaCG_2.png");
                break;
            case INTRO2:
                this.screen = CurScreen.INTRO3;
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                break;
            case INTRO3:
                this.screen = CurScreen.INTRO4;
                this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.imageEventText.loadImage("lobotomyMod/images/events/angelaCG_3.png");
                break;
            case INTRO4:
                this.screen = CurScreen.NEXT;
                if(buttonPressed == 0) {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                }
                else {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    this.imageEventText.loadImage("lobotomyMod/images/events/angelaCG_2.png");
                }
                this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                this.imageEventText.clearRemainingOptions();
                break;
            case NEXT:
                this.screen = CurScreen.NEXT2;
                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                if(AbstractDungeon.player.hasRelic(CogitoBucket.ID)){
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                }
                else {
                    this.imageEventText.updateDialogOption(0, OPTIONS[7], true);
                }
                this.imageEventText.setDialogOption(OPTIONS[4]);
                this.imageEventText.setDialogOption(OPTIONS[5]);
                break;
            case NEXT2:
                this.screen = CurScreen.FINAL;
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                switch (buttonPressed){
                    case 0:
                        AbstractDungeon.player.getRelic(CogitoBucket.ID).counter += 100;
                        break;
                    case 1:
                        AbstractDungeon.player.gainGold(160);
                        break;
                    case 2:
                        if(AbstractDungeon.player.hasRelic(RabbitCall.ID)){
                            AbstractDungeon.player.getRelic(RabbitCall.ID).counter ++;
                        }
                        else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, RelicLibrary.getRelic(RabbitCall.ID).makeCopy());
                        }
                        break;
                }
                this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                this.imageEventText.clearRemainingOptions();
                break;
            case FINAL:
                openMap();
                break;
        }
    }
}
