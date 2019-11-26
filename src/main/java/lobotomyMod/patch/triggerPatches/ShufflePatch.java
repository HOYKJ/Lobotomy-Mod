package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class ShufflePatch {

//    @SpirePatch(
//            clz = AbstractPlayer.class,
//            method = "onShuffle"
//    )
//    public static class onShuffle {
//        @SpireInsertPatch(rloc=0)
//        public static void Insert(AbstractPlayer _inst){
//            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
//                if (card instanceof AbstractLobotomyCard) {
//                    ((AbstractLobotomyCard) card).onShuffle();
//                }
//            }
//            for(AbstractCard card : AbstractDungeon.player.hand.group) {
//                if (card instanceof AbstractLobotomyCard) {
//                    ((AbstractLobotomyCard) card).onShuffle();
//                }
//            }
//            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
//                if (card instanceof AbstractLobotomyCard) {
//                    ((AbstractLobotomyCard) card).onShuffle();
//                }
//            }
//        }
//    }

    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EmptyDeckShuffleAction0 {
        @SpirePostfixPatch
        public static void Postfix(EmptyDeckShuffleAction _inst){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAllAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ShuffleAllAction0 {
        @SpirePostfixPatch
        public static void Postfix(ShuffleAllAction _inst){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAction.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=0)
        public static void Insert(ShuffleAction _inst){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onShuffle();
                }
            }
        }
    }
}
