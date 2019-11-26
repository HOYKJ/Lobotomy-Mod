package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.rareCard.MeltingLove;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class MeltigLovePatch {

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

            for(AbstractCard card : AbstractDungeon.player.drawPile.group){
                if(card instanceof MeltingLove){
                    if((((MeltingLove) card).heart != null) && (((MeltingLove) card).heart == _inst)){
                        heart(_inst, sb);
                        return;
                    }
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                if(card instanceof MeltingLove){
                    if((((MeltingLove) card).heart != null) && (((MeltingLove) card).heart == _inst)){
                        heart(_inst, sb);
                        return;
                    }
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group){
                if(card instanceof MeltingLove){
                    if((((MeltingLove) card).heart != null) && (((MeltingLove) card).heart == _inst)){
                        heart(_inst, sb);
                        return;
                    }
                }
            }
        }

        public static void heart(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();
            color.a = 0.6F;

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, LobotomyImageMaster.MELTING_HEART, _inst.current_x - 512.0F, _inst.current_y - 512.0F);
        }
    }
}
