package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class YinYangAction extends AbstractGameAction {

    public YinYangAction(){
    }

    public void update(){
        this.isDone = true;

        float tmp = AbstractDungeon.player.currentHealth / (float)AbstractDungeon.player.maxHealth;
        AbstractDungeon.player.currentHealth = (int) (AbstractDungeon.player.maxHealth * (1 - tmp));
        AbstractDungeon.player.healthBarUpdatedEvent();
        if(AbstractDungeon.player.currentHealth < 1){
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }

        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            card.costForTurn = 3 - card.cost;

            if (card.cost != card.costForTurn) {
                card.isCostModified = true;
            }

            card.cost = card.costForTurn;
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            card.costForTurn = 3 - card.cost;

            if (card.cost != card.costForTurn) {
                card.isCostModified = true;
            }

            card.cost = card.costForTurn;
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            card.costForTurn = 3 - card.cost;

            if (card.cost != card.costForTurn) {
                card.isCostModified = true;
            }

            card.cost = card.costForTurn;
        }

        for (int i = (AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1); i >= 0; i--) {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if ((!(target.isDying)) && (target.currentHealth > 0) && (!(target.isEscaping))) {
                tmp = target.currentHealth / (float)target.maxHealth;
                target.currentHealth = (int) (target.maxHealth * (1 - tmp));
                target.healthBarUpdatedEvent();
                if(target.currentHealth < 1){
                    AbstractDungeon.getCurrRoom().cannotLose = false;
                    target.die();
                }
            }
        }

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.cleanCardQueue();
            AbstractDungeon.effectList.add(new DeckPoofEffect(64.0F * Settings.scale, 64.0F * Settings.scale, true));
            AbstractDungeon.effectList.add(new DeckPoofEffect((float)Settings.WIDTH - 64.0F * Settings.scale, 64.0F * Settings.scale, false));
            AbstractDungeon.overlayMenu.hideCombatPanels();
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                AbstractDungeon.getCurrRoom().endBattle();
            }, 1.8F));
        }
    }
}
