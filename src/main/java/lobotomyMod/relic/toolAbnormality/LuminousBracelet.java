package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.LuminousBraceletRelic;

/**
 * @author hoykj
 */
public class LuminousBracelet extends AbstractLobotomyAbnRelic {
    public static final String ID = "LuminousBracelet";

    public LuminousBracelet()
    {
        super("LuminousBracelet",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(15, true);
        this.counter = 0;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        super.onLoseHp(damageAmount);
        this.counter = 0;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 1),1));
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.counter ++;
        if(this.counter > 5){
            this.counter = 0;
            AbstractDungeon.player.maxHealth -= 10;
            if(AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth){
                AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
            }
            AbstractDungeon.player.healthBarUpdatedEvent();
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new LuminousBraceletRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new LuminousBracelet();
    }
}
