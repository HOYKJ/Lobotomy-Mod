package lobotomyMod.power;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author hoykj
 */
public class YinYangPower extends AbstractPower {
    public static final String POWER_ID = "YinYangPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public YinYangPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "YinYangPower";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("regrow");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("YinYangPower");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
