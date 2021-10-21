package lobotomyMod.power;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.monster.sephirah.Malkuth;

/**
 * @author hoykj
 */
public class MalkuthConfusionPower extends AbstractPower
{
    public static final String POWER_ID = "MalkuthConfusionPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MalkuthConfusionPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private Malkuth malkuth;

    public MalkuthConfusionPower(AbstractCreature owner, Malkuth malkuth)
    {
        this.name = NAME;
        this.ID = "MalkuthConfusionPower";
        this.owner = owner;
        loadRegion("confusion");
        this.type = AbstractPower.PowerType.DEBUFF;
        this.malkuth = malkuth;
        updateDescription();
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
    }

    public void onCardDraw(AbstractCard card)
    {
        if (card.cost >= 0)
        {
            int newCost;
            if(this.malkuth.getTurns() >= 6){
                newCost = AbstractDungeon.cardRandomRng.random(2) + 1;
            }
            else {
                newCost = AbstractDungeon.cardRandomRng.random(3);
            }
            if (card.cost != newCost)
            {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
        }
        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.malkuth.getTurns() >= 6){
            this.description = DESCRIPTIONS[1];
        }
        else {
            this.description = DESCRIPTIONS[0];
        }
    }
}
