package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class CardObtainPatch {
    @SpirePatch(
            clz = Soul.class,
            method = "obtain"
    )
    public static class obtain {
        @SpireInsertPatch(rloc=0)
        public static void Insert(Soul _inst, AbstractCard card){
            if(card instanceof AbstractLobotomyCard){
                ((AbstractLobotomyCard) card).obtain();
            }
        }
    }
}
