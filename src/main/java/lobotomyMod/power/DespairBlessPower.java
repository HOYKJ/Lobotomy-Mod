package lobotomyMod.power;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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
public class DespairBlessPower extends AbstractPower {
    public static final String POWER_ID = "DespairBlessPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("DespairBlessPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DespairBlessPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "DespairBlessPower";
        this.owner = owner;
        this.amount = -1;
        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/DespairBlessPower.png");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if((damageAmount > 0) && (info.type != DamageInfo.DamageType.HP_LOSS)){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, 5));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount * 1.5F;
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, c);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(LobotomyImageMaster.DESPAIR_BLESS, this.owner.hb.cX - 32.0F, this.owner.hb.y + this.owner.hb.height, 64.0F, 64.0F);
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
