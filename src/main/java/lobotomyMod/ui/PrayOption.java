package lobotomyMod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import lobotomyMod.vfx.action.CampfirePrayEffect;

/**
 * @author hoykj
 */
public class PrayOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PrayOption");
    public static final String[] TEXT = uiStrings.TEXT;

    public static Texture GetUpgradeOptionTexture()
    {
        return new Texture("lobotomyMod/images/ui/campfire/pray.png");
    }

    public PrayOption(boolean active)
    {
        this.label = TEXT[0];
        this.usable = active;
        if (active) {
            this.description = TEXT[1];
            this.img = GetUpgradeOptionTexture();
        } else {
            this.description = TEXT[1];
            this.img = GetUpgradeOptionTexture();
        }
    }

    public void useOption()
    {
        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfirePrayEffect());
        }
    }
}
