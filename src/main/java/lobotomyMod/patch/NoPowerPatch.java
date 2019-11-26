package lobotomyMod.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import lobotomyMod.character.Angela;

import java.util.ArrayList;

/**
 * @author hoykj
 */
@SpirePatch(cls="com.megacrit.cardcrawl.core.AbstractCreature", method="renderPowerTips")
public class NoPowerPatch {
    @SpireInsertPatch(rloc=9, localvars={"tips"})
    public static void Insert(AbstractCreature _inst, SpriteBatch sb, ArrayList<PowerTip> tips)
    {
        if(AbstractDungeon.player instanceof Angela){
            if(Angela.departments[1] < 2) {
                tips.clear();
            }
        }
    }
}
