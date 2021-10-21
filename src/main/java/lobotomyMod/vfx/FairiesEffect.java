package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class FairiesEffect extends AbstractGameEffect {
    private float interval = 0.0F, x;

    public FairiesEffect(float x) {
        this.duration = 0.5F;
        switch (MathUtils.random(3)){
            case 0:
                this.color = new Color(0.79F, 0.15F, 0.25F, 1);
                break;
            case 1:
                this.color = new Color(0.93F, 0.91F, 0.76F, 1);
                break;
            case 2:
                this.color = new Color(0.61F, 0.37F, 0.64F, 1);
                break;
            default:
                this.color = new Color(0.27F, 0.78F, 0.72F, 1);
                break;
        }
        this.x = x;
    }

    public void update()
    {
        this.interval -= Gdx.graphics.getDeltaTime();
        if (this.interval < 0.0F)
        {
            this.interval = 0.02F;
            this.x -= Settings.WIDTH / 20.0F;
            int derp = MathUtils.random(2, 4);
            for (int i = 0; i < derp; i++) {
                AbstractDungeon.effectsQueue.add(new FairiesDamageEffect(x + MathUtils.random(-100.0F, 100.0F) * Settings.scale,
                        AbstractDungeon.floorY + MathUtils.random(0.0F, 300.0F) * Settings.scale, this.color));
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}
