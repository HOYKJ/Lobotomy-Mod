package lobotomyMod.power;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author hoykj
 */
public class TwilightPower extends AbstractPower {
    public static final String POWER_ID = "TwilightPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TwilightPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TwilightPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "TwilightPower";
        this.owner = owner;
        this.img = null;
        this.type = PowerType.BUFF;
        updateDescription();
        this.priority = 999;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float more = (this.owner.maxHealth - this.owner.currentHealth) / (float)this.owner.maxHealth;
        return damage * (1 + more);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        updateDescription();
        return super.onLoseHp(damageAmount);
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
    }

    public void updateDescription()
    {
        float more = (this.owner.maxHealth - this.owner.currentHealth) / (float)this.owner.maxHealth * 100;
        this.description = DESCRIPTIONS[0] + more + DESCRIPTIONS[1];
    }
}
