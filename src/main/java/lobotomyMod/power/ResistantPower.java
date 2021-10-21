package lobotomyMod.power;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.monster.sephirah.Binah.Binah;
import lobotomyMod.monster.sephirah.Chesed;
import lobotomyMod.monster.sephirah.Geburah;

/**
 * @author hoykj
 */
public class ResistantPower extends AbstractPower {
    public static final String POWER_ID = "ResistantPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ResistantPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ResistantPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "ResistantPower";
        this.owner = owner;
        this.amount = -1;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/ResistantPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        this.updateDescription();
        if(this.owner instanceof Binah){
            return damage * ((Binah) this.owner).resistance;
        }
        else if(this.owner instanceof Chesed){
            return damage * ((Chesed) this.owner).resistance;
        }
        else if(this.owner instanceof Geburah){
            return damage * ((Geburah) this.owner).resistance;
        }
        return super.atDamageReceive(damage, damageType);
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
        if(this.owner instanceof Binah){
            this.description = DESCRIPTIONS[0] + (int)(((Binah) this.owner).resistance * 100) + DESCRIPTIONS[1];
        }
        else if(this.owner instanceof Chesed){
            this.description = DESCRIPTIONS[0] + (int)(((Chesed) this.owner).resistance * 100) + DESCRIPTIONS[1];
        }
        else if(this.owner instanceof Geburah){
            this.description = DESCRIPTIONS[0] + (int)(((Geburah) this.owner).resistance * 100) + DESCRIPTIONS[1];
        }
    }
}
