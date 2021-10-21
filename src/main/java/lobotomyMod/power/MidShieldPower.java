package lobotomyMod.power;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.sephirah.Angela;

/**
 * @author hoykj
 */
public class MidShieldPower extends AbstractPower {
    public static final String POWER_ID = "MidShieldPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MidShieldPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private Angela angela;

    public MidShieldPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "MidShieldPower";
        this.owner = owner;
        this.amount = -1;
        this.img = LobotomyImageMaster.MID_SHIELD[0];
        this.type = PowerType.BUFF;
        this.angela = (Angela) owner;
        updateDescription();
    }

    public void updateDescription() {
        this.img = LobotomyImageMaster.MID_SHIELD[this.angela.nextMid];
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[this.angela.nextMid + 1] + DESCRIPTIONS[5];
    }
}
