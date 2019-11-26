package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.unique.YinYangAction;
import lobotomyMod.card.uncommonCard.Yin;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class YinYangMergeEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private Texture img;
    private boolean dragon;

    public YinYangMergeEffect(){
        this.duration = 6.0F;
        this.startingDuration = this.duration;
        this.img = LobotomyImageMaster.YIN_YANG_MERGE;
        this.color = Color.WHITE.cpy();
        this.dragon = false;

        this.x = Settings.WIDTH / 2.0F - this.img.getWidth() / 3.0F;
        this.y = Settings.HEIGHT / 2.0F - this.img.getHeight() / 3.0F;
        this.vY = -MathUtils.random(10.0F * Settings.scale, 20.0F * Settings.scale);
        CardCrawlGame.sound.play("YinYang_Merge");
    }

    public void update(){
        if(this.duration == this.startingDuration){
            for(int i = 0; i < 60; i ++){
                AbstractDungeon.effectsQueue.add(new YinYangSmokeEffect(Settings.WIDTH / 2.0F, this.y, true));
                AbstractDungeon.effectsQueue.add(new YinYangSmokeEffect(Settings.WIDTH / 2.0F, this.y, false));
            }
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToBottom(new YinYangAction());
            }, 4.5F));
        }
        else if(this.duration <= 3.0F){
            if(!this.dragon){
                this.dragon = true;
                AbstractDungeon.topLevelEffectsQueue.add(new YinYangDragon());
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.XLONG, false);
            }
        }
        if(this.duration <= 2.0F){
            this.color.a = this.duration / 2.0F;
        }
        if(this.duration <= 0){
            AbstractDungeon.player.masterDeck.removeCard(Yin.ID);
            this.isDone = true;
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        this.y += this.vY * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.getWidth() / 1.5F, this.img.getHeight() / 1.5F);
    }

    public void dispose(){}
}
