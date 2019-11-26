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
import lobotomyMod.card.deriveCard.AbstractDeriveCard;

/**
 * @author hoykj
 */
public class DamagePatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class damage {
        @SpireInsertPatch(rloc=43, localvars={"damageAmount"})
        public static void Insert(AbstractPlayer _inst, DamageInfo info, @ByRef int[] damageAmount){
            if(info.owner != AbstractDungeon.player){
                return;
            }
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
                else if (card instanceof AbstractDeriveCard) {
                    ((AbstractDeriveCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onAttack(info, damageAmount[0], _inst, true);
                }
                else if (card instanceof AbstractDeriveCard) {
                    ((AbstractDeriveCard) card).onAttack(info, damageAmount[0], _inst, true);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
                else if (card instanceof AbstractDeriveCard) {
                    ((AbstractDeriveCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class damage2 {
        @SpireInsertPatch(rloc=44, localvars={"damageAmount"})
        public static void Insert(AbstractMonster _inst, DamageInfo info, @ByRef int[] damageAmount){
            if(info.owner != AbstractDungeon.player){
                return;
            }
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
                else if (card instanceof AbstractDeriveCard) {
                    ((AbstractDeriveCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onAttack(info, damageAmount[0], _inst, true);
                }
                else if (card instanceof AbstractDeriveCard) {
                    ((AbstractDeriveCard) card).onAttack(info, damageAmount[0], _inst, true);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
                else if (card instanceof AbstractDeriveCard) {
                    ((AbstractDeriveCard) card).onAttack(info, damageAmount[0], _inst, false);
                }
            }
        }
    }
}
