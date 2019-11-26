package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;
import lobotomyMod.patch.MeatLanternField;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.nextRoom;

/**
 * @author hoykj
 */
public class EnterRoomPatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "nextRoomTransition",
            paramtypez = {
                    SaveFile.class
            }
    )
    public static class nextRoomTransition {
        @SpireInsertPatch(rloc = 68)
        public static void Insert(AbstractDungeon _inst, SaveFile saveFile) {
            if (nextRoom != null) {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        ((AbstractLobotomyCard) card).onEnterRoom(nextRoom.room);
                    }
                }
            }
        }
    }
}

