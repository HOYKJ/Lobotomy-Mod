package lobotomyMod.power;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.Apostles.AbstractApostleCard;

/**
 * @author hoykj
 */
public class InspiredPower extends AbstractPower {
    public static final String POWER_ID = "InspiredPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("InspiredPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public InspiredPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "InspiredPower";
        this.owner = owner;
        this.amount = amount;
        this.img = null;
        this.type = PowerType.BUFF;
        updateDescription();
        this.priority = 999;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount > 4){
            this.amount = 4;
        }
        if(this.amount > 2){
            this.name = DESCRIPTIONS[0];
        }
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if((card instanceof AbstractLobotomyCard) || (card instanceof AbstractApostleCard)){
            return;
        }
        if(card.type == AbstractCard.CardType.SKILL){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount * 5)));
        }
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[this.amount];
    }
}
