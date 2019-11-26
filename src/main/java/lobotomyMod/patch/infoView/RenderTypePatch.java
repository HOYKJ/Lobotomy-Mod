package lobotomyMod.patch.infoView;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import lobotomyMod.card.AbstractLobotomyCard;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class RenderTypePatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CardType");
    public static final String[] TEXT = uiStrings.TEXT;

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderType"
    )
    public static class renderType {
        @SpireInsertPatch(rloc=0)
        public static SpireReturn Insert(AbstractCard _inst, SpriteBatch sb) {
            if(_inst instanceof AbstractLobotomyCard){
                BitmapFont font = FontHelper.cardTypeFont;
                font.getData().setScale(_inst.drawScale);

                FontHelper.renderRotatedText(sb, font, TEXT[0], _inst.current_x, _inst.current_y - 22.0F * _inst.drawScale * Settings.scale, 0.0F, -1.0F * _inst.drawScale * Settings.scale,
                        _inst.angle, false, new Color(0.35F, 0.35F, 0.35F, Color.WHITE.cpy().a));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardTypeText"
    )
    public static class renderCardTypeText {
        @SpireInsertPatch(rloc=0)
        public static SpireReturn Insert(SingleCardViewPopup _inst, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException {
            Field card = _inst.getClass().getDeclaredField("card");
            card.setAccessible(true);
            if(card.get(_inst) instanceof AbstractLobotomyCard){
                FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTypeFont, TEXT[0], Settings.WIDTH / 2.0F + 3.0F * Settings.scale,
                        Settings.HEIGHT / 2.0F - 40.0F * Settings.scale, new Color(0.35F, 0.35F, 0.35F, 1.0F));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
