package lobotomyMod.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.department.Binah;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.character.Angela;
import lobotomyMod.relic.CogitoBucket;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class ReExtractPatch {

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=0)
        public static void Insert(CardRewardScreen _inst){
            if(!(_inst.rewardGroup.get(0) instanceof AbstractLobotomyCard)){
                ReExtractField.reExtractButton.get(_inst).hide();
            }
            ReExtractField.reExtractButton.get(_inst).update();
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "cardSelectUpdate"
    )
    public static class cardSelectUpdate {
        @SpireInsertPatch(rloc=33)
        public static void Insert(CardRewardScreen _inst){
            ReExtractField.reExtractButton.get(_inst).hide();
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "render"
    )
    public static class render {
        @SpireInsertPatch(rloc=0)
        public static void Insert(CardRewardScreen _inst, SpriteBatch sb){
            ReExtractField.reExtractButton.get(_inst).render(sb);
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "open"
    )
    public static class open {
        @SpirePostfixPatch
        public static void postfix(CardRewardScreen _inst, ArrayList<AbstractCard> cards, RewardItem rItem, String header) throws NoSuchFieldException, IllegalAccessException {
            if(cards.get(0) instanceof AbstractLobotomyCard){
                if(cards.get(0) instanceof AbstractLobotomyRelicCard){
                    ReExtractField.reExtractButton.get(_inst).hide();
                    return;
                }
                Field bowlButton = _inst.getClass().getDeclaredField("bowlButton");
                Field skipButton = _inst.getClass().getDeclaredField("skipButton");
                bowlButton.setAccessible(true);
                skipButton.setAccessible(true);
                ((SingingBowlButton)bowlButton.get(_inst)).hide();
                ((SkipCardButton)skipButton.get(_inst)).hideInstantly();
                if(AbstractDungeon.player instanceof Angela && Angela.departments[Binah.departmentCode[0]] < 3){
                    ((SkipCardButton)skipButton.get(_inst)).show(false);
                    return;
                }
                if((CogitoBucket.reExtract) && (((CogitoBucket)AbstractDungeon.player.getRelic(CogitoBucket.ID)).counter > 20)) {
                    ((SkipCardButton)skipButton.get(_inst)).show(true);
                    ReExtractField.reExtractButton.get(_inst).show();
                }
                else {
                    ((SkipCardButton)skipButton.get(_inst)).show(false);
                }
            }
            else {
                ReExtractField.reExtractButton.get(_inst).hide();
            }
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "reopen"
    )
    public static class reopen {
        @SpirePostfixPatch
        public static void postfix(CardRewardScreen _inst) throws NoSuchFieldException, IllegalAccessException {
            if(_inst.rewardGroup.get(0) instanceof AbstractLobotomyCard){
                Field bowlButton = _inst.getClass().getDeclaredField("bowlButton");
                Field skipButton = _inst.getClass().getDeclaredField("skipButton");
                bowlButton.setAccessible(true);
                skipButton.setAccessible(true);
                ((SingingBowlButton)bowlButton.get(_inst)).hide();
                ((SkipCardButton)skipButton.get(_inst)).hideInstantly();
                if(AbstractDungeon.player instanceof Angela && Angela.departments[Binah.departmentCode[0]] < 3){
                    ((SkipCardButton)skipButton.get(_inst)).show(false);
                    return;
                }
                if((CogitoBucket.reExtract) && (((CogitoBucket)AbstractDungeon.player.getRelic(CogitoBucket.ID)).counter > 20)) {
                    ((SkipCardButton)skipButton.get(_inst)).show(true);
                    ReExtractField.reExtractButton.get(_inst).show();
                }
                else {
                    ((SkipCardButton)skipButton.get(_inst)).show(false);
                }
            }
            else {
                ReExtractField.reExtractButton.get(_inst).hide();
            }
        }
    }
}
