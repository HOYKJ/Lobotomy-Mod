package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import lobotomyMod.LobotomyMod;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class PlayerDeadPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class damage {
        @SpirePostfixPatch
        public static void post(AbstractPlayer _inst, DamageInfo info){
            if(_inst.isDead && _inst.hasRelic(CogitoBucket.ID) && LobotomyMod.activeChampagne){
                LobotomyMod.deadTime ++;
            }
        }
    }
}
