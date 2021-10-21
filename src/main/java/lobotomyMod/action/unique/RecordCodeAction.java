package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lobotomyMod.relic.toolAbnormality.ResearcherNote;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class RecordCodeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> exhumes = new ArrayList<>();
    private int num;

    public RecordCodeAction(int num) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.num = num;
    }

    public void update() {
        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10 && !AbstractDungeon.player.hasRelic(ResearcherNote.ID)) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.exhaustPile.size() == 1) {
                if ((this.p.exhaustPile.group.get(0)).cardID.equals("Exhume")) {
                    this.isDone = true;
                } else {
                    AbstractCard c = this.p.exhaustPile.getTopCard();
                    c.unfadeOut();
                    this.p.hand.addToHand(c);
                    if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                        c.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(c);

                    c.unhover();
                    c.fadingOut = false;
                    this.isDone = true;
                }
            } else {
                Iterator c;
                c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    derp = (AbstractCard)c.next();
                    derp.stopGlowing();
                    derp.unhover();
                    derp.unfadeOut();
                }

                c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    derp = (AbstractCard)c.next();
                    if (derp.cardID.equals("Exhume")) {
                        c.remove();
                        this.exhumes.add(derp);
                    }
                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.exhumes);
                    this.exhumes.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, this.num, true, TEXT[0]);
                    this.tickDuration();
                }
            }
        } else {
            Iterator c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); derp.unhover()) {
                    derp = (AbstractCard)c.next();
                    this.p.hand.addToHand(derp);
                    if (AbstractDungeon.player.hasPower("Corruption") && derp.type == AbstractCard.CardType.SKILL) {
                        derp.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(derp);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();

                for(c = this.p.exhaustPile.group.iterator(); c.hasNext(); derp.target_y = 0.0F) {
                    derp = (AbstractCard)c.next();
                    derp.unhover();
                    derp.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
