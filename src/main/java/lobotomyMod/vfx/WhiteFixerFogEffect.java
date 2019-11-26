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
public class WhiteFixerFogEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float aV;
    private float targetAlpha = MathUtils.random(0.2F, 0.3F);
    private boolean flipX = MathUtils.randomBoolean();
    private boolean flipY = MathUtils.randomBoolean();
    private TextureAtlas.AtlasRegion img;

    public WhiteFixerFogEffect(float x, float y)
    {
        this.duration = MathUtils.random(3.0F, 3.5F);
        this.startingDuration = this.duration;
        switch (MathUtils.random(2))
        {
            case 0:
                this.img = ImageMaster.SMOKE_1;
                break;
            case 1:
                this.img = ImageMaster.SMOKE_2;
                break;
            default:
                this.img = ImageMaster.SMOKE_3;
        }
        this.x = x;
        this.y = y;
        this.aV = MathUtils.random(-30.0F, 30.0F);
        this.rotation = MathUtils.random(0.0F, 360.0F);
        float tmp = MathUtils.random(0.85F, 1.0F);
        this.color = new Color();
        this.color.r = tmp;
        this.color.g = (tmp - 0.03F);
        this.color.b = (tmp - 0.07F);
        this.scale = (MathUtils.random(1.6F, 3.0F) * Settings.scale);
    }

    public void update()
    {
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        if (this.startingDuration - this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(0.0F, this.targetAlpha, this.startingDuration - this.duration);
        } else if (this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(this.targetAlpha, 0.0F, 1.0F - this.duration);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        if ((this.flipX) && (!this.img.isFlipX())) {
            this.img.flip(true, false);
        } else if ((!this.flipX) && (this.img.isFlipX())) {
            this.img.flip(true, false);
        }
        if ((this.flipY) && (!this.img.isFlipY())) {
            this.img.flip(false, true);
        } else if ((!this.flipY) && (this.img.isFlipY())) {
            this.img.flip(false, true);
        }
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight,
                this.scale, this.scale, this.rotation);
    }

    public void dispose() {}
}
