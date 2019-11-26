package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class RedFixerLaserEffect extends AbstractGameEffect
{
    private float sX;
    private float sY;
    private static TextureAtlas.AtlasRegion img;

    public RedFixerLaserEffect(float sX, float sY, float dX, float dY, float duration)
    {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }
        this.sX = sX;
        this.sY = sY;

        this.color = new Color(0.1F, 0.54F, 0.77F, 1.0F);
        this.duration = duration;
        this.startingDuration = duration;

        this.rotation = MathUtils.atan2(dX - sX, dY - sY);
        this.rotation *= 57.295776F;
        this.rotation = (this.rotation + 90.0F);
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - this.startingDuration / 2.0F) / this.startingDuration);
        } else {
            this.color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, this.duration / this.startingDuration);
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.59F, 0.1F, 0.1F, this.color.a));
        sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F, 0.0F, img.packedHeight / 2.0F, Settings.WIDTH, 100.0F,
                this.scale + MathUtils.random(-0.02F, 0.02F), this.scale, this.rotation);

        sb.setColor(this.color);
        sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F - 25.0F * Settings.scale, 0.0F, img.packedHeight / 2.0F, Settings.WIDTH, 50.0F,
                this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, this.rotation);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}
