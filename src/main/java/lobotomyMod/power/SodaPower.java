package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
public class SodaPower extends AbstractPower {
    public static final String POWER_ID = "SodaPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SodaPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int counter;

    public SodaPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "SodaPower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/SodaPower.png");
        this.type = PowerType.BUFF;
        this.counter = 3;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        this.counter --;
        if(this.counter <= 0){
            this.counter = 3;
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.amount));
        }
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}
