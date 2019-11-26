package lobotomyMod.action.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

/**
 * @author hoykj
 */
public class LatterAction extends AbstractGameAction {
    private Runnable action;

    public LatterAction(final Runnable action){
        this(action, 0);
    }

    public LatterAction(final Runnable action, float lateTime){
        this.action = action;
        this.duration = lateTime;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0.0F){
            this.action.run();
            this.isDone = true;
        }
    }
}
