package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.event.ApocalypseBirdEvent;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.WhiteNightMonster;

/**
 * @author hoykj
 */
public class BlackForestStory extends AbstractGameEffect {
    private GlyphLayout gl = new GlyphLayout();
    private String word;
    private float x, y;
    private Texture img;
    private int code;

    public BlackForestStory(int code){
        this.duration = 6;
        this.startingDuration = this.duration;
        this.word = ApocalypseBirdEvent.DESCRIPTIONS[code];
        this.color = Color.WHITE.cpy();
        this.color.a = 0;
        this.code = code;
        this.x = Settings.WIDTH / 2.0F;

        switch (code) {
            case 2:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[0];
                break;
            case 4:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[2];
                break;
            case 6:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[4];
                break;
            case 7:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[6];
                break;
            case 9:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[1];
                break;
            case 11:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[3];
                break;
            case 13:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[5];
                break;
            case 15:
                this.img = LobotomyImageMaster.BLACK_FOREST_BACK[7];
                break;
            default:
                this.img = null;
        }

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

        this.gl.setText(FontHelper.largeDialogOptionFont, this.word);
        this.x = Settings.WIDTH / 2.0F - this.gl.width / 2.0F;
        switch (this.code){
            case 6:
                this.y = 100 - this.gl.height / 2.0F;
                break;
            case 2: case 4: case 7: case 9: case 11: case 13:
                this.y = 140 - this.gl.height / 2.0F;
                break;
            case 3: case 5: case 8: case 10: case 12: case 14:
                this.y = 80 - this.gl.height / 2.0F;
                break;
            case 15:
                this.y = 160 - this.gl.height / 2.0F;
                break;
            case 16:
                this.y = 110 - this.gl.height / 2.0F;
                break;
            case 17:
                this.y = 60 - this.gl.height / 2.0F;
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.img != null) {
            sb.setColor(this.color);
            sb.draw(this.img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }

        FontHelper.renderSmartText(sb, FontHelper.largeDialogOptionFont, this.word, this.x, this.y, Settings.WIDTH, 0.0F, this.color);
    }

    public void dispose(){

    }
}
