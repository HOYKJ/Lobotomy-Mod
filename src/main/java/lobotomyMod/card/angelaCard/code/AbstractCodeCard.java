package lobotomyMod.card.angelaCard.code;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lobotomyMod.LobotomyMod;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;

/**
 * @author hoykj
 */
public abstract class AbstractCodeCard extends CustomCard {
    public boolean dep;

    public AbstractCodeCard(final String id, final String name, final int cost, final String description, final CardType type, final CardTarget target) {
        super(id, name, LobotomyHandler.angelaCardImage(id), cost, description, type, AbstractCardEnum.Angela, CardRarity.SPECIAL, target);
        this.dep = false;
    }

    public AbstractCodeCard(final String id, final String name, String imgId, final int cost, final String description, final CardTarget target) {
        super(id, name, (LobotomyMod.useBlackAngela? LobotomyHandler.angelaBlackCardImage(imgId): LobotomyHandler.angelaCardImage(imgId)), cost, description, CardType.SKILL, AbstractCardEnum.Angela, CardRarity.SPECIAL, target);
        this.dep = true;
    }

    public void endOfTurn(boolean hand){}

    public void onVictory(){}

    public void onEnterRoom(AbstractRoom room){}

    public void expand(){

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
