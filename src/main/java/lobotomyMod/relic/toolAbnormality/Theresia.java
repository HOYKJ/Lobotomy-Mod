package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.TheresiaRelic;

/**
 * @author hoykj
 */
public class Theresia extends AbstractLobotomyAbnRelic {
    public static final String ID = "Theresia";

    public Theresia()
    {
        super("Theresia",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
        this.counter ++;
        if(this.counter >= 6){
            this.counter = 0;
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), 1));
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.counter = 0;
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new TheresiaRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Theresia();
    }
}
