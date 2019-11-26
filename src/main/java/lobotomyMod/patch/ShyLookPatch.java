package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.commonCard.ShyLook;
import lobotomyMod.card.rareCard.CENSORED;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class ShyLookPatch {

    @SpirePatch(
            clz= AbstractCard.class,
            method="renderEnergy"
    )
    public static class renderEnergy {
        @SpirePostfixPatch
        public static void postfix(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if(AbstractDungeon.player == null){
                return;
            }

            if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD){
                return;
            }

            if ((_inst instanceof ShyLook) && (((ShyLook) _inst).face != 0)){
                shyLook(_inst, sb, ((ShyLook) _inst).face);
            }
        }

        public static void shyLook(AbstractCard _inst, SpriteBatch sb, int code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, LobotomyImageMaster.SHY_FACE[code - 1], _inst.current_x - 512.0F, _inst.current_y - 512.0F);
        }
    }
}
