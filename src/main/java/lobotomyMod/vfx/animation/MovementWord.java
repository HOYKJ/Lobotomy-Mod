package lobotomyMod.vfx.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;

/**
 * @author hoykj
 */
public class MovementWord extends AbstractGameEffect {
    private Texture Img;

    public MovementWord(int movement)
    {
        this.startingDuration = 4.0F;
        this.duration = this.startingDuration;

        switch (movement){
            case 1:
                this.Img = ImageMaster.loadImage("lobotomyMod/images/texture/Movement1.png");
                break;
            case 2:
                this.Img = ImageMaster.loadImage("lobotomyMod/images/texture/Movement2.png");
                break;
            case 3:
                this.Img = ImageMaster.loadImage("lobotomyMod/images/texture/Movement3.png");
                break;
            case 4:
                this.Img = ImageMaster.loadImage("lobotomyMod/images/texture/Movement4.png");
                break;
            case 5:
                this.Img = ImageMaster.loadImage("lobotomyMod/images/texture/Finale.png");
                break;
        }

        this.color = Color.WHITE.cpy();
        this.color.a = 0.0F;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();

        if(this.duration > 3){
            this.color.a = 4 - this.duration;
        }
        else if(this.duration > 1){
            this.color.a = 1;
        }
        else if(this.duration > 0){
            this.color.a = this.duration;
        }
        else {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(this.Img, Settings.WIDTH / 2.0F - 256.0F, Settings.HEIGHT / 2.0F - 50.0F, 256.0F, 50.0F, 512.0F, 100.0F,
                Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 100, false, false);
    }

    public void dispose(){}
}
