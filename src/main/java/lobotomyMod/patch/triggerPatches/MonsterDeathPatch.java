package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class MonsterDeathPatch {

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "die",
            paramtypez = {
                    boolean.class
            }
    )
    public static class die {
        @SpireInsertPatch(rloc=0)
        public static void Insert(AbstractMonster _inst, boolean triggerRelics){
            if (!_inst.isDying) {
                for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        ((AbstractLobotomyCard) card).onMonsterDeath(_inst);
                    }
                }
                for(AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        ((AbstractLobotomyCard) card).onMonsterDeath(_inst);
                    }
                }
                for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    if (card instanceof AbstractLobotomyCard) {
                        ((AbstractLobotomyCard) card).onMonsterDeath(_inst);
                    }
                }
            }
        }
    }
}
