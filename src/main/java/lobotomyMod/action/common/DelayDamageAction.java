package lobotomyMod.action.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author hoykj
 */
public class DelayDamageAction extends AbstractGameAction {
    private AbstractCreature target;
    private DamageInfo info;
    private boolean end;

    public DelayDamageAction(){
        this(AbstractDungeon.player);
    }

    public DelayDamageAction(AbstractCreature target){
        this.info = null;
        this.target = target;
    }

    public void update(){
        if(this.info != null){
            this.target.damage(this.info);
            this.info = null;
        }

        if(this.end){
            this.isDone = true;
        }
    }

    public void damage(DamageInfo info){
        this.info = info;
    }

    public void end(){
        this.isDone = true;
    }

    public void laterEnd(){
        this.end = true;
    }
}
