package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import lobotomyMod.relic.AbstractLobotomyRelic;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class SingleRelicViewPopupPatch {
    @SpirePatch(
            clz= SingleRelicViewPopup.class,
            method="generateFrameImg"
    )
    public static class generateFrameImg {
        @SpirePrefixPatch
        public static void prefix(SingleRelicViewPopup _inst) throws IllegalAccessException, NoSuchFieldException {
            Field relic = _inst.getClass().getDeclaredField("relic");
            Field relicFrameImg = _inst.getClass().getDeclaredField("relicFrameImg");
            relic.setAccessible(true);
            relicFrameImg.setAccessible(true);
            if((relic.get(_inst) instanceof AbstractLobotomyRelic) && (((AbstractLobotomyRelic)relic.get(_inst)).tier == RelicTierEnum.Abnormality)){
                switch (((AbstractLobotomyRelic)relic.get(_inst)).hideTier)
                {
                    case COMMON:
                        relicFrameImg.set(_inst, ImageMaster.loadImage("images/ui/relicFrameCommon.png"));
                        break;
                    case RARE:
                        relicFrameImg.set(_inst, ImageMaster.loadImage("images/ui/relicFrameRare.png"));
                        break;
                    case UNCOMMON:
                        relicFrameImg.set(_inst, ImageMaster.loadImage("images/ui/relicFrameUncommon.png"));
                        break;
                }
            }
        }
    }
}
