package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.compendium.RelicViewScreen;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class RelicViewScreenPatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RelicViewScreenPatch");
    public static final String[] TEXT = uiStrings.TEXT;
    public static ArrayList<AbstractRelic> abnormalityPool = new ArrayList<>();
    public static ArrayList<AbstractRelic> egoPool = new ArrayList<>();

    @SpirePatch(
            clz= RelicViewScreen.class,
            method="render"
    )
    public static class render {
        @SpireInsertPatch(rloc=10)
        public static void Insert(RelicViewScreen _inst, SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method renderList = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "renderList", SpriteBatch.class, String.class, String.class, ArrayList.class);
            renderList.setAccessible(true);
            renderList.invoke(_inst, sb, TEXT[2], TEXT[3], egoPool);
            renderList.invoke(_inst, sb, TEXT[0], TEXT[1], abnormalityPool);
        }
    }

    @SpirePatch(
            clz= RelicViewScreen.class,
            method="update"
    )
    public static class update {
        @SpireInsertPatch(rloc=53)
        public static void Insert(RelicViewScreen _inst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method updateList = SuperclassFinder.getSuperClassMethod(_inst.getClass(), "updateList", ArrayList.class);
            updateList.setAccessible(true);
            updateList.invoke(_inst, egoPool);
            updateList.invoke(_inst, abnormalityPool);
        }
    }
}
