package lobotomyMod.action.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

/**
 * @author hoykj
 */
public class DelayAction extends AbstractGameAction {

    public DelayAction(){
    }

    public void update(){

    }

    public void end(){
        this.isDone = true;
    }
}
