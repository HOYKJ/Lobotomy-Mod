package lobotomyMod.action.unique;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.ego.uncommon.FeatherOfHonor;
import lobotomyMod.card.ego.uncommon.FrostShard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author hoykj
 */
public class FeatherOfHonorAction extends AbstractGameAction {
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    private int[] damage;

    public FeatherOfHonorAction(AbstractCreature source, int amount, int[] damage) {
        this.shuffleCheck = false;
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.setValues(AbstractDungeon.player, source, amount);
            this.isDone = true;
            this.duration = 0.0F;
            this.actionType = ActionType.WAIT;
            return;
        }

        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }

        this.damage = damage;
    }

    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.isDone = true;
                } else if (AbstractDungeon.player.hand.size() == 10) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                } else {
                    if (!this.shuffleCheck) {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                            tmp = 10 - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > deckSize) {
                            tmp = this.amount - deckSize;
                            AbstractDungeon.actionManager.addToTop(new FeatherOfHonorAction(this.source, tmp, this.damage));
                            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0) {
                                AbstractDungeon.actionManager.addToTop(new FeatherOfHonorAction(this.source, deckSize, this.damage));
                            }

                            this.amount = 0;
                            this.isDone = true;
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty()) {
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                            AbstractCard card = AbstractDungeon.player.hand.group.get(AbstractDungeon.player.hand.size() - 1);
                            if(card.type == AbstractCard.CardType.ATTACK){
                                AbstractDungeon.actionManager.addToTop(new FeatherOfHonorAction(this.source, 1, this.damage));
                                AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS, AttackEffect.FIRE));
                            }
                            if(card instanceof FrostShard){
                                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                            }
                        } else {
                            logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.isDone = true;
                        }

                        if (this.amount == 0) {
                            this.isDone = true;
                        }
                    }

                }
            }
        }
    }
}
