package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.BehaviorAdjustmentRelic;

/**
 * @author hoykj
 */
public class BehaviorAdjustment extends AbstractLobotomyAbnRelic {
    public static final String ID = "BehaviorAdjustment";

    public BehaviorAdjustment()
    {
        super("BehaviorAdjustment",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if(drawnCard.type == AbstractCard.CardType.STATUS || drawnCard.type == AbstractCard.CardType.CURSE){
            AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(drawnCard));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS)));
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new BehaviorAdjustmentRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BehaviorAdjustment();
    }
}
