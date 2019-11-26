package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.patch.RelicTierEnum;
import lobotomyMod.relic.AbstractLobotomyRelic;

/**
 * @author hoykj
 */
public abstract class AbstractLobotomyAbnRelic extends AbstractLobotomyRelic {

    public AbstractLobotomyAbnRelic(final String id, final AbstractRelic.RelicTier hideTier, final AbstractRelic.LandingSound landingSound) {
        super(id, RelicTierEnum.Abnormality, hideTier, landingSound);
    }

    public AbstractLobotomyRelicCard getCard(){
        return null;
    }
}
