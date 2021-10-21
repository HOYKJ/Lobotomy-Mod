package lobotomyMod.action.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.monster.Ordeal.Machine.MachineDawn;
import lobotomyMod.monster.Ordeal.Machine.MachineNoon;

/**
 * @author hoykj
 */
public class CreateMachineAction extends AbstractGameAction
{
    private float x, y;
    private boolean type;
    private AbstractMonster creature;

    public CreateMachineAction()
    {
        this.type = AbstractDungeon.monsterRng.randomBoolean();
        this.duration = Settings.ACTION_DUR_XFAST;
        this.x = 999;
        this.y = 999;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_XFAST)
        {
            int count = 0;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.isDying)
                {
                    this.x = m.drawX - Settings.WIDTH * 0.75F;
                    this.y = m.drawY - AbstractDungeon.floorY;
                    this.creature = m;
                }
                else {
                    count++;
                }
            }
            if(this.creature != null){
                AbstractDungeon.getMonsters().monsters.remove(this.creature);
            }
            if (count == 1) {
                if(this.x == 999){
                    this.x = -100.0F;
                }
                if(this.y == 999){
                    this.y = 0.0F;
                }
            } else if (count == 2) {
                if(this.x == 999){
                    this.x = -350.0F;
                }
                if(this.y == 999){
                    this.y = -20.0F;
                }
            } else if (count == 3) {
                if(this.x == 999){
                    this.x = -600.0F;
                }
                if(this.y == 999){
                    this.y = 0.0F;
                }
            }
            else {
                this.isDone = true;
                return;
            }
            if(this.type){
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new MachineNoon(this.x, this.y), false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
            }
            else {
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new MachineDawn(this.x, this.y), false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
            }
        }
        tickDuration();
    }
}
