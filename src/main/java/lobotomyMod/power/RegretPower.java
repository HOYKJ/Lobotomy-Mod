package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;

/**
 * @author hoykj
 */
public class RegretPower extends AbstractPower {
    public static final String POWER_ID = "RegretPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("RegretPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RegretPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "RegretPower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/RegretPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new MetallicizePower(this.owner, this.amount), this.amount));
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
