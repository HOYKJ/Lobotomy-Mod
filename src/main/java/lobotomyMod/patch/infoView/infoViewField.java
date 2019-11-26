package lobotomyMod.patch.infoView;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

/**
 * @author hoykj
 */

@SpirePatch(
        clz= SingleCardViewPopup.class,
        method= SpirePatch.CLASS
)
public class infoViewField {
    public static SpireField<Hitbox> lastInfo = new SpireField<>(() -> null);
    public static SpireField<Hitbox> lock = new SpireField<>(() -> null);
    public static SpireField<Hitbox> nextLock = new SpireField<>(() -> null);
    public static SpireField<Hitbox> nextInfo = new SpireField<>(() -> null);
}
