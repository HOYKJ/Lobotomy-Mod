package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.LobotomyMod;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.sephirah.Angela;

/**
 * @author hoykj
 */
public class SmallShieldPower extends AbstractPower {
    public static final String POWER_ID = "SmallShieldPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SmallShieldPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private Angela angela;

    public SmallShieldPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "SmallShieldPower";
        this.owner = owner;
        this.amount = -1;
        this.img = LobotomyImageMaster.SMALL_SHIELD[0];
        this.type = PowerType.BUFF;
        this.angela = (Angela) owner;
        updateDescription();
    }

    public void updateDescription() {
        this.img = LobotomyImageMaster.SMALL_SHIELD[this.angela.nextSmall];
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[this.angela.nextSmall + 1] + DESCRIPTIONS[4];
    }
}
