package lobotomyMod.card.angelaCard.bullets;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.localization.CardStrings;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;

/**
 * @author hoykj
 */
public abstract class AbstractBulletCard extends CustomCard {
    protected boolean removeOnUse;

    public AbstractBulletCard(final String id, final String name, final int cost, final String description, final CardType type, final CardTarget target) {
        super(id, name, LobotomyHandler.angelaCardImage(id), cost, description, type, AbstractCardEnum.Angela, CardRarity.BASIC, target);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
