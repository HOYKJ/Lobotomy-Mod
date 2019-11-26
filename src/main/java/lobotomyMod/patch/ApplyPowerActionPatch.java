package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author hoykj
 */
public class ApplyPowerActionPatch {

    @SpirePatch(
            clz= ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractCreature.class,
                    AbstractCreature.class,
                    AbstractPower.class,
                    int.class,
                    boolean.class,
                    AbstractGameAction.AttackEffect.class
            }
    )
    public static class CONSTRUCTOR {
        @SpirePrefixPatch
        public static SpireReturn prefix(ApplyPowerAction _inst, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect){
            if(AbstractDungeon.getMonsters() == null){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
