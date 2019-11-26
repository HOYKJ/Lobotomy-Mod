package lobotomyMod.neow;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.RabbitCall;
import lobotomyMod.ui.LobotomyFtue;

/**
 * @author hoykj
 */
public class GetBucket extends NeowReward
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RewardItem_Lob");
    public static final String[] TEXT = uiStrings.TEXT;
    private static final String DESCRIPTION = TEXT[1];

    public GetBucket()
    {
        super(0);

        this.optionLabel = TEXT[1];
    }

    public void activate()
    {
        if(!AbstractDungeon.player.hasRelic(CogitoBucket.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, RelicLibrary.getRelic(CogitoBucket.ID).makeCopy());
        }
        if(LobotomyMod.activeRabbit && !AbstractDungeon.player.hasRelic(RabbitCall.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, RelicLibrary.getRelic(RabbitCall.ID).makeCopy());
        }
        if(LobotomyMod.activeTutorials[0]) {
            AbstractDungeon.ftue = new LobotomyFtue(0);
            LobotomyMod.activeTutorials[0] = false;
        }
    }
}
