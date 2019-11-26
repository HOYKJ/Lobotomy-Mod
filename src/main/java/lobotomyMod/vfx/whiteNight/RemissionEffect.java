package lobotomyMod.vfx.whiteNight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.monster.WhiteNightMonster;

/**
 * @author hoykj
 */
public class RemissionEffect extends AbstractGameEffect {
    private float x, y;

    public RemissionEffect(Hitbox hb){
        this.duration = 2.0F;
        this.startingDuration = this.duration;
        this.color = Color.WHITE.cpy();
        this.color.a = 0.8F;
        this.renderBehind = true;
        this.scale = 1.0F / 1.5F;

        this.x = hb.cX + 10.0F;
        this.y = hb.cY + 35.0F;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();

        this.color.a = 0.8F * this.duration / this.startingDuration;
        this.scale = 1.0F / (1.3F * this.duration / this.startingDuration + 0.2F);

        if(this.duration <= 0){
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(WhiteNightMonster.halo, this.x - WhiteNightMonster.halo.packedWidth / 2.0F, this.y - WhiteNightMonster.halo.packedHeight / 2.0F, WhiteNightMonster.halo.packedWidth / 2.0F,
                WhiteNightMonster.halo.packedHeight / 2.0F, WhiteNightMonster.halo.packedWidth, WhiteNightMonster.halo.packedHeight, this.scale / 1.1F, this.scale / 1.1F, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose(){}
}
