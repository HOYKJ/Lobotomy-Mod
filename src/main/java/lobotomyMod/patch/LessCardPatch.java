package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.LobotomyMod;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class LessCardPatch {

    @SpirePatch(
            clz= AbstractDungeon.class,
            method="getColorlessRewardCards"
    )
    public static class getColorlessRewardCards {
        @SpireInsertPatch(rloc=4, localvars={"numCards"})
        public static void Insert(@ByRef int[] numCards)
        {
            if(AbstractDungeon.player instanceof Angela){
                if(Angela.departments[8] < 2) {
                    LobotomyMod.logger.info("run reduce card");
                    numCards[0] --;
                }
            }
        }
    }

    @SpirePatch(
            clz= AbstractDungeon.class,
            method="getRewardCards"
    )
    public static class getRewardCards {
        @SpireInsertPatch(rloc=4, localvars={"numCards"})
        public static void Insert(@ByRef int[] numCards)
        {
            if(AbstractDungeon.player instanceof Angela){
                if(Angela.departments[8] < 2) {
                    LobotomyMod.logger.info("run reduce card");
                    numCards[0] --;
                }
            }
        }
    }
}
