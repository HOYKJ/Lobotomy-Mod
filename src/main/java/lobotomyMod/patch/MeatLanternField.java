package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.events.city.TheLibrary;

/**
 * @author hoykj
 */

@SpirePatch(
        clz= AbstractCard.class,
        method= SpirePatch.CLASS
)
public class MeatLanternField {
    public static SpireField<Boolean> hasLantern = new SpireField<>(() -> false);
}
