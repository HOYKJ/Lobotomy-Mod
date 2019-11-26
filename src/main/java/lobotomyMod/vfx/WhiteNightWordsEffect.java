package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.WhiteNightMonster;

/**
 * @author hoykj
 */
public class WhiteNightWordsEffect extends AbstractGameEffect {
    private GlyphLayout gl = new GlyphLayout();
    private String word;
    private float x;
    private Texture img;

    public WhiteNightWordsEffect(int code){
        this.duration = 4;
        this.startingDuration = this.duration;
        this.word = WhiteNightMonster.DIALOG[code];
        this.color = Color.WHITE.cpy();
        this.color.a = 0;
        this.x = Settings.WIDTH / 2.0F;

        this.img = LobotomyImageMaster.CLOCK_SHADER;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            this.isDone = true;
        }

        if(this.duration > 3) {
            this.color.a = 4 - this.duration;
        }
        else if(this.duration < 1){
            this.color.a = this.duration;
        }
        else {
            this.color.a = 1;
        }

        this.gl.setText(FontHelper.losePowerFont, this.word);
        this.x = Settings.WIDTH / 2.0F - this.gl.width / 2.0F;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setBlendFunction(770, 771);

        FontHelper.renderSmartText(sb, FontHelper.losePowerFont, this.word, this.x, Settings.HEIGHT / 2.0F + 10.0F * Settings.scale, Settings.WIDTH, 0.0F, this.color);
    }

    public void dispose(){

    }
}
