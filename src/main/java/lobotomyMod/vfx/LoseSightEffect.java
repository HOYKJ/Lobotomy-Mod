package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class LoseSightEffect extends AbstractGameEffect
{
    private boolean end = false;

    public LoseSightEffect()
    {
        this.duration = 1.0F;
        this.color = Color.BLACK.cpy();
    }

    public void update()
    {
        float targetAlpha = 0.75F;
        this.duration -= Gdx.graphics.getDeltaTime();

        if(this.end){
            this.color.a = targetAlpha * this.duration;
            if(this.duration <= 0){
                this.color.a = 0;
                this.isDone = true;
            }
        }
        else {
            this.color.a = targetAlpha * (1 - this.duration);
            if(this.duration <= 0){
                this.color.a = targetAlpha;
            }
        }
    }

    public void end(){
        this.end = true;
        this.duration = 1.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose() {}
}
