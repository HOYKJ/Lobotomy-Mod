package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.relic.ApostleMask;

/**
 * @author hoykj
 */
public class Gospel extends AbstractPower {
    public static final String POWER_ID = "Gospel";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Gospel");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Gospel(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Gospel";
        this.owner = owner;
        this.amount = -1;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/Gospel.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        ApostleMask.heal = true;
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
