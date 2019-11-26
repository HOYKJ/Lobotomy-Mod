package lobotomyMod.monster.Ordeal.Fixer;

import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public abstract class AbstractFixer extends AbstractOrdealMonster {
    public boolean clear;

    public AbstractFixer(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        this.clear = false;
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if ((this.currentHealth <= 0) && (!this.halfDead))
        {
            this.halfDead = true;
            this.changeState("DIE");

            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                r.onMonsterDeath(this);
            }
            this.powers.clear();

            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((m instanceof AbstractFixer) && (!m.halfDead) && (!m.escaped)) {
                    allDead = false;
                }
            }
            if(allDead){
                this.clear = true;
                this.die();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof AbstractFixer) {
                        ((AbstractFixer) m).clear = true;
                        m.die();
                    }
                }
            }
            else {
                setMove((byte)9, Intent.NONE);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)9, Intent.NONE));
            }
        }
    }

    public void die() {
        if(this.clear) {
            super.die();
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
                if(AbstractDungeon.getCurrRoom().monsters.monsters.size() > 3){
                    AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(6, 3, true));
                    return;
                }
                if(AbstractDungeon.getCurrRoom().monsters.monsters.size() > 1){
                    AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(6, 2, true));
                    return;
                }
                AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(6, 1, true));
            }
        }
        else{
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((m instanceof AbstractFixer) && (!m.halfDead) && (!m.escaped)) {
                    allDead = false;
                }
            }
            if(allDead){
                this.clear = true;
                this.die();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof AbstractFixer) {
                        ((AbstractFixer) m).clear = true;
                        m.die();
                    }
                }
            }
        }
    }
}
