package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.code.AbstractCodeCard;

/**
 * @author hoykj
 */
public class VictoryPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class onVictory {
        @SpireInsertPatch(rloc=1)
        public static void Insert(AbstractPlayer _inst){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onVictory();
                }
                else if (card instanceof AbstractCodeCard) {
                    ((AbstractCodeCard) card).onVictory();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onVictory();
                }
                else if (card instanceof AbstractCodeCard) {
                    ((AbstractCodeCard) card).onVictory();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onVictory();
                }
                else if (card instanceof AbstractCodeCard) {
                    ((AbstractCodeCard) card).onVictory();
                }
            }
        }
    }
}
