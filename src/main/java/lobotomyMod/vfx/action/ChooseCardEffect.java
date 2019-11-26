package lobotomyMod.vfx.action;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.relic.CogitoBucket;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class ChooseCardEffect extends AbstractGameEffect {
    private CardGroup groupToChoose;
    private String text;
    private int amount;

    public ChooseCardEffect(CardGroup chooseGroup, String text) {
        this.groupToChoose = chooseGroup;
        this.text = text;
        this.amount = 1;
        this.duration = 0.5f;
    }

    public void update() {
        if (this.duration == 0.5f) {
            if (AbstractDungeon.cardRewardScreen.codexCard != null) {
                final AbstractCard c = AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
                if(c instanceof AbstractLobotomyRelicCard) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(c.current_x, c.current_y, ((AbstractLobotomyRelicCard) c).getRelic());
                    CogitoBucket.level[((AbstractLobotomyRelicCard) c).AbnormalityID] = 1;
                }
                AbstractDungeon.cardRewardScreen.codexCard = null;
                this.isDone = true;
            }
        }
        if (this.amount > 0) {
            --this.amount;
            ArrayList<AbstractCard> chooseGroup;
            if(this.groupToChoose.group.size() >= 5) {
                chooseGroup = getRandomCards(5);
            }
            else {
                chooseGroup = getRandomCards(this.groupToChoose.group.size());
            }
            LobotomyUtils.openCardRewardsScreen(chooseGroup, true, this.text);
        }
    }

    private ArrayList<AbstractCard> getRandomCards(final int amount) {
        final ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < amount) {
            final AbstractCard card = getRandomCard();
            if (!cards.contains(card)) {
                cards.add(card);
            }
        }
        return cards;
    }

    private AbstractCard getRandomCard() {
        return allCardsToChoose().getRandomCard(true);
    }

    private CardGroup allCardsToChoose() {
        return this.groupToChoose;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
