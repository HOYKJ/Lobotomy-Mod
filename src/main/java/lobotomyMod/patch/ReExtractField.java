package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import lobotomyMod.ui.buttons.ReExtractButton;

/**
 * @author hoykj
 */

@SpirePatch(
        clz= CardRewardScreen.class,
        method= SpirePatch.CLASS
)
public class ReExtractField {
    public static SpireField<ReExtractButton> reExtractButton = new SpireField<>(ReExtractButton::new);
}
