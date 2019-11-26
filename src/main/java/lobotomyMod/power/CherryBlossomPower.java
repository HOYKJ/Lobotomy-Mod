package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author hoykj
 */
public class CherryBlossomPower extends AbstractPower {
    public static final String POWER_ID = "CherryBlossomPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("CherryBlossomPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CherryBlossomPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "CherryBlossomPower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/CherryBlossomPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if(m == null){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
