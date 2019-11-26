package lobotomyMod.patch.infoView;

import basemod.DevConsole;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.relic.AbstractLobotomyRelic;
import lobotomyMod.relic.CogitoBucket;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class infoViewPatch {

    public static void init(SingleCardViewPopup _inst, AbstractCard card, boolean unlock){
        if(card instanceof AbstractLobotomyCard){
            if(card instanceof AbstractLobotomyRelicCard){
                infoViewField.lock.set(_inst, null);
                infoViewField.lastInfo.set(_inst, null);
                infoViewField.nextLock.set(_inst, null);
                infoViewField.nextInfo.set(_inst, null);
                return;
            }
            if(CogitoBucket.level[((AbstractLobotomyCard) card).AbnormalityID] == 0) {
                if((AbstractDungeon.player == null) || (!AbstractDungeon.player.hasRelic(CogitoBucket.ID))){
                    infoViewField.lock.set(_inst, null);
                    return;
                }
                infoViewField.lock.set(_inst, new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale));
                infoViewField.lock.get(_inst).move(Settings.WIDTH / 2.0F, 170.0F * Settings.scale);
                infoViewField.nextLock.set(_inst, null);
                infoViewField.nextInfo.set(_inst, null);
                infoViewField.lastInfo.set(_inst, null);
                return;
            }
            else {
                infoViewField.lock.set(_inst, null);
            }

            if(((AbstractLobotomyCard) card).infoStage < ((AbstractLobotomyCard) card).maxInfo){
                if(CogitoBucket.level[((AbstractLobotomyCard) card).AbnormalityID] <= ((AbstractLobotomyCard) card).infoStage){
                    infoViewField.nextInfo.set(_inst, null);
                    infoViewField.nextLock.set(_inst, new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale));
                    infoViewField.nextLock.get(_inst).move(Settings.WIDTH / 2.0F + 250F * Settings.scale, 340.0F * Settings.scale);
                    if((AbstractDungeon.player == null) || (!AbstractDungeon.player.hasRelic(CogitoBucket.ID))){
                        infoViewField.nextLock.set(_inst, null);
                    }
                }
                else {
                    infoViewField.nextLock.set(_inst, null);
                    infoViewField.nextInfo.set(_inst, new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale));
                    infoViewField.nextInfo.get(_inst).move(Settings.WIDTH / 2.0F + 250F * Settings.scale, 340.0F * Settings.scale);
                }
            }
            else {
                infoViewField.nextLock.set(_inst, null);
                infoViewField.nextInfo.set(_inst, null);
            }

            if(((AbstractLobotomyCard) card).infoStage > 1) {
                infoViewField.lastInfo.set(_inst, new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale));
                infoViewField.lastInfo.get(_inst).move(Settings.WIDTH / 2.0F - 250F * Settings.scale, 340.0F * Settings.scale);
            }
            else {
                infoViewField.lastInfo.set(_inst, null);
            }
        }
        else {
            infoViewField.lock.set(_inst, null);
            infoViewField.lastInfo.set(_inst, null);
            infoViewField.nextLock.set(_inst, null);
            infoViewField.nextInfo.set(_inst, null);
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class,
            method="open",
            paramtypez={
                    AbstractCard.class,
                    CardGroup.class,
            })
    public static class openpatch{
        public static void Postfix(SingleCardViewPopup _inst, AbstractCard card, CardGroup group) {
            init(_inst, card, true);
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class,
            method="open",
            paramtypez={
                    AbstractCard.class
            })
    public static class openpatch2{
        public static void Postfix(SingleCardViewPopup _inst, AbstractCard card){
            init(_inst, card, true);
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateArrows"
    )
    public static class updateArrows {
        @SpirePrefixPatch
        public static void prefix(SingleCardViewPopup _inst) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            Field card = _inst.getClass().getDeclaredField("card");
            card.setAccessible(true);

            if(card.get(_inst) instanceof AbstractLobotomyCard){
                if((CogitoBucket.level[((AbstractLobotomyCard) card.get(_inst)).AbnormalityID] == 0) && (infoViewField.lock.get(_inst) != null)){
                    infoViewField.lock.get(_inst).update();
                    if (infoViewField.lock.get(_inst).justHovered) {
                        CardCrawlGame.sound.play("UI_HOVER");
                    }
                    if (infoViewField.lock.get(_inst).clicked)
                    {
                        if(((AbstractLobotomyCard) card.get(_inst)).canUnlockInfo()) {
                            ((AbstractLobotomyCard) card.get(_inst)).unlockInfo();
                            Method loadPortraitImg = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                            loadPortraitImg.setAccessible(true);
                            loadPortraitImg.invoke(_inst);
                            infoViewField.lock.set(_inst, null);

                            init(_inst, (AbstractCard) card.get(_inst), true);
                        }
                    }
                }

                if(infoViewField.nextLock.get(_inst) != null){
                    infoViewField.nextLock.get(_inst).update();
                    if (infoViewField.nextLock.get(_inst).justHovered) {
                        CardCrawlGame.sound.play("UI_HOVER");
                    }
                    if (infoViewField.nextLock.get(_inst).clicked)
                    {
                        if(((AbstractLobotomyCard) card.get(_inst)).canUnlockInfo()) {
                            ((AbstractLobotomyCard) card.get(_inst)).unlockInfo();
                            init(_inst, (AbstractCard) card.get(_inst), true);
                        }
                    }
                }



                if(infoViewField.nextInfo.get(_inst) != null){
                    infoViewField.nextInfo.get(_inst).update();
                    if (infoViewField.nextInfo.get(_inst).justHovered) {
                        CardCrawlGame.sound.play("UI_HOVER");
                    }
                    if (infoViewField.nextInfo.get(_inst).clicked) {
                        ((AbstractLobotomyCard) card.get(_inst)).infoStage ++;
                        ((AbstractLobotomyCard) card.get(_inst)).initInfo();

                        init(_inst, (AbstractCard) card.get(_inst), true);
                    }
                }

                if(infoViewField.lastInfo.get(_inst) != null){
                    infoViewField.lastInfo.get(_inst).update();
                    if (infoViewField.lastInfo.get(_inst).justHovered) {
                        CardCrawlGame.sound.play("UI_HOVER");
                    }
                    if (infoViewField.lastInfo.get(_inst).clicked) {
                        ((AbstractLobotomyCard) card.get(_inst)).infoStage --;
                        ((AbstractLobotomyCard) card.get(_inst)).initInfo();

                        init(_inst, (AbstractCard) card.get(_inst), true);
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput"
    )
    public static class updateInput {
        @SpirePrefixPatch
        public static SpireReturn prefix(SingleCardViewPopup _inst) throws NoSuchFieldException, IllegalAccessException {
            Field card = _inst.getClass().getDeclaredField("card");
            card.setAccessible(true);

            if(card.get(_inst) instanceof AbstractLobotomyCard) {
                if ((CogitoBucket.level[((AbstractLobotomyCard) card.get(_inst)).AbnormalityID] == 0) && (infoViewField.lock.get(_inst) != null)) {
                    if (InputHelper.justClickedLeft) {
                        if (infoViewField.lock.get(_inst).hovered) {
                            infoViewField.lock.get(_inst).clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                            return SpireReturn.Return(null);
                        }
                    }
                }

                if (infoViewField.nextLock.get(_inst) != null) {
                    if (InputHelper.justClickedLeft) {
                        if (infoViewField.nextLock.get(_inst).hovered) {
                            infoViewField.nextLock.get(_inst).clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                            return SpireReturn.Return(null);
                        }
                    }
                }




                if (infoViewField.nextInfo.get(_inst) != null) {
                    if (InputHelper.justClickedLeft) {
                        if (infoViewField.nextInfo.get(_inst).hovered) {
                            infoViewField.nextInfo.get(_inst).clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                            return SpireReturn.Return(null);
                        }
                    }
                }

                if (infoViewField.lastInfo.get(_inst) != null) {
                    if (InputHelper.justClickedLeft) {
                        if (infoViewField.lastInfo.get(_inst).hovered) {
                            infoViewField.lastInfo.get(_inst).clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                            return SpireReturn.Return(null);
                        }
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render"
    )
    public static class render {
        @SpirePostfixPatch
        public static void postfix(SingleCardViewPopup _inst, SpriteBatch sb) {
            if (infoViewField.lock.get(_inst) != null) {
                infoViewField.lock.get(_inst).render(sb);
            }

            if (infoViewField.nextLock.get(_inst) != null) {
                infoViewField.nextLock.get(_inst).render(sb);
            }

            if (infoViewField.nextInfo.get(_inst) != null) {
                infoViewField.nextInfo.get(_inst).render(sb);
            }

            if (infoViewField.lastInfo.get(_inst) != null) {
                infoViewField.lastInfo.get(_inst).render(sb);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderArrows"
    )
    public static class renderArrows {
        @SpirePostfixPatch
        public static void postfix(SingleCardViewPopup _inst, SpriteBatch sb) {
            if (infoViewField.lock.get(_inst) != null)
            {
                sb.draw(ImageMaster.COLOR_TAB_LOCK, infoViewField.lock.get(_inst).cX - 40.0F, infoViewField.lock.get(_inst).cY - 40.0F, 20.0F, 20.0F, 80.0F, 80.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 40, 40, false, false);
                if (infoViewField.lock.get(_inst).hovered)
                {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                    sb.draw(ImageMaster.COLOR_TAB_LOCK, infoViewField.lock.get(_inst).cX - 40.0F, infoViewField.lock.get(_inst).cY - 40.0F, 20.0F, 20.0F, 80.0F, 80.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 40, 40, false, false);

                    sb.setColor(Color.WHITE);
                    sb.setBlendFunction(770, 771);
                }
            }

            if (infoViewField.nextLock.get(_inst) != null)
            {
                sb.draw(ImageMaster.COLOR_TAB_LOCK, infoViewField.nextLock.get(_inst).cX - 40.0F, infoViewField.nextLock.get(_inst).cY - 40.0F, 40.0f, 40.0f, 80.0F, 80.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 40, 40, false, false);
                if (infoViewField.nextLock.get(_inst).hovered)
                {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                    sb.draw(ImageMaster.COLOR_TAB_LOCK, infoViewField.nextLock.get(_inst).cX - 40.0F, infoViewField.nextLock.get(_inst).cY - 40.0F, 40.0f, 40.0f, 80.0F, 80.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 40, 40, false, false);

                    sb.setColor(Color.WHITE);
                    sb.setBlendFunction(770, 771);
                }
            }




            if (infoViewField.nextInfo.get(_inst) != null)
            {
                sb.draw(ImageMaster.POPUP_ARROW, infoViewField.nextInfo.get(_inst).cX - 50.0F, infoViewField.nextInfo.get(_inst).cY - 50.0F, 50.0f, 50.0f, 100.0F, 100.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, true, false);
                if (infoViewField.nextInfo.get(_inst).hovered)
                {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                    sb.draw(ImageMaster.POPUP_ARROW, infoViewField.nextInfo.get(_inst).cX - 50.0F, infoViewField.nextInfo.get(_inst).cY - 50.0F, 50.0f, 50.0f, 100.0F, 100.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, true, false);

                    sb.setColor(Color.WHITE);
                    sb.setBlendFunction(770, 771);
                }
            }

            if (infoViewField.lastInfo.get(_inst) != null)
            {
                sb.draw(ImageMaster.POPUP_ARROW, infoViewField.lastInfo.get(_inst).cX - 50.0F, infoViewField.lastInfo.get(_inst).cY - 50.0F, 50.0f, 50.0f, 100.0F, 100.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
                if (infoViewField.lastInfo.get(_inst).hovered)
                {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                    sb.draw(ImageMaster.POPUP_ARROW, infoViewField.lastInfo.get(_inst).cX - 50.0F, infoViewField.lastInfo.get(_inst).cY - 50.0F, 50.0f, 50.0f, 100.0F, 100.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);

                    sb.setColor(Color.WHITE);
                    sb.setBlendFunction(770, 771);
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "loadPortraitImg"
    )
    public static class loadPortraitImg {
        @SpirePrefixPatch
        public static void prefix(SingleCardViewPopup _inst) throws NoSuchFieldException, IllegalAccessException {
            Field card = _inst.getClass().getDeclaredField("card");
            card.setAccessible(true);

            if(card.get(_inst) instanceof AbstractLobotomyCard) {
                ((AbstractLobotomyCard) card.get(_inst)).loadImg();
            }
        }
    }
}
