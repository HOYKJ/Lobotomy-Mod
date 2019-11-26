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
import lobotomyMod.card.deriveCard.Apostles.GuardApostle;
import lobotomyMod.card.deriveCard.Apostles.ScytheApostle;
import lobotomyMod.card.deriveCard.Apostles.SpearApostle;
import lobotomyMod.card.deriveCard.Apostles.WandApostle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class ApostleCardPatch {
    public static Texture Scythe = ImageMaster.loadImage("lobotomyMod/images/texture/Scythe.png");
    public static Texture Wand = ImageMaster.loadImage("lobotomyMod/images/texture/Wand.png");
    public static Texture Wand2 = ImageMaster.loadImage("lobotomyMod/images/texture/Wand2.png");
    public static Texture Spear = ImageMaster.loadImage("lobotomyMod/images/texture/Spear.png");

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

            if(_inst instanceof GuardApostle){
                weapon(_inst, sb, 0);
            }
            else if(_inst instanceof ScytheApostle){
                weapon(_inst, sb, 0);
            }
            else if(_inst instanceof WandApostle){
                weapon(_inst, sb, 1);
            }
            else if(_inst instanceof SpearApostle){
                weapon(_inst, sb, 2);
            }
        }

        public static void weapon(AbstractCard _inst, SpriteBatch sb, int code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();
            color.a = 1.0F;
            Texture img = null;
            switch (code){
                case 0:
                    img = Scythe;
                    break;
                case 1:
                    img = Wand;
                    break;
                case 2:
                    img = Spear;
                    break;
            }

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, img, _inst.current_x - 512.0F, _inst.current_y - 512.0F);
        }
    }
}
