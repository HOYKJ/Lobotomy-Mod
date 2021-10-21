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
public class AtDamageGivePatch {

    @SpirePatch(
            clz = AbstractCard.class,
            method = "applyPowers"
    )
    public static class applyPowers {
        @SpireInsertPatch(rloc=34, localvars={"tmp"})
        public static void Insert(AbstractCard _inst, @ByRef float[] tmp){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    tmp[0] = ((AbstractLobotomyCard) card).atDamageGive(tmp[0], _inst.damageTypeForTurn);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    tmp[0] = ((AbstractLobotomyCard) card).atDamageGive(tmp[0], _inst.damageTypeForTurn);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    tmp[0] = ((AbstractLobotomyCard) card).atDamageGive(tmp[0], _inst.damageTypeForTurn);
                }
            }
        }

        @SpireInsertPatch(rloc=76, localvars={"tmp"})
        public static void Insert2(AbstractCard _inst, float[] tmp){
            for(int i = 0; i < tmp.length; i ++) {
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        tmp[i] = ((AbstractLobotomyCard) card).atDamageGive(tmp[i], _inst.damageTypeForTurn);
                    }
                }
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        tmp[i] = ((AbstractLobotomyCard) card).atDamageGive(tmp[i], _inst.damageTypeForTurn);
                    }
                }
                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        tmp[i] = ((AbstractLobotomyCard) card).atDamageGive(tmp[i], _inst.damageTypeForTurn);
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "calculateCardDamage"
    )
    public static class calculateCardDamage {
        @SpireInsertPatch(rloc=24, localvars={"tmp"})
        public static void Insert(AbstractCard _inst, AbstractMonster mo, @ByRef float[] tmp){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    tmp[0] = ((AbstractLobotomyCard) card).atDamageGive(tmp[0], _inst.damageTypeForTurn);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    tmp[0] = ((AbstractLobotomyCard) card).atDamageGive(tmp[0], _inst.damageTypeForTurn);
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    tmp[0] = ((AbstractLobotomyCard) card).atDamageGive(tmp[0], _inst.damageTypeForTurn);
                }
            }
        }

        @SpireInsertPatch(rloc=88, localvars={"tmp"})
        public static void Insert2(AbstractCard _inst, AbstractMonster mo, float[] tmp){
            for(int i = 0; i < tmp.length; i ++) {
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        tmp[i] = ((AbstractLobotomyCard) card).atDamageGive(tmp[i], _inst.damageTypeForTurn);
                    }
                }
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        tmp[i] = ((AbstractLobotomyCard) card).atDamageGive(tmp[i], _inst.damageTypeForTurn);
                    }
                }
                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        tmp[i] = ((AbstractLobotomyCard) card).atDamageGive(tmp[i], _inst.damageTypeForTurn);
                    }
                }
            }
        }
    }
}
