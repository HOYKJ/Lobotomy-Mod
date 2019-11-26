package lobotomyMod.power;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class PinkArmyPower extends AbstractPower {
    public static final String POWER_ID = "PinkArmyPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("PinkArmyPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PinkArmyPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "PinkArmyPower";
        this.owner = owner;
        this.amount = -1;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/PinkArmyPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage * 0.8F;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, 1));
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, c);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(LobotomyImageMaster.PINK_HEART, this.owner.hb.cX - 32.0F, this.owner.hb.y + this.owner.hb.height, 64.0F, 64.0F);
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
