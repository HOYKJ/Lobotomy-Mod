package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.rareCard.WhiteNight;

/**
 * @author hoykj
 */
public class CannotHealPatch {

    @SpirePatch(
            clz= AbstractCreature.class,
            method="heal",
            paramtypez = {
                    int.class,
                    boolean.class
            }
    )
    public static class heal {
        @SpireInsertPatch(rloc=0)
        public static SpireReturn Insert(AbstractCreature _inst, int healAmount, boolean showEffect){
            if(_inst instanceof AbstractPlayer) {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if ((card instanceof WhiteNight) && (!WhiteNight.canHeal)) {
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
