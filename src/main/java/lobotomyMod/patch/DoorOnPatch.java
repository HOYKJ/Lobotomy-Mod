package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import lobotomyMod.card.AbstractLobotomyCard;

/**
 * @author hoykj
 */
public class DoorOnPatch {

    @SpirePatch(
            clz= CardRewardScreen.class,
            method="cardSelectUpdate"
    )
    public static class cardSelectUpdate {
        @SpireInsertPatch(rloc=15)
        public static void Insert(CardRewardScreen _inst){
            for (AbstractCard c : _inst.rewardGroup) {
                if ((c.hb.justHovered) && (c instanceof AbstractLobotomyCard)) {
                    CardCrawlGame.sound.play("DoorOn");
                }
            }
        }
    }
}
