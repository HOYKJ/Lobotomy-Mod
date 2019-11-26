package lobotomyMod.power;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.action.unique.TurnEndAction;

/**
 * @author hoykj
 */
public class BleedPower extends AbstractPower {
    public static final String POWER_ID = "BleedPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("BleedPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BleedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "BleedPower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/BleedPower.png");
        this.type = PowerType.DEBUFF;
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, 2, DamageInfo.DamageType.HP_LOSS)));
            if(this.amount > 1){
                this.amount -= 1;
            }
            else if(this.amount == 1){
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
