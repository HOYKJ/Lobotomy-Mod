package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

/**
 * @author hoykj
 */
public class NetzachDrawPower extends AbstractPower
{
    public static final String POWER_ID = "NetzachDrawPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NetzachDrawPower(AbstractCreature owner)
    {
        this.name = NAME;
        this.ID = "NetzachDrawPower";
        this.owner = owner;
        updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
        loadRegion("draw");
        this.isTurnBased = false;
        AbstractDungeon.player.gameHandSize += 2;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new NoDrawPower(this.owner)));
    }

    public void onRemove()
    {
        AbstractDungeon.player.gameHandSize -= 2;
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + 2 + DESCRIPTIONS[3]);
    }
}
