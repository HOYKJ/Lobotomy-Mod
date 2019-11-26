package lobotomyMod.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import lobotomyMod.card.ego.rare.ParadiseLost;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.character.Angela;
import lobotomyMod.ui.ExpandOption;
import lobotomyMod.ui.PrayOption;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class CampfireUIPatch {
//    @SpirePatch(cls="com.megacrit.cardcrawl.rooms.CampfireUI", method="initializeButtons")
//    public static void Postfix(Object meObj)
//    {
//        if(!(AbstractDungeon.player instanceof Angela)){
//            return;
//        }
//        CampfireUI campfire = (CampfireUI)meObj;
//        try {
//            ArrayList campfireButtons = (ArrayList) ReflectionHacks.getPrivate(campfire, CampfireUI.class, "buttons");
//
//            int height = 450;
//            int wide = 950;
//            if (campfireButtons.size() > 3) {
//                height = 180;
//            } else if (campfireButtons.size() == 3) {
//                wide = 1110;
//            } else if (campfireButtons.size() == 1) {
//                height = 720;
//                wide = 1110;
//            }
//
//            ExpandOption button = ;
//
//            boolean flag = false;
//            for (int i : Angela.departments) {
//                if (i < 4) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (flag) {
//                campfireButtons.add(button);
//                if (campfireButtons.size() == 4) {
//                    ((AbstractCampfireOption) campfireButtons.get(campfireButtons.size() - 2)).setPosition(800.0F * Settings.scale, height * Settings.scale);
//                } else if (campfireButtons.size() == 6) {
//                    ((AbstractCampfireOption) campfireButtons.get(campfireButtons.size() - 4)).setPosition(650.0F * Settings.scale, 450.0F * Settings.scale);
//                    ((AbstractCampfireOption) campfireButtons.get(campfireButtons.size() - 3)).setPosition(950.0F * Settings.scale, 450.0F * Settings.scale);
//                    ((AbstractCampfireOption) campfireButtons.get(campfireButtons.size() - 2)).setPosition(1250.0F * Settings.scale, 450.0F * Settings.scale);
//                } else if (campfireButtons.size() == 2) {
//                    ((AbstractCampfireOption) campfireButtons.get(campfireButtons.size() - 2)).setPosition(800.0F * Settings.scale, height * Settings.scale);
//                }
//                ((AbstractCampfireOption) campfireButtons.get(campfireButtons.size() - 1)).setPosition(wide * Settings.scale, height * Settings.scale);
//            }
//        }
//        catch (java.lang.IllegalArgumentException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @SpirePatch(cls="com.megacrit.cardcrawl.rooms.CampfireUI", method="initializeButtons")
    public static class CampfireUIFix
    {
        @SpireInsertPatch(rloc=69, localvars={"buttons"})
        public static void Insert(CampfireUI _inst, ArrayList<AbstractCampfireOption> buttons)
        {
            if (buttons.size() < 6) {
                return;
            }
            int w = buttons.size() / 2 + 1;
            for (int i = 0; i < w; i++) {
                (buttons.get(i)).setPosition((950.0F - 150.0F * (w - 1) + 300.0F * i) * Settings.scale, 720.0F * Settings.scale);
            }
            int w2 = buttons.size() - w;
            for (int j = w; j < buttons.size(); j++) {
                (buttons.get(j)).setPosition((950.0F - 150.0F * (w2 - 1) + 300.0F * (j - w)) * Settings.scale, 450.0F * Settings.scale);
            }
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.rooms.CampfireUI", method="initializeButtons")
    public static class CardDupeFix
    {
        @SpireInsertPatch(rloc=36, localvars={"buttons"})
        public static void Insert(CampfireUI _inst, ArrayList<AbstractCampfireOption> buttons)
        {
            boolean flag = false;
            for (int i : Angela.departments) {
                if (i < 4) {
                    flag = true;
                    break;
                }
            }
            if(!(AbstractDungeon.player instanceof Angela)){
                flag = false;
            }
            if (flag) {
                buttons.add(new ExpandOption(true));
            }

            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c instanceof ParadiseLost) {
                    for (AbstractCard c2 : AbstractDungeon.player.masterDeck.group) {
                        if(c2 instanceof WhiteNight){
                            buttons.add(new PrayOption(true));
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
