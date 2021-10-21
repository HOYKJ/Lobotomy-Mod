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
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class HellTrainEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private Texture img;

    public HellTrainEffect(){
        this.duration = 6.2F;
        this.startingDuration = this.duration;
        this.img = LobotomyImageMaster.HELL_TRAIN;
        this.color = Color.WHITE.cpy();

        this.x = 0.0F + this.img.getWidth() * 4.0F;
        this.y = Settings.HEIGHT / 2.0F - this.img.getHeight();
        this.vX = -MathUtils.random(3000.0F * Settings.scale, 3600.0F * Settings.scale);
        CardCrawlGame.sound.play("Train_Move1");
        AbstractDungeon.effectsQueue.add(new LatterEffect(()->{
            CardCrawlGame.sound.play("Train_End");
        }, 12.0F));
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
        sb.draw(this.img, this.x, this.y, this.img.getWidth() * 2.0F, this.img.getHeight() * 2.0F);
    }

    public void dispose(){}
}
