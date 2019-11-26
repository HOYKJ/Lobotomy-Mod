package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import lobotomyMod.card.commonCard.MHz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class NoisePatch {
    public static Texture img1 = ImageMaster.loadImage("lobotomyMod/images/texture/Noise1.png");
    public static Texture img2 = ImageMaster.loadImage("lobotomyMod/images/texture/Noise2.png");
    public static Texture img3 = ImageMaster.loadImage("lobotomyMod/images/texture/Noise3.png");

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
                if(card instanceof MHz){
                    if(((MHz) card).noiseTargets.contains(_inst)){
                        noise(_inst, sb);
                        return;
                    }
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                if(card instanceof MHz){
                    if(((MHz) card).noiseTargets.contains(_inst)){
                        noise(_inst, sb);
                        return;
                    }
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group){
                if(card instanceof MHz){
                    if(((MHz) card).noiseTargets.contains(_inst)){
                        noise(_inst, sb);
                        return;
                    }
                }
            }
        }

        public static void noise(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();
            color.a = 0.6F;
            Texture img;
            switch (MathUtils.random(2)){
                case 0:
                    img = img1;
                    break;
                case 1:
                    img = img2;
                    break;
                default:
                    img = img3;
                    break;
            }

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, img, _inst.current_x - 512.0F, _inst.current_y - 512.0F);
        }
    }
}
