package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.AdjustmentMirrorRelic;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class AdjustmentMirror extends AbstractLobotomyAbnRelic {
    public static final String ID = "AdjustmentMirror";
    private boolean used;

    public AdjustmentMirror()
    {
        super("AdjustmentMirror",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.used = false;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.used = false;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.addAll(AbstractDungeon.player.drawPile.group);
        list.addAll(AbstractDungeon.player.hand.group);
        list.addAll(AbstractDungeon.player.discardPile.group);
        AbstractDungeon.player.drawPile.clear();
        AbstractDungeon.player.hand.clear();
        AbstractDungeon.player.discardPile.clear();

        if(this.used){
            AbstractCard card = list.get(AbstractDungeon.cardRng.random(list.size() - 1));
            list.remove(card);
            AbstractDungeon.player.exhaustPile.addToBottom(card);

            card = list.get(AbstractDungeon.cardRng.random(list.size() - 1));
            list.remove(card);
            AbstractDungeon.player.exhaustPile.addToBottom(card);
        }

        for(AbstractCard card : list){
            int roll = AbstractDungeon.cardRng.random(2);
            switch (roll){
                case 0:
                    AbstractDungeon.player.drawPile.addToRandomSpot(card);
                    break;
                case 1:
                    AbstractDungeon.player.hand.addToRandomSpot(card);
                    break;
                case 2:
                    AbstractDungeon.player.discardPile.addToRandomSpot(card);
                    break;
            }
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.used = true;
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new AdjustmentMirrorRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new AdjustmentMirror();
    }
}
