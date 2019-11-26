package lobotomyMod.action.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class ExhaustOtherAction extends AbstractGameAction {
    private AbstractCard sourceCard;
    private CardGroup group;
    private float startingDuration;

    public ExhaustOtherAction(AbstractCard sourceCard) {
        this.sourceCard = sourceCard;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.EXHAUST;
        this.group = AbstractDungeon.player.hand;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            ArrayList<AbstractCard> list = new ArrayList<>(this.group.group);
            for(AbstractCard card : list){
                if(card == this.sourceCard){
                    continue;
                }
                this.group.moveToExhaustPile(card);
                CardCrawlGame.dungeon.checkForPactAchievement();
                card.exhaustOnUseOnce = false;
                card.freeToPlayOnce = false;
            }
        }

        this.tickDuration();
    }
}
