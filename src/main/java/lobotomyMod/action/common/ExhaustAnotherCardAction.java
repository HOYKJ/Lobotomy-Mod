package lobotomyMod.action.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author hoykj
 */
public class ExhaustAnotherCardAction extends AbstractGameAction {
    private CardGroup group1, group2, group3;
    private AbstractCard source;

    public ExhaustAnotherCardAction(CardGroup group1, CardGroup group2, CardGroup group3, AbstractCard source){
        this.group1 = group1;
        this.group2 = group2;
        this.group3 = group3;
        this.source = source;
    }

    public void update(){
        if(group1.contains(source)){
            if(group1.size() > 1){
                AbstractCard card = group1.getRandomCard(true);
                while (card == source){
                    card = group1.getRandomCard(true);
                }
                ExhaustCard(card, group1);
            }
            else if(group2.size() > 0){
                ExhaustCard(group2.getRandomCard(true), group2);
            }
            else if(group3.size() > 0){
                ExhaustCard(group3.getRandomCard(true), group3);
            }
        }
        else if(group2.contains(source)){
            if(group1.size() > 0){
                ExhaustCard(group1.getRandomCard(true), group1);
            }
            else if(group2.size() > 1){
                AbstractCard card = group2.getRandomCard(true);
                while (card == source){
                    card = group2.getRandomCard(true);
                }
                ExhaustCard(card, group2);
            }
            else if(group3.size() > 0){
                ExhaustCard(group3.getRandomCard(true), group3);
            }
        }
        else if(group3.contains(source)){
            if(group1.size() > 0){
                ExhaustCard(group1.getRandomCard(true), group1);
            }
            else if(group2.size() > 0){
                ExhaustCard(group2.getRandomCard(true), group2);
            }
            else if(group3.size() > 1){
                AbstractCard card = group3.getRandomCard(true);
                while (card == source){
                    card = group3.getRandomCard(true);
                }
                ExhaustCard(card, group3);
            }
        }
        else {
            if(group1.size() > 0){
                ExhaustCard(group1.getRandomCard(true), group1);
            }
            else if(group2.size() > 0){
                ExhaustCard(group2.getRandomCard(true), group2);
            }
            else if(group3.size() > 0){
                ExhaustCard(group3.getRandomCard(true), group3);
            }
        }

        this.isDone = true;
    }

    private void ExhaustCard(AbstractCard targetCard, CardGroup group){
        group.moveToExhaustPile(targetCard);
        CardCrawlGame.dungeon.checkForPactAchievement();
        targetCard.exhaustOnUseOnce = false;
        targetCard.freeToPlayOnce = false;
    }
}
