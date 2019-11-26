package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.UUID;

/**
 * @author hoykj
 */
public class LampAttackAction extends AbstractGameAction {
    private DamageInfo info;
    private UUID uuid;

    public LampAttackAction(AbstractCreature target, DamageInfo info, UUID targetUUID) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.uuid = targetUUID;
    }

    public void update() {
        if ((this.duration == 0.1F) && (this.target != null)) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));

            this.target.damage(this.info);
            if (((this.target.isDying) || (this.target.currentHealth <= 0)) && (!this.target.halfDead)) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid.equals(this.uuid))
                    {
                        c.upgrade();
                    }
                }
                for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                    c.upgrade();
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
