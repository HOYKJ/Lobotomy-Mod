package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class SnipTrailEffect extends AbstractGameEffect {
    private float x, y;

    public SnipTrailEffect(float x, float y){
        this.color = Color.WHITE.cpy();
        this.duration = 0.6F;
        this.startingDuration = this.duration;
        this.x = x;
        this.y = y;
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
        sb.draw(LobotomyImageMaster.FREISCHUTZ_CURSOR, this.x - 32.0F + (24.0F * Settings.scale), this.y - 32.0F - (24.0F * Settings.scale), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
    }

    public void dispose(){

    }
}
