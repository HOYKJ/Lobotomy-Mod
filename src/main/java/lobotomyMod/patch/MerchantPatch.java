//package lobotomyMod.patch;
//
//import com.badlogic.gdx.math.MathUtils;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
//import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardGroup;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.CardLibrary;
//import com.megacrit.cardcrawl.shop.Merchant;
//import com.megacrit.cardcrawl.unlock.UnlockTracker;
//import lobotomyMod.LobotomyMod;
//import lobotomyMod.character.Angela;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Map;
//import java.util.Objects;
//
//import static com.megacrit.cardcrawl.shop.Merchant.DRAW_X;
//import static com.megacrit.cardcrawl.shop.Merchant.DRAW_Y;
//
///**
// * @author hoykj
// */
//public class MerchantPatch {
//    @SpirePatch(
//            clz = Merchant.class,
//            method = SpirePatch.CONSTRUCTOR,
//            paramtypez = {
//                    float.class,
//                    float.class,
//                    int.class
//            }
//    )
//    public static class CONSTRUCTOR {
//        @SpireInsertPatch(rloc=13)
//        public static SpireReturn insert(Merchant _inst, float x, float y, int newShopScreen) throws NoSuchFieldException, IllegalAccessException {
//            if(!(AbstractDungeon.player instanceof Angela)){
//                return SpireReturn.Continue();
//            }
//            ArrayList<AbstractCard> cards1 = new ArrayList<>();
//            ArrayList<AbstractCard> cards2 = new ArrayList<>();
//            ArrayList<AbstractCard> tmp = new ArrayList<>();
//            for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
//                if ((c.getValue()).type != AbstractCard.CardType.CURSE && (c.getValue()).type != AbstractCard.CardType.STATUS
//                        && (!UnlockTracker.isCardLocked(c.getKey()) || Settings.treatEverythingAsUnlocked())) {
//                    tmp.add(c.getValue());
//                }
//            }
//
//            AbstractCard c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
//            cards1.add(c);
//
//            c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
//            while ((Objects.equals(c.cardID, (cards1.get(cards1.size() - 1)).cardID))) {
//                c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
//            }
//            cards1.add(c);
//
//            c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
//            cards1.add(c);
//
//            c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
//            while ((Objects.equals(c.cardID, (cards1.get(cards1.size() - 1)).cardID))) {
//                c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
//            }
//            cards1.add(c);
//
//            c = getRandomCard(tmp, AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy();
//            cards1.add(c);
//
//            cards2.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON).makeCopy());
//            cards2.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy());
//
//            Field idleMessages = _inst.getClass().getDeclaredField("idleMessages");
//            idleMessages.setAccessible(true);
//            if (AbstractDungeon.id.equals("TheEnding")) {
//                Collections.addAll((ArrayList<String>)idleMessages.get(_inst), Merchant.ENDING_TEXT);
//            } else {
//                Collections.addAll((ArrayList<String>)idleMessages.get(_inst), Merchant.TEXT);
//            }
//
//            Field speechTimer = _inst.getClass().getDeclaredField("speechTimer");
//            Field modX = _inst.getClass().getDeclaredField("modX");
//            Field modY = _inst.getClass().getDeclaredField("modY");
//            Field shopScreen = _inst.getClass().getDeclaredField("shopScreen");
//            speechTimer.setAccessible(true);
//            modX.setAccessible(true);
//            modY.setAccessible(true);
//            shopScreen.setAccessible(true);
//
//            speechTimer.set(_inst, 1.5F);
//            modX.set(_inst, x);
//            modY.set(_inst, y);
//            _inst.hb.move(DRAW_X + (250.0F + x) * Settings.scale, DRAW_Y + (130.0F + y) * Settings.scale);
//            shopScreen.set(_inst, newShopScreen);
//            AbstractDungeon.shopScreen.init(cards1, cards2);
//            return SpireReturn.Return(null);
//        }
//
//        public static AbstractCard getRandomCard(ArrayList<AbstractCard> group, AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng)
//        {
//            AbstractCard.CardRarity r = rarity;
//            if(type == AbstractCard.CardType.POWER && rarity == AbstractCard.CardRarity.COMMON){
//                r = AbstractCard.CardRarity.UNCOMMON;
//            }
//            ArrayList<AbstractCard> tmp = new ArrayList<>();
//            for (AbstractCard c : group) {
//                if (c.type == type && c.rarity == r) {
//                    tmp.add(c);
//                }
//            }
//            if (tmp.isEmpty())
//            {
//                LobotomyMod.logger.info("ERROR: No cards left for type: " + type.name() + rarity.name());
//                return null;
//            }
//            Collections.sort(tmp);
//            if (useRng) {
//                return tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
//            }
//            return tmp.get(MathUtils.random(tmp.size() - 1));
//        }
//    }
//}
