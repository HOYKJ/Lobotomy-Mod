package lobotomyMod.card.ego;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hoykj
 */
public abstract class AbstractEgoCard extends CustomCard {
    public List<TooltipInfo> tips = new ArrayList<>();

    public AbstractEgoCard(final String id, final String name, final int cost, final String description, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, name, LobotomyHandler.egoCardImage(id), cost, description, type, AbstractCardEnum.Angela, rarity, target);
        this.addTips();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return this.tips;
    }

    public abstract void addTips();



    public void ExhaustCard(AbstractCard card, boolean hand){}

    public void endOfTurn(boolean hand){}

    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target){}

    public void onLoseHP(int damageAmount, boolean hand){}
}
