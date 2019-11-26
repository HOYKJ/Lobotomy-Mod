package lobotomyMod.relic;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.deriveCard.ApostleCardGroup.*;
import lobotomyMod.character.LobotomyHandler;

/**
 * @author hoykj
 */
public class DeathAngel extends AbstractLobotomyRelic{
    public static final String ID = "DeathAngel";
    public static boolean heal;

    public DeathAngel()
    {
        super("DeathAngel",  RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new DeathAngel();
    }
}
