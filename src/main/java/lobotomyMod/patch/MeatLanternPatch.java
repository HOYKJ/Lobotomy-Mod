package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.card.uncommonCard.BigBird;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class MeatLanternPatch {

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=1)
        public static void Insert(UseCardAction _inst) throws NoSuchFieldException, IllegalAccessException {
            Field targetCard = _inst.getClass().getDeclaredField("targetCard");
            targetCard.setAccessible(true);
            if(MeatLanternField.hasLantern.get(targetCard.get(_inst))){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 50), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
    }

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

            if (MeatLanternField.hasLantern.get(_inst)) {
                flower(_inst, sb);
            }
        }

        public static void flower(AbstractCard _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Color color = Color.WHITE.cpy();

            Method renderHelper = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            renderHelper.setAccessible(true);
            renderHelper.invoke(_inst, sb, color, LobotomyImageMaster.LANTERN_FLOWER, _inst.current_x - 512.0F, _inst.current_y - 512.0F);
        }
    }
}
