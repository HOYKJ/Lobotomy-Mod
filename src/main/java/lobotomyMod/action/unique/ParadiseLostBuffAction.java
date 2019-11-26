package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import lobotomyMod.card.ego.rare.ParadiseLost;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.vfx.action.CampfirePrayEffect;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class ParadiseLostBuffAction extends AbstractGameAction {
    private ParadiseLost paradiseLost;
    private CardGroup cards;

    public ParadiseLostBuffAction(ParadiseLost paradiseLost){
        this.paradiseLost = paradiseLost;
        this.duration = 1.0F;
        this.startDuration = this.duration;
        this.cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard choice;
        for(int i = 0; i < 16; i ++) {
            if(this.paradiseLost.buffs[i] > 0){
                continue;
            }
            choice = this.paradiseLost.makeStatEquivalentCopy();
            ((ParadiseLost)choice).tips.clear();
            choice.name = ParadiseLost.EXTENDED_DESCRIPTION[2 * i + 3];
            choice.rawDescription = ParadiseLost.EXTENDED_DESCRIPTION[2 * i + 4];
            ((ParadiseLost)choice).spID = i;
            choice.initializeDescription();
            this.cards.addToTop(choice);
        }
        choice = this.paradiseLost.makeStatEquivalentCopy();
        ((ParadiseLost)choice).tips.clear();
        choice.name = ParadiseLost.EXTENDED_DESCRIPTION[37];
        choice.rawDescription = ParadiseLost.EXTENDED_DESCRIPTION[38];
        ((ParadiseLost)choice).spID = 17;
        choice.initializeDescription();
        this.cards.addToTop(choice);
    }

    public void update()
    {
        if(this.duration != this.startDuration){
            if (AbstractDungeon.cardRewardScreen.codexCard != null) {
                AbstractCard c = AbstractDungeon.cardRewardScreen.codexCard;
                if(((ParadiseLost)c).spID == 17){
                    this.paradiseLost.upgrade();
                }
                else {
                    this.paradiseLost.buffs[((ParadiseLost) c).spID]++;
                    this.paradiseLost.refresh();
                }
                AbstractDungeon.cardRewardScreen.codexCard = null;
                this.isDone = true;
            }
        }
        else {
            LobotomyUtils.openCardRewardsScreen(getGroup(), false, CampfirePrayEffect.TEXT[1]);
            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> getGroup(){
        final ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < 3) {
            AbstractCard card = this.getRandomCard();
            if (!cards.contains(card)) {
                cards.add(card);
            }
        }
        return cards;
    }

    private AbstractCard getRandomCard(){
        return cards.getRandomCard(true);
    }
}
