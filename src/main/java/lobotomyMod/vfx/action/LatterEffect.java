package lobotomyMod.vfx.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LatterEffect extends AbstractGameEffect {
    private Runnable action;

    public LatterEffect(final Runnable action){
        this(action, 0);
    }

    public LatterEffect(final Runnable action, float lateTime){
        this.action = action;
        this.duration = lateTime;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0.0F){
            this.isDone = true;
            this.action.run();
        }
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    public void dispose(){}
}
