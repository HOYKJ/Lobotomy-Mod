package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.TreeSapRelic;

import java.util.List;

/**
 * @author hoykj
 */
public class TreeSap extends AbstractLobotomyAbnRelic {
    public static final String ID = "TreeSap";
    private int reduce;

    public TreeSap()
    {
        super("TreeSap",  RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.reduce = 15;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 6), 6));
        if(AbstractDungeon.cardRng.random(100) < this.reduce){
            AbstractDungeon.player.maxHealth -= 15;
            if(AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth){
                AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
            }
            AbstractDungeon.player.healthBarUpdatedEvent();
            this.reduce = 15;
        }
        else {
            this.reduce += 5;
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new TreeSapRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new TreeSap();
    }
}
