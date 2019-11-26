package lobotomyMod.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class LobotomyFontHelper {
    public static BitmapFont Font_22;
    public static BitmapFont Font_28;
    public static BitmapFont Font_38;
    public static BitmapFont Font_118;
    public static BitmapFont Font_30;
    public static BitmapFont Rabbit_Manual_30;
    public static BitmapFont Rabbit_Manual_24;
    public static BitmapFont Rabbit_Manual_28;
    public static BitmapFont Rabbit_Manual_32;
    public static BitmapFont Rabbit_Manual_34;
    public static BitmapFont Rabbit_Manual_58;

    public static void initialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method prepFont = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
        Field fontFile = FontHelper.class.getDeclaredField("fontFile");
        Field param = FontHelper.class.getDeclaredField("param");
        prepFont.setAccessible(true);
        fontFile.setAccessible(true);
        param.setAccessible(true);

        Font_38 = (BitmapFont) prepFont.invoke(FontHelper.class, 38.0F, false);
        fontFile.set(FontHelper.class, Gdx.files.internal("font/zhs/NotoSansMonoCJKsc-Regular.otf"));
        ((FreeTypeFontGenerator.FreeTypeFontParameter)param.get(FontHelper.class)).borderWidth = 0.0F;
        ((FreeTypeFontGenerator.FreeTypeFontParameter)param.get(FontHelper.class)).shadowOffsetX = 1;
        ((FreeTypeFontGenerator.FreeTypeFontParameter)param.get(FontHelper.class)).shadowOffsetY = 1;
        ((FreeTypeFontGenerator.FreeTypeFontParameter)param.get(FontHelper.class)).spaceX = 0;
        Font_22 = (BitmapFont) prepFont.invoke(FontHelper.class, 22.0F, false);
        Font_28 = (BitmapFont) prepFont.invoke(FontHelper.class, 28.0F, false);
        Font_38.getData().markupEnabled = false;
        fontFile.set(FontHelper.class, Gdx.files.internal("lobotomyMod/font/norwester.otf"));
        Font_118 = (BitmapFont) prepFont.invoke(FontHelper.class, 118.0F, false);
        Font_30 = (BitmapFont) prepFont.invoke(FontHelper.class, 30.0F, false);
        Rabbit_Manual_58 = (BitmapFont) prepFont.invoke(FontHelper.class, 58.0F, false);
        if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT)  {
            fontFile.set(FontHelper.class, Gdx.files.internal("font/zhs/NotoSansMonoCJKsc-Regular.otf"));
        }
        Rabbit_Manual_30 = (BitmapFont) prepFont.invoke(FontHelper.class, 30.0F, false);
        Rabbit_Manual_24 = (BitmapFont) prepFont.invoke(FontHelper.class, 24.0F, false);
        Rabbit_Manual_28 = (BitmapFont) prepFont.invoke(FontHelper.class, 28.0F, false);
        Rabbit_Manual_32 = (BitmapFont) prepFont.invoke(FontHelper.class, 32.0F, false);
        Rabbit_Manual_34 = (BitmapFont) prepFont.invoke(FontHelper.class, 34.0F, false);
    }
}
