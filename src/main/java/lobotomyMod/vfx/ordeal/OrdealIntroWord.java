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
public class OrdealIntroWord extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OrdealText");
    public static final String[] TEXT = uiStrings.TEXT;
    private GlyphLayout gl = new GlyphLayout();
    private GlyphLayout gl2 = new GlyphLayout();
    private GlyphLayout gl3 = new GlyphLayout();
    private String word, word2, word3;
    private float x, x2, x3, y, y2, y3;
    private int line;

    public OrdealIntroWord(int code, int difficulty, boolean end){
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

        getWord(code, difficulty, end);
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

        switch (this.line){
            case 1:
                this.gl.setText(LobotomyFontHelper.Font_22, this.word);
                this.x = Settings.WIDTH / 2.0F - this.gl.width / 2.0F;
                this.y = Settings.HEIGHT / 2.0F - 70.0F * Settings.scale + this.gl.height / 2.0F;
                break;
            case 2:
                this.gl.setText(LobotomyFontHelper.Font_22, this.word);
                this.x = Settings.WIDTH / 2.0F - this.gl.width / 2.0F;
                this.y = Settings.HEIGHT / 2.0F - 55.0F * Settings.scale + this.gl.height / 2.0F;
                this.gl2.setText(LobotomyFontHelper.Font_22, this.word2);
                this.x2 = Settings.WIDTH / 2.0F - this.gl2.width / 2.0F;
                this.y2 = Settings.HEIGHT / 2.0F - 90.0F * Settings.scale + this.gl2.height / 2.0F;
                break;
            case 3:
                this.gl.setText(LobotomyFontHelper.Font_22, this.word);
                this.x = Settings.WIDTH / 2.0F - this.gl.width / 2.0F;
                this.y = Settings.HEIGHT / 2.0F - 45.0F * Settings.scale + this.gl.height / 2.0F;
                this.gl2.setText(LobotomyFontHelper.Font_22, this.word2);
                this.x2 = Settings.WIDTH / 2.0F - this.gl2.width / 2.0F;
                this.y2 = Settings.HEIGHT / 2.0F - 80.0F * Settings.scale + this.gl2.height / 2.0F;
                this.gl3.setText(LobotomyFontHelper.Font_22, this.word3);
                this.x3 = Settings.WIDTH / 2.0F - this.gl3.width / 2.0F;
                this.y3 = Settings.HEIGHT / 2.0F - 115.0F * Settings.scale + this.gl3.height / 2.0F;
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        FontHelper.renderSmartText(sb, LobotomyFontHelper.Font_22, this.word, this.x, this.y, Settings.WIDTH, 0.0F, this.color);
        if(this.line > 1){
            FontHelper.renderSmartText(sb, LobotomyFontHelper.Font_22, this.word2, this.x2, this.y2, Settings.WIDTH, 0.0F, this.color);
        }
        if(this.line > 2){
            FontHelper.renderSmartText(sb, LobotomyFontHelper.Font_22, this.word3, this.x3, this.y3, Settings.WIDTH, 0.0F, this.color);
        }
    }

    private void getWord(int code, int difficulty, boolean end){
        if (end){
            switch (code) {
                case 1:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[2];
                            break;
                        case 2:
                            this.line = 1;
                            this.word = TEXT[5];
                            break;
                        case 3:
                            this.line = 1;
                            this.word = TEXT[7];
                            break;
                        case 4:
                            this.line = 1;
                            this.word = TEXT[9];
                            break;
                    }
                    break;
                case 2:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[11];
                            break;
                        case 3:
                            this.line = 1;
                            this.word = TEXT[13];
                            break;
                        case 4:
                            this.line = 1;
                            this.word = TEXT[15];
                            break;
                    }
                    break;
                case 3:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[17];
                            break;
                        case 2:
                            this.line = 1;
                            this.word = TEXT[19];
                            break;
                        case 4:
                            this.line = 2;
                            this.word = TEXT[22];
                            this.word2 = TEXT[23];
                            break;
                    }
                    break;
                case 4:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[25];
                            break;
                        case 2:
                            this.line = 1;
                            this.word = TEXT[27];
                            break;
                        case 3:
                            this.line = 1;
                            this.word = TEXT[30];
                            break;
                    }
                    break;
                case 5:
                    this.line = 1;
                    this.word = TEXT[32];
                    break;
                default:
                    switch (difficulty) {
                        case 1:
                            this.line = 2;
                            this.word = TEXT[35];
                            this.word2 = TEXT[36];
                            break;
                        case 2:
                            this.line = 2;
                            this.word = TEXT[40];
                            this.word2 = TEXT[41];
                            break;
                        case 3:
                            this.line = 2;
                            this.word = TEXT[44];
                            this.word2 = TEXT[45];
                            break;
                        case 4:
                            this.line = 1;
                            this.word = TEXT[48];
                            break;
                    }
                    break;
            }
        }
        else {
            switch (code) {
                case 1:
                    switch (difficulty) {
                        case 1:
                            this.line = 2;
                            this.word = TEXT[0];
                            this.word2 = TEXT[1];
                            break;
                        case 2:
                            this.line = 2;
                            this.word = TEXT[3];
                            this.word2 = TEXT[4];
                            break;
                        case 3:
                            this.line = 1;
                            this.word = TEXT[6];
                            break;
                        case 4:
                            this.line = 1;
                            this.word = TEXT[8];
                            break;
                    }
                    break;
                case 2:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[10];
                            break;
                        case 3:
                            this.line = 1;
                            this.word = TEXT[12];
                            break;
                        case 4:
                            this.line = 1;
                            this.word = TEXT[14];
                            break;
                    }
                    break;
                case 3:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[16];
                            break;
                        case 2:
                            this.line = 1;
                            this.word = TEXT[18];
                            break;
                        case 4:
                            this.line = 2;
                            this.word = TEXT[20];
                            this.word2 = TEXT[21];
                            break;
                    }
                    break;
                case 4:
                    switch (difficulty) {
                        case 1:
                            this.line = 1;
                            this.word = TEXT[24];
                            break;
                        case 2:
                            this.line = 1;
                            this.word = TEXT[26];
                            break;
                        case 3:
                            this.line = 2;
                            this.word = TEXT[28];
                            this.word2 = TEXT[29];
                            break;
                    }
                    break;
                case 5:
                    this.line = 1;
                    this.word = TEXT[31];
                    break;
                default:
                    switch (difficulty) {
                        case 1:
                            this.line = 2;
                            this.word = TEXT[33];
                            this.word2 = TEXT[34];
                            break;
                        case 2:
                            this.line = 3;
                            this.word = TEXT[37];
                            this.word2 = TEXT[38];
                            this.word3 = TEXT[39];
                            break;
                        case 3:
                            this.line = 2;
                            this.word = TEXT[42];
                            this.word2 = TEXT[43];
                            break;
                        case 4:
                            this.line = 2;
                            this.word = TEXT[46];
                            this.word2 = TEXT[47];
                            break;
                    }
                    break;
            }
        }
    }

    public void dispose(){

    }
}
