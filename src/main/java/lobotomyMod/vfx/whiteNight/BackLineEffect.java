package lobotomyMod.vfx.whiteNight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.monster.WhiteNightMonster;

/**
 * @author hoykj
 */
public class BackLineEffect extends AbstractGameEffect {
    private float x, y;
    private float scaX, scaY;

    public BackLineEffect(float x, float y, int i){
        this.duration = 2F;
        this.startingDuration = this.duration;
        this.color = Color.WHITE.cpy();
        this.color.a = 0F;
        this.renderBehind = true;
        this.rotation = 30 * i;
        this.scale = 1.0F / 1.5F;
        this.scaX = 1;
        this.scaY = 1.2F;

        this.x = x;
        this.y = y;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();

        if(this.duration > this.startingDuration - 1.0F / 3.0F){
            this.color.a = (this.startingDuration - this.duration) * 3;
        }
        else if(this.duration > 0.75F){
            this.color.a = 1;
        }
        else if(this.duration > 0){
            this.color.a = this.duration / 0.75F;
        }
        else {
            this.isDone = true;
        }

        this.scaX = 1.4F - 0.2F * this.duration;
        this.scaY = 0.8F + 0.2F * this.duration;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(WhiteNightMonster.line, this.x, this.y - WhiteNightMonster.line.packedHeight / 2.0F, 0, WhiteNightMonster.line.packedHeight / 2.0F,
                WhiteNightMonster.line.packedWidth, WhiteNightMonster.line.packedHeight, this.scale * this.scaX, this.scale * this.scaY, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose(){}
}
