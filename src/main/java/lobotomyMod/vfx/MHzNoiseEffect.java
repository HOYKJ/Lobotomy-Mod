package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class MHzNoiseEffect extends AbstractGameEffect {

    public MHzNoiseEffect(){
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("1.76_Active.mp3");
    }

    public void update(){

    }

    public void end(){
        this.isDone = true;
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        CardCrawlGame.music.unsilenceBGM();
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    public void dispose(){

    }
}
