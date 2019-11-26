package lobotomyMod.card.deriveCard.Apostles;

import lobotomyMod.card.deriveCard.AbstractDeriveCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;

/**
 * @author hoykj
 */
public abstract class AbstractApostleCard extends AbstractDeriveCard {

    public AbstractApostleCard(final String id, final String name, final int cost, final String description, CardColor color, final CardType type, final CardTarget target) {
        super(id, name, cost, description, color, type, target);
        this.exhaust = true;
        this.isEthereal = false;
    }
}
