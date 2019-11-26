package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class OnAttackedPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class damage {
        @SpireInsertPatch(rloc=36, localvars={"damageAmount"})
        public static void Insert(AbstractPlayer _inst, DamageInfo info, @ByRef int[] damageAmount){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    damageAmount[0] = ((AbstractLobotomyCard) card).onAttackedToChangeDamage(info, damageAmount[0], false);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    damageAmount[0] = ((AbstractLobotomyCard) card).onAttackedToChangeDamage(info, damageAmount[0], true);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    damageAmount[0] = ((AbstractLobotomyCard) card).onAttackedToChangeDamage(info, damageAmount[0], false);
                }
            }
        }
    }
}
