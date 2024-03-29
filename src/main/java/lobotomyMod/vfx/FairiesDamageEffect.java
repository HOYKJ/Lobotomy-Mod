package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class FairiesDamageEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private TextureAtlas.AtlasRegion img;

    public FairiesDamageEffect(float x, float y, Color color)
    {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = (x - MathUtils.random(320.0F, 360.0F) - this.img.packedWidth / 2.0F);
        this.destY = y;
        this.y = (this.destY + MathUtils.random(-25.0F, 25.0F) * Settings.scale - this.img.packedHeight / 2.0F);
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = (Settings.scale * MathUtils.random(0.5F, 2.0F));
        this.rotation = MathUtils.random(-30.0F, 30.0F);
        this.color = color.cpy();
        this.color.r += MathUtils.random(-0.05F, 0.05F);
        this.color.g += MathUtils.random(-0.05F, 0.05F);
        this.color.b += MathUtils.random(-0.05F, 0.05F);
        this.rotation -= 180;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        if (this.duration > 0.2F) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 0.2F) * 5.0F);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 5.0F);
        }
        this.scale = Interpolation.bounceIn.apply(Settings.scale * 0.3F, Settings.scale * 0.6F, this.duration / 0.4F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth * 0.85F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale * 1.5F, this.rotation);

        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth * 0.85F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.75F, this.scale * 0.75F, this.rotation);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}
