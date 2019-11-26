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
public class WhiteNightEffectCore extends AbstractGameEffect {
    private float x, y;
    private float lineTimer;private float rotation2, rotation3;


    public WhiteNightEffectCore(Hitbox hb){
        this.color = Color.WHITE.cpy();
        this.color.a = 0.8F;
        this.renderBehind = true;
        this.rotation2 = 0;
        this.rotation3 = 90;
        this.scale = 1.0F / 1.5F;

        this.x = hb.cX + 10.0F;
        this.y = hb.cY + 35.0F;
        this.lineTimer = 2.0F / 3.0F;
    }

    public void update(){
        this.lineTimer -= Gdx.graphics.getDeltaTime();

        this.rotation2 -= Gdx.graphics.getDeltaTime() * 60;
        this.rotation3 += Gdx.graphics.getDeltaTime() * 60;

        if(this.lineTimer <= 0){
            this.lineTimer += 6;
            for(int i = 0; i < 7; i ++) {
                AbstractDungeon.effectsQueue.add(new BackLineEffect(this.x - 10.0F, this.y, i));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(WhiteNightMonster.halo, this.x - WhiteNightMonster.halo.packedWidth / 2.0F, this.y - WhiteNightMonster.halo.packedHeight / 2.0F, WhiteNightMonster.halo.packedWidth / 2.0F,
                WhiteNightMonster.halo.packedHeight / 2.0F, WhiteNightMonster.halo.packedWidth, WhiteNightMonster.halo.packedHeight, this.scale / 1.1F, this.scale / 1.1F, this.rotation);

        sb.draw(WhiteNightMonster.light, this.x - WhiteNightMonster.light.packedWidth / 2.0F, this.y - WhiteNightMonster.light.packedHeight / 2.0F, WhiteNightMonster.light.packedWidth / 2.0F,
                WhiteNightMonster.light.packedHeight / 2.0F, WhiteNightMonster.light.packedWidth, WhiteNightMonster.light.packedHeight, this.scale, this.scale, this.rotation2);

        sb.draw(WhiteNightMonster.light, this.x - WhiteNightMonster.light.packedWidth / 2.0F, this.y - WhiteNightMonster.light.packedHeight / 2.0F, WhiteNightMonster.light.packedWidth / 2.0F,
                WhiteNightMonster.light.packedHeight / 2.0F, WhiteNightMonster.light.packedWidth, WhiteNightMonster.light.packedHeight, this.scale, this.scale, this.rotation3);
        sb.setBlendFunction(770, 771);
    }

    public void dispose(){}
}
