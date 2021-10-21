package lobotomyMod.power;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import lobotomyMod.monster.sephirah.Hokma;

/**
 * @author hoykj
 */
public class HokmaTimeWarpPower extends AbstractPower {
    public static final String POWER_ID = "HokmaTimeWarpPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("HokmaTimeWarpPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESC = powerStrings.DESCRIPTIONS;
    public Hokma hokma;

    public HokmaTimeWarpPower(Hokma owner)
    {
        this.name = NAME;
        this.ID = "HokmaTimeWarpPower";
        this.owner = owner;
        this.hokma = owner;
        this.amount = 0;
        updateDescription();
        loadRegion("time");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
    }

    public void updateDescription()
    {
        this.description = (DESC[0] + this.hokma.limit + DESC[1] + this.hokma.price + DESC[2]);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        flashWithoutSound();
        this.amount += 1;
        if (this.amount >= this.hokma.limit) {
            this.amount -= this.hokma.limit;
            playApplyPowerSfx();
            AbstractDungeon.actionManager.cardQueue.clear();
            for (AbstractCard c : AbstractDungeon.player.limbo.group) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
            }
            AbstractDungeon.player.limbo.group.clear();
            AbstractDungeon.player.releaseCard();
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);

            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, this.hokma.price), this.hokma.price));
            }
        }
        this.updateDescription();
    }
}
