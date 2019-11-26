package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
public class ChristmasPower extends AbstractPower {
    public static final String POWER_ID = "ChristmasPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ChristmasPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ChristmasPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "ChristmasPower";
        this.owner = owner;
        this.amount = -1;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/ChristmasPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat()));
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
