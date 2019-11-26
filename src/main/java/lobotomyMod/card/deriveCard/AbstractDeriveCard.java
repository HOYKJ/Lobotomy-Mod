package lobotomyMod.card.deriveCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public abstract class AbstractDeriveCard extends CustomCard {

    public AbstractDeriveCard(final String id, final String name, final int cost, final String description, CardColor color, final CardType type, final CardTarget target) {
        super(id, name, LobotomyHandler.deriveCardImage(id), cost, description, type, color, CardRarity.SPECIAL, target);
        this.exhaust = true;
        this.isEthereal = true;
    }

    public AbstractDeriveCard(final String id, final String name, final int cost, final String description, final CardType type, final CardTarget target) {
        super(id, name, LobotomyHandler.deriveCardImage(id), cost, description, type, AbstractCardEnum.Lobotomy, CardRarity.SPECIAL, target);
        this.exhaust = true;
        this.isEthereal = true;
    }

    public void obtain(){}

    public void atBattleStart(){}

    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target){}

    public void endOfTurn(boolean hand){}

    public void ExhaustCard(AbstractCard card, boolean hand){}

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, boolean hand){}

    public void onVictory(){}

    public void onShuffle(){}

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }
}
