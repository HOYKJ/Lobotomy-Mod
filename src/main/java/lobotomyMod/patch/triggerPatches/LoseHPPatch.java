package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class LoseHPPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class damage {
        @SpireInsertPatch(rloc=57, localvars={"damageAmount"})
        public static void Insert(AbstractPlayer _inst, DamageInfo info, @ByRef int[] damageAmount){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onLoseHP(damageAmount[0], false);
                }
                else if (card instanceof AbstractEgoCard) {
                    ((AbstractEgoCard) card).onLoseHP(damageAmount[0], false);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onLoseHP(damageAmount[0], true);
                }
                else if (card instanceof AbstractEgoCard) {
                    ((AbstractEgoCard) card).onLoseHP(damageAmount[0], true);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onLoseHP(damageAmount[0], false);
                }
                else if (card instanceof AbstractEgoCard) {
                    ((AbstractEgoCard) card).onLoseHP(damageAmount[0], false);
                }
            }
        }
    }
}
