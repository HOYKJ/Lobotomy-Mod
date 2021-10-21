package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

/**
 * @author hoykj
 */
public class ShiftingPower2 extends AbstractPower {
    public static final String POWER_ID = "Shifting2";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public ShiftingPower2(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Shifting2";
        this.owner = owner;
        this.updateDescription();
        this.isPostActionPower = true;
        this.loadRegion("shift");
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -damageAmount), -damageAmount));
            if (!this.owner.hasPower("Artifact")) {
                this.addToTop(new ApplyPowerAction(this.owner, this.owner, new GainStrengthPower2(this.owner, damageAmount), damageAmount));
            }

            this.flash();
        }

        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Shifting");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
