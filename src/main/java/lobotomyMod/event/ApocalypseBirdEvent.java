package lobotomyMod.event;

import basemod.helpers.SuperclassFinder;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.vfx.AutoPlayStory;
import lobotomyMod.vfx.action.LatterEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class ApocalypseBirdEvent extends AbstractImageEvent {
    public static final String ID = "ApocalypseBirdEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("ApocalypseBirdEvent");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[0];
    private CurScreen screen = CurScreen.INTRO;

    private static enum CurScreen
    {
        INTRO, NEXT, FINAL;

        private CurScreen() {}
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
        AbstractDungeon.getCurrRoom().playBgmInstantly("Blue_Dots.mp3");
    }

    public ApocalypseBirdEvent(boolean skip){
        super(NAME, INTRO_MSG, "lobotomyMod/images/events/blackForestCG.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        if(skip) {
            this.imageEventText.setDialogOption(OPTIONS[1]);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.NEXT;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        break;
                    case 1:
                        this.screen = CurScreen.FINAL;
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("ApocalypseBird");
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        enterCombatFromImage();
                        break;
                }
                break;
            case NEXT:
                this.screen = CurScreen.FINAL;
                AbstractDungeon.effectList.add(new AutoPlayStory());
                AbstractDungeon.effectList.add(new LatterEffect(()->{
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("ApocalypseBird");
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    enterCombatFromImage();
                }, 24.0F));
                GenericEventDialog.hide();
                break;
            case FINAL:
                break;
        }
    }
}
