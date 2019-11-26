package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class BackwardEffect extends AbstractGameEffect {

    public BackwardEffect(){
        this.color = Color.WHITE.cpy();
        this.duration = 2.0F;
        this.startingDuration = this.duration;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();

        this.color.a = this.duration / this.startingDuration;
        if(this.duration <= 0){
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(LobotomyImageMaster.BACKWARD_BACK, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose(){
    }
}
