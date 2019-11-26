package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

/**
 * @author hoykj
 */
public class DrawCardPatch {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="draw",
            paramtypez = {
                    int.class
            }
    )
    public static class draw {
        @SpireInsertPatch(rloc=21)
        public static void Insert(AbstractPlayer _inst, int numCards){
//            AbstractCard card = _inst.drawPile.getTopCard();
//            for(AbstractPower power : AbstractDungeon.player.powers){
//                if(power instanceof AbstractSamuraiPower){
//                    ((AbstractSamuraiPower) power).onDrawCard(card);
//                }
//            }
        }
    }
}
