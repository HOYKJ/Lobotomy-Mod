package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class ExhaustCardPatch {

    @SpirePatch(
            clz = CardGroup.class,
            method = "moveToExhaustPile"
    )
    public static class moveToExhaustPile {
        @SpireInsertPatch(rloc=0)
        public static void Insert(CardGroup _inst, AbstractCard c){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).ExhaustCard(c, false);
                }
                if (card instanceof AbstractEgoCard) {
                    ((AbstractEgoCard) card).ExhaustCard(c, false);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).ExhaustCard(c, true);
                }
                if (card instanceof AbstractEgoCard) {
                    ((AbstractEgoCard) card).ExhaustCard(c, true);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).ExhaustCard(c, false);
                }
                if (card instanceof AbstractEgoCard) {
                    ((AbstractEgoCard) card).ExhaustCard(c, false);
                }
            }
        }
    }
}
