package lobotomyMod.relic;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

/**
 * @author hoykj
 */
public class HonorFeather extends AbstractLobotomyRelic{
    public static final String ID = "HonorFeather";

    public HonorFeather()
    {
        super("HonorFeather",  RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        super.obtain();
        flash();
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
        if ((room instanceof RestRoom))
        {
            flash();
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new HonorFeather();
    }
}
