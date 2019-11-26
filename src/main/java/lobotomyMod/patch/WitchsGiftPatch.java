package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.uncommonCard.BigBird;
import lobotomyMod.card.uncommonCard.Laetitia;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class WitchsGiftPatch {
    public static Texture img = LobotomyImageMaster.WITCH_GIFT;
    public static Texture img2 = LobotomyImageMaster.WITCH_GIFT_2;

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
                if (card instanceof Laetitia) {
                    if(((Laetitia) card).list.contains(_inst)){
                        if((AbstractDungeon.player.hoveredCard == _inst) && (AbstractDungeon.player.hand.contains(_inst))){
                            heart2(_inst, sb);
                            return;
                        }
                        heart(_inst, sb);
                        return;
                    }
                }
            }
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof Laetitia) {
                    if(((Laetitia) card).list.contains(_inst)){
                        heart(_inst, sb);
                        return;
                    }
                }
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof Laetitia) {
                    if(((Laetitia) card).list.contains(_inst)){
                        if((AbstractDungeon.player.hoveredCard == _inst) && (AbstractDungeon.player.hand.contains(_inst))){
                            heart2(_inst, sb);
                            return;
                        }
                        heart(_inst, sb);
                        return;
                    }
                }
            }
        }

        public static void heart(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, img, _inst.current_x - 512.0F, _inst.current_y - 482.0F);
        }

        public static void heart2(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, img2, _inst.current_x - 512.0F, _inst.current_y - 482.0F);
        }
    }
}
