package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.monster.ApocalypseBirdMonster;
import lobotomyMod.monster.LongArm;
import lobotomyMod.monster.WhiteNightMonster;
import lobotomyMod.vfx.WhiteNightWordsEffect;

/**
 * @author hoykj
 */
public class WhiteNightControlPatch {

//    @SpirePatch(
//            clz= TopPanel.class,
//            method="update"
//    )
//    public static class update {
//        @SpireInsertPatch(rloc=0)
//        public static void Insert(TopPanel _inst) {
//            if (AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof WhiteNightMonster) {
//                _inst.settingsHb.update();
//                _inst.deckHb.update();
//                _inst.mapHb.update();
//                if (_inst.settingsHb.hovered) {
//                    _inst.settingsHb.unhover();
//                    AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(0));
//                } else if (_inst.deckHb.hovered) {
//                    _inst.deckHb.unhover();
//                    AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(2));
//                } else if (_inst.mapHb.hovered) {
//                    _inst.mapHb.unhover();
//                    AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(1));
//                } else {
//                    for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
//                        if (effect instanceof WhiteNightWordsEffect) {
//                            ((WhiteNightWordsEffect) effect).end();
//                        }
//                    }
//                }
//            }
//        }
//    }

    @SpirePatch(
            clz= TopPanel.class,
            method="updateSettingsButtonLogic"
    )
    public static class updateSettingsButtonLogic {
        @SpireInsertPatch(rloc=4)
        public static SpireReturn Insert(TopPanel _inst) {
            if (_inst.settingsHb.hovered && InputHelper.justClickedLeft || InputHelper.pressedEscape || CInputActionSet.settings.isJustPressed()) {
                if((AbstractDungeon.getCurrRoom().monsters != null) && (AbstractDungeon.getCurrRoom().monsters.monsters != null) && (!AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty())) {
                    if (AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof WhiteNightMonster) {
                        for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
                            if (effect instanceof WhiteNightWordsEffect) {
                                effect.isDone = true;
                            }
                        }
                        AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(0));
                        InputHelper.pressedEscape = false;
                        CInputActionSet.settings.unpress();
                        InputHelper.justClickedLeft = false;
                        return SpireReturn.Return(null);
                    }
                    else if ((AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof ApocalypseBirdMonster) && (AbstractDungeon.getCurrRoom().monsters.monsters.get(2) instanceof LongArm)) {
                        if(!AbstractDungeon.getCurrRoom().monsters.monsters.get(2).halfDead) {
                            return SpireReturn.Return(null);
                        }
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TopPanel.class,
            method="updateDeckViewButtonLogic"
    )
    public static class updateDeckViewButtonLogic {
        @SpireInsertPatch(rloc=16)
        public static SpireReturn Insert(TopPanel _inst) {
            boolean clickedDeckButton = _inst.deckHb.hovered && InputHelper.justClickedLeft;
            if ((clickedDeckButton || InputActionSet.masterDeck.isJustPressed() || CInputActionSet.pageLeftViewDeck.isJustPressed()) && !CardCrawlGame.isPopupOpen) {
                if((AbstractDungeon.getCurrRoom().monsters != null) && (AbstractDungeon.getCurrRoom().monsters.monsters != null) && (!AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty())) {
                    if (AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof WhiteNightMonster) {
                        for(AbstractGameEffect effect : AbstractDungeon.topLevelEffects){
                            if(effect instanceof WhiteNightWordsEffect){
                                effect.isDone = true;
                            }
                        }
                        AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(2));
                        InputHelper.justClickedLeft = false;
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TopPanel.class,
            method="updateMapButtonLogic"
    )
    public static class updateMapButtonLogic {
        @SpireInsertPatch(rloc=16)
        public static SpireReturn Insert(TopPanel _inst) {
            boolean clickedMapButton = _inst.mapHb.hovered && InputHelper.justClickedLeft;
            if (!CardCrawlGame.cardPopup.isOpen && (clickedMapButton || InputActionSet.map.isJustPressed() || CInputActionSet.map.isJustPressed())) {
                if((AbstractDungeon.getCurrRoom().monsters != null) && (AbstractDungeon.getCurrRoom().monsters.monsters != null) && (!AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty())) {
                    if (AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof WhiteNightMonster) {
                        for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
                            if (effect instanceof WhiteNightWordsEffect) {
                                effect.isDone = true;
                            }
                        }
                        AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(1));
                        InputHelper.justClickedLeft = false;
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= AbstractRelic.class,
            method="updateRelicPopupClick"
    )
    public static class updateRelicPopupClick {
        @SpireInsertPatch(rloc=4)
        public static SpireReturn Insert(AbstractRelic _inst) {
            if((AbstractDungeon.getCurrRoom().monsters != null) && (AbstractDungeon.getCurrRoom().monsters.monsters != null) && (!AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty())) {
                if (AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof WhiteNightMonster) {
                    for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
                        if (effect instanceof WhiteNightWordsEffect) {
                            effect.isDone = true;
                        }
                    }
                    AbstractDungeon.topLevelEffects.add(new WhiteNightWordsEffect(3));
                    CInputActionSet.select.unpress();
                    _inst.hb.clicked = false;
                    _inst.hb.clickStarted = false;
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
