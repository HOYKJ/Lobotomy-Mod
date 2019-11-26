package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import lobotomyMod.card.commonCard.MHz;
import lobotomyMod.card.uncommonCard.BigBird;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class BigBirdFocusPatch {
    public static Texture img = ImageMaster.loadImage("lobotomyMod/images/texture/BigBirdFocus.png");

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

            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof BigBird) {
                    if(((BigBird) card).focus1.contains(_inst)){
                        focus(_inst, sb);
                        return;
                    }
                }
                if (card instanceof BigBird) {
                    if(((BigBird) card).focus2.contains(_inst)){
                        focus2(_inst, sb);
                        return;
                    }
                }
            }
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof BigBird) {
                    if(((BigBird) card).focus1.contains(_inst)){
                        focus(_inst, sb);
                        return;
                    }
                }
                if (card instanceof BigBird) {
                    if(((BigBird) card).focus2.contains(_inst)){
                        focus2(_inst, sb);
                        return;
                    }
                }
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof BigBird) {
                    if(((BigBird) card).focus1.contains(_inst)){
                        focus(_inst, sb);
                        return;
                    }
                }
                if (card instanceof BigBird) {
                    if(((BigBird) card).focus2.contains(_inst)){
                        focus2(_inst, sb);
                        return;
                    }
                }
            }
        }

        public static void focus(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, img, _inst.current_x - 512.0F, _inst.current_y - 256.0F);
        }

        public static void focus2(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.RED.cpy();

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, img, _inst.current_x - 512.0F, _inst.current_y - 256.0F);
        }
    }
}
