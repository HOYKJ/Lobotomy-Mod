package lobotomyMod.vfx.ordeal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyFontHelper;

/**
 * @author hoykj
 */
public class OrdealTitleWord extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OrdealTitle");
    public static final String[] TEXT = uiStrings.TEXT;
    private GlyphLayout gl = new GlyphLayout();
    private String word;
    private float x, y;

    public OrdealTitleWord(int code, int difficulty){
        this.duration = 6;
        this.startingDuration = this.duration;
        switch (code){
            case 1:
                this.color = new Color(113 / 255.0F, 155 / 255.0F, 91 / 255.0F, 1.0F);
                break;
            case 2:
                this.color = new Color(252 / 255.0F, 149 / 255.0F, 11 / 255.0F, 1.0F);
                break;
            case 3:
                this.color = new Color(160 / 255.0F, 105 / 255.0F, 233 / 255.0F, 1.0F);
                break;
            case 4:
                this.color = new Color(177 / 255.0F, 38 / 255.0F, 69 / 255.0F, 1.0F);
                break;
            case 5:
                this.color = new Color(69 / 255.0F, 111 / 255.0F, 255 / 255.0F, 1.0F);
                break;
            default:
                this.color = Color.WHITE.cpy();
                break;
        }
        this.color.a = 0;

        this.word = TEXT[code - 1];
        this.word += TEXT[difficulty + 5];
        this.x = Settings.WIDTH / 2.0F;
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            this.isDone = true;
        }

        if(this.duration > 5) {
            this.color.a = 6 - this.duration;
        }
        else if(this.duration < 1){
            this.color.a = this.duration;
        }
        else {
            this.color.a = 1;
        }

        this.gl.setText(LobotomyFontHelper.Font_28, this.word);
        this.x = Settings.WIDTH / 2.0F - this.gl.width / 2.0F;
        this.y = Settings.HEIGHT / 2.0F + 100.0F * Settings.scale + this.gl.height / 2.0F;
    }

    @Override
    public void render(SpriteBatch sb) {
        FontHelper.renderSmartText(sb, LobotomyFontHelper.Font_28, this.word, this.x, this.y, Settings.WIDTH, 0.0F, this.color);
    }

    public void dispose(){

    }
}
