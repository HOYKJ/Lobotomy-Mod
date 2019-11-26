package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class PreBattlePatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyPreCombatLogic"
    )
    public static class applyPreCombatLogic {
        @SpireInsertPatch(rloc=0)
        public static void Insert(AbstractPlayer _inst){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).atPreBattle();
                }
            }
        }
    }
}
