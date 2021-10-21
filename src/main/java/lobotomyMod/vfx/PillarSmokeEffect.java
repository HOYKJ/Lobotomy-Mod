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
public class PillarSmokeEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX, vY;
    private float aV;
    private float startDur;
    private float targetScale;
    private TextureAtlas.AtlasRegion img;
    private float maxA;

    public PillarSmokeEffect(float x, float y, Color color, float rotation) {
        this.color = color.cpy();
        this.color.r += MathUtils.random(-0.05F, 0.05F);
        this.color.g += MathUtils.random(-0.05F, 0.05F);
        this.color.b += MathUtils.random(-0.05F, 0.05F);
        this.maxA = this.color.a;

        if (MathUtils.randomBoolean())
        {
            this.img = ImageMaster.EXHAUST_L;
            this.duration = MathUtils.random(2.0F, 2.5F);
            this.targetScale = MathUtils.random(0.5F, 0.7F);
        }
        else
        {
            this.img = ImageMaster.EXHAUST_S;
            this.duration = MathUtils.random(2.0F, 2.5F);
            this.targetScale = MathUtils.random(0.5F, 0.6F);
        }
        this.startDur = this.duration;

        float tx = MathUtils.random(-10.0F * Settings.scale, 350.0F * Settings.scale), ty = MathUtils.random(-40.0F * Settings.scale, 40.0F * Settings.scale);
        this.x = (x + tx * MathUtils.cos((float) Math.toRadians(rotation)) + ty * MathUtils.sin((float) Math.toRadians(rotation)) - this.img.packedWidth / 2.0F);
        this.y = (y + tx * MathUtils.sin((float) Math.toRadians(rotation)) + ty * MathUtils.cos((float) Math.toRadians(rotation)) - this.img.packedHeight / 2.0F);
        this.scale = 0.01F;
        this.rotation = MathUtils.random(360.0F);
        this.aV = MathUtils.random(-250.0F, 250.0F);
        this.vX = MathUtils.random(-40.0F * Settings.scale, 40.0F * Settings.scale);
        this.vY = MathUtils.random(-40.0F * Settings.scale, 40.0F * Settings.scale);
        this.renderBehind = true;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        //this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        //this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.exp10Out.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);
        if (this.duration < 0.33F) {
            this.color.a = (this.duration * 3.0F) * this.maxA;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}
