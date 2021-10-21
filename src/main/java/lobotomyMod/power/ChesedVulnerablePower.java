package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.monster.sephirah.Chesed;

/**
 * @author hoykj
 */
public class ChesedVulnerablePower extends AbstractPower {
    public static final String POWER_ID = "ChesedVulnerablePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public ChesedVulnerablePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "ChesedVulnerablePower";
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("vulnerable");
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return damage * (this.amount / 100.0F + 1);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("ChesedVulnerablePower");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
