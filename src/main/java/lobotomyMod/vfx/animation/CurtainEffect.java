package lobotomyMod.vfx.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;

/**
 * @author hoykj
 */
public class CurtainEffect extends AbstractGameEffect {
    private Texture ImgLeft;
    private Texture ImgRight;
    private Texture ImgMid;
    private float outCurtainX;
    private float curtainScaleX;
    private float timer;
    private boolean end;

    public CurtainEffect()
    {
        this.startingDuration = 7.0F;
        this.duration = this.startingDuration;

        this.ImgLeft = ImageMaster.loadImage("lobotomyMod/images/texture/CurtainUpperLeft.png");
        this.ImgRight = ImageMaster.loadImage("lobotomyMod/images/texture/CurtainUpperRight.png");
        this.ImgMid = ImageMaster.loadImage("lobotomyMod/images/texture/Curtain.png");

        this.outCurtainX = -Settings.WIDTH / 2.0F;
        this.curtainScaleX = 0;
        this.timer = 0.4F;
        LobotomyMod.logger.info(Settings.WIDTH + "          " + Settings.HEIGHT);

        this.end = false;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(!this.end){
            if(this.duration > this.startingDuration - 2.0F){
                this.outCurtainX = (2.0F + this.duration - this.startingDuration) * (-Settings.WIDTH / 4.0F);
            }
            else if(this.duration > 3.0F){
                this.timer -= Gdx.graphics.getDeltaTime();
                if(this.timer <= 0) {
                    this.timer += 0.4F;
                    this.curtainScaleX = (5.0F - this.duration) / 2.0F;
                }
            }
            else if(this.duration > 2.0F){
                this.curtainScaleX = 1;
                this.timer = 0.4F;
            }
            else if(this.duration > 0){
                this.timer -= Gdx.graphics.getDeltaTime();
                if(this.timer <= 0) {
                    this.timer += 0.4F;
                    this.curtainScaleX = this.duration / 2.0F;
                }
            }
            else {
                this.curtainScaleX = 0;
            }
        }
        else {
            if(this.duration > 5.0F){
                this.timer -= Gdx.graphics.getDeltaTime();
                if(this.timer <= 0) {
                    this.timer += 0.4F;
                    this.curtainScaleX = (7.0F - this.duration) / 2.0F;
                }
            }
            else if(this.duration > 4.0F){
                this.curtainScaleX = 1;
                this.timer = 0.4F;
            }
            else if(this.duration > 2.0F){
                this.timer -= Gdx.graphics.getDeltaTime();
                if(this.timer <= 0) {
                    this.timer += 0.4F;
                    this.curtainScaleX = (this.duration - 2.0F) / 2.0F;
                }
            }
            else if(this.duration > 0){
                this.curtainScaleX = 0;
                this.outCurtainX = (2 - this.duration) * (-Settings.WIDTH / 4.0F);
            }
            else {
                this.outCurtainX = -Settings.WIDTH / 2.0F;
                this.isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE.cpy());
//        sb.draw(this.ImgMid, 0.0F, 0.0F, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
//                Settings.WIDTH * this.curtainScaleX * 1.02F, Settings.HEIGHT, Settings.scale * 1.2F, Settings.scale * 1.2F,
//                0.0F, 0, 0, 512, 288, false, false);
//        sb.draw(this.ImgMid, Settings.WIDTH  * ((1 - this.curtainScaleX) * 1.02F - 0.02F), 0.0F, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
//                Settings.WIDTH * this.curtainScaleX * 1.02F, Settings.HEIGHT, Settings.scale * 1.2F, Settings.scale * 1.2F,
//                0.0F, 0, 0, 512, 288, true, false);

//        sb.draw(this.ImgLeft, this.outCurtainX, 0.0F, Settings.WIDTH, Settings.HEIGHT,
//                Settings.WIDTH, Settings.HEIGHT, Settings.scale * 1.2F, Settings.scale * 1.2F, 0.0F, 0, 0, 512, 288, false, false);
//        sb.draw(this.ImgRight, -this.outCurtainX, 0.0F, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
//                Settings.WIDTH, Settings.HEIGHT, Settings.scale * 1.2F, Settings.scale * 1.2F, 0.0F, 0, 0, 512, 288, false, false);

        sb.draw(this.ImgMid, 0.0F, 0.0F, Settings.WIDTH * this.curtainScaleX * 1.02F, Settings.HEIGHT);
        sb.draw(this.ImgMid, Settings.WIDTH  * ((1 - this.curtainScaleX) * 1.02F - 0.02F), 0.0F, Settings.WIDTH * this.curtainScaleX * 1.02F, Settings.HEIGHT,
                0, 0, 512, 288, true, false);

        sb.draw(this.ImgLeft, this.outCurtainX, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.draw(this.ImgRight, -this.outCurtainX, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void end(){
        this.end = true;
        this.duration = this.startingDuration;
    }

    public void dispose(){}
}
