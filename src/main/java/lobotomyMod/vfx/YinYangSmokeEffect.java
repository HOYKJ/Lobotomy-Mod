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
public class YinYangSmokeEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private float aV;
    private float startDur;
    private float targetScale;
    private TextureAtlas.AtlasRegion img;

    public YinYangSmokeEffect(float x, float y, boolean Yin)
    {
        this.color = new Color(0.0F, 0.0F, 0.0F, 1.0F);

        if (MathUtils.randomBoolean())
        {
            this.img = ImageMaster.EXHAUST_L;
            this.duration = MathUtils.random(2.0F, 2.5F);
            this.targetScale = MathUtils.random(0.8F, 1.2F);
        }
        else
        {
            this.img = ImageMaster.EXHAUST_S;
            this.duration = MathUtils.random(2.0F, 2.5F);
            this.targetScale = MathUtils.random(0.8F, 1.0F);
        }
        this.startDur = this.duration;

        if(Yin) {
            this.color.r = MathUtils.random(0.32F, 0.36F);
            this.color.g = MathUtils.random(0.14F, 0.16F);
            this.color.b = 0.1F;

            this.x = (x + MathUtils.random(-80.0F * Settings.scale, 300.0F * Settings.scale) - this.img.packedWidth / 2.0F);
            this.y = (y + MathUtils.random(-40.0F * Settings.scale, 240.0F * Settings.scale) - this.img.packedHeight / 2.0F);
            this.scale = 0.01F;
            this.rotation = MathUtils.random(360.0F);
            this.aV = MathUtils.random(-250.0F, 250.0F);
            this.vY = MathUtils.random(40.0F * Settings.scale, 80.0F * Settings.scale);
        }
        else {
            this.color.r = MathUtils.random(0.65F, 0.77F);
            this.color.g = 1.0F;
            this.color.b = MathUtils.random(0.84F, 1.0F);

            this.x = (x - MathUtils.random(-80.0F * Settings.scale, 300.0F * Settings.scale) - this.img.packedWidth / 2.0F);
            this.y = (y + MathUtils.random(-40.0F * Settings.scale, 240.0F * Settings.scale) - this.img.packedHeight / 2.0F);
            this.scale = 0.01F;
            this.rotation = MathUtils.random(360.0F);
            this.aV = MathUtils.random(-250.0F, 250.0F);
            this.vY = -MathUtils.random(40.0F * Settings.scale, 80.0F * Settings.scale);
        }
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.x += this.vY * Gdx.graphics.getDeltaTime();
        this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.exp10Out.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);
        if (this.duration < 0.33F) {
            this.color.a = (this.duration * 3.0F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {}
}
