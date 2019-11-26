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
public class BloodyDesirePower extends AbstractPower {
    public static final String POWER_ID = "BloodyDesirePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("BloodyDesirePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BloodyDesirePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "BloodyDesirePower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/BloodyDesirePower.png");
        this.type = AbstractPower.PowerType.DEBUFF;
        updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        return super.onLoseHp(damageAmount);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
