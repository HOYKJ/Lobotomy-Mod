package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import lobotomyMod.monster.friendlyMonster.AbstractFriendlyMonster;

/**
 * @author hoykj
 */
public class AntiEscapePatch {
    @SpirePatch(
            clz= EscapeAction.class,
            method="update"
    )
    public static class render {
        @SpirePrefixPatch
        public static SpireReturn prefix(EscapeAction _inst){
            if (_inst.source instanceof AbstractFriendlyMonster){
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
