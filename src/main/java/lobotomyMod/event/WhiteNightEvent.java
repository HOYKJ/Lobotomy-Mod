package lobotomyMod.event;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import lobotomyMod.card.commonCard.MHz;
import lobotomyMod.relic.ApostleMask;
import lobotomyMod.relic.DeathAngel;

/**
 * @author hoykj
 */
public class WhiteNightEvent extends AbstractEvent {
    public static final String ID = "WhiteNightEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("WhiteNightEvent");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[0];
    private float x = 800.0F * Settings.scale;
    private float y = AbstractDungeon.floorY;
    private CurScreen screen = CurScreen.INTRO;
    private boolean re;

    private enum CurScreen
    {
        INTRO, NEXT, CHOOSE, LAST, LEAVE;

        CurScreen() {}
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Lucifer_Advent1.mp3");
    }

    public WhiteNightEvent(boolean re){
        this.body = INTRO_MSG;
        this.roomEventText.addDialogOption(OPTIONS[0]);

        this.hasDialog = true;
        this.hasFocus = true;
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("WhiteNightMonster");
        this.re = re;
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen)
        {
            case INTRO:
                this.screen = CurScreen.NEXT;
                this.roomEventText.updateBodyText(DESCRIPTIONS[1]);
                CardCrawlGame.sound.play("Lucifer_Bell0");
                break;
            case NEXT:
                this.screen = CurScreen.CHOOSE;
                this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
                this.roomEventText.updateDialogOption(0, OPTIONS[1]);
                this.roomEventText.addDialogOption(OPTIONS[3]);
                this.roomEventText.addDialogOption(OPTIONS[2]);
                CardCrawlGame.sound.play("Lucifer_Bell0");
                break;
            case CHOOSE:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.LEAVE;
                        this.roomEventText.updateBodyText(DESCRIPTIONS[6]);
                        this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                        this.roomEventText.clearRemainingOptions();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.x, this.y,  RelicLibrary.getRelic(ApostleMask.ID).makeCopy());
                        CardCrawlGame.sound.play("Lucifer_Bell0");
                        break;
                    case 1:
                        CardCrawlGame.fadeIn(1.5F);
                        if(this.re){
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new DeathAngel());
                        }
                        enterCombat();
                        break;
                    case 2:
                        if (AbstractDungeon.player.masterDeck.findCardById(MHz.ID) != null) {
                            this.screen = CurScreen.LEAVE;
                            this.roomEventText.updateBodyText(DESCRIPTIONS[4]);
                            this.roomEventText.updateDialogOption(0, OPTIONS[0]);
                            this.roomEventText.clearRemainingOptions();
                            return;
                        }
                        this.screen = CurScreen.LAST;
                        this.roomEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.roomEventText.clearRemainingOptions();
                        this.roomEventText.addDialogOption(OPTIONS[3]);
                        CardCrawlGame.sound.play("Lucifer_Bell0");
                        break;
                }
                break;
            case LAST:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.LEAVE;
                        this.roomEventText.updateBodyText(DESCRIPTIONS[6]);
                        this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                        this.roomEventText.clearRemainingOptions();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.x, this.y,  RelicLibrary.getRelic(ApostleMask.ID).makeCopy());
                        CardCrawlGame.sound.play("Lucifer_Bell0");
                        break;
                    case 1:
                        CardCrawlGame.fadeIn(1.5F);
                        if(this.re){
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new DeathAngel());
                        }
                        enterCombat();
                        break;
                }
                break;
            case LEAVE:
                openMap();
                break;
        }
    }

    public void render(SpriteBatch sb) {
        super.render(sb);
    }
}
