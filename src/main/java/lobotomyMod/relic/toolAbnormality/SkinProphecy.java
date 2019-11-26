package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.SkinProphecyRelic;

/**
 * @author hoykj
 */
public class SkinProphecy extends AbstractLobotomyAbnRelic {
    public static final String ID = "SkinProphecy";

    public SkinProphecy()
    {
        super("SkinProphecy",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        AbstractDungeon.player.maxHealth += 2;
        AbstractDungeon.player.currentHealth -= 4;
        AbstractDungeon.player.healthBarUpdatedEvent();
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new SkinProphecyRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new SkinProphecy();
    }
}
