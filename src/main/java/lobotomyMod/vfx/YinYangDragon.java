package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class YinYangDragon extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private Texture img;

    public YinYangDragon(){
        this.duration = 3.2F;
        this.startingDuration = this.duration;
        this.img = LobotomyImageMaster.YIN_YANG_DRAGON;
        this.color = Color.WHITE.cpy();

        this.x = 0.0F - this.img.getWidth() / 1.0F;
        this.y = Settings.HEIGHT / 2.0F - this.img.getHeight() / 2.0F;
        this.vX = MathUtils.random(2000.0F * Settings.scale, 2400.0F * Settings.scale);
        CardCrawlGame.sound.play("YinYang_Dragon");
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            this.isDone = true;
        }

        this.x += this.vX * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.getWidth(), this.img.getHeight());
    }

    public void dispose(){}
}
