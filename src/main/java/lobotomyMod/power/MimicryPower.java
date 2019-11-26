package lobotomyMod.power;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author hoykj
 */
public class MimicryPower extends AbstractPower {
    public static final String POWER_ID = "MimicryPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MimicryPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MimicryPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "MimicryPower";
        this.owner = owner;
        this.amount = -1;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/MimicryPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(damageType == DamageInfo.DamageType.THORNS || damageType == DamageInfo.DamageType.HP_LOSS){
            return 0;
        }
        return super.atDamageReceive(damage, damageType);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.THORNS || info.type == DamageInfo.DamageType.HP_LOSS){
            return 0;
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
