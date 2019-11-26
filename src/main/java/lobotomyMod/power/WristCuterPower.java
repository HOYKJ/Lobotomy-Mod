package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author hoykj
 */
public class WristCuterPower extends AbstractPower {
    public static final String POWER_ID = "WristCuterPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("WristCuterPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WristCuterPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "WristCuterPower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/WristCuterPower.png");
        this.type = PowerType.DEBUFF;
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS)
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount));
        return super.onAttacked(info, damageAmount);
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
