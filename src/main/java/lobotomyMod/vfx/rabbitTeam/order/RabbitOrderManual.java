package lobotomyMod.vfx.rabbitTeam.order;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyFontHelper;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author hoykj
 */
public class RabbitOrderManual extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RabbitTeam");
    public static final String[] TEXT = uiStrings.TEXT;
    private RabbitOrderScreen screen;
    private String word;
    private ArrayList<DialogWord> words = new ArrayList<>();
    private int curLine = 0;
    private float curLineWidth = 0.0F;
    private Scanner s;
    private GlyphLayout gl = new GlyphLayout();
    private static final float DIALOG_MSG_X = Settings.WIDTH * 0.11F;
    private static final float DIALOG_MSG_Y = Settings.HEIGHT * 0.58F;
    private static final float DIALOG_MSG_W = Settings.WIDTH * 0.16F;
    private static final float CHAR_SPACING = 8.0F * Settings.scale;
    private static final float LINE_SPACING = 30.0F * Settings.scale;
    private boolean textDone = false;
    private float x, y;

    public RabbitOrderManual(RabbitOrderScreen screen){
        this.screen = screen;
        this.word = TEXT[3];
        this.s = new Scanner(TEXT[4]);
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_30, this.word);
        this.x = Settings.WIDTH * 0.19F - this.gl.width / 2.0F;
        this.y = Settings.HEIGHT * 0.68F + this.gl.height / 2.0F;
    }

    public void update(){
        if (Settings.lineBreakViaCharacter) {
            bodyTextEffectCN();
        } else {
            bodyTextEffect();
        }
        for (DialogWord w : this.words) {
            w.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_30, this.word, this.x, this.y + this.screen.up, Settings.WIDTH, 0.0F, Settings.GOLD_COLOR.cpy());
        for (DialogWord w : this.words) {
            w.render(sb, this.screen.up);
        }
    }

    private void bodyTextEffectCN() {
        if(this.textDone){
            return;
        }
        if (this.s.hasNext()) {
            String word = this.s.next();
            if (word.equals("NL")) {
                this.curLine += 1;
                this.curLineWidth = 0.0F;
                return;
            }
            DialogWord.WordColor color = DialogWord.identifyWordColor(word);
            //DialogWord.WordColor color = DialogWord.WordColor.GOLD;
            if (color != DialogWord.WordColor.DEFAULT) {
                word = word.substring(2);
            }
            DialogWord.WordEffect effect = DialogWord.identifyWordEffect(word);
            if (effect != DialogWord.WordEffect.NONE) {
                word = word.substring(1, word.length() - 1);
            }
            for (int i = 0; i < word.length(); i++) {
                String tmp = Character.toString(word.charAt(i));

                this.gl.setText(LobotomyFontHelper.Rabbit_Manual_24, tmp);
                if (this.curLineWidth + this.gl.width > DIALOG_MSG_W) {
                    this.curLine += 1;
                    this.curLineWidth = this.gl.width;
                } else {
                    this.curLineWidth += this.gl.width;
                }
                this.words.add(new DialogWord(LobotomyFontHelper.Rabbit_Manual_24, tmp, DialogWord.AppearEffect.NONE, effect, color, DIALOG_MSG_X + this.curLineWidth - this.gl.width, DIALOG_MSG_Y - LINE_SPACING * this.curLine, this.curLine));
            }
        } else {
            this.textDone = true;
            this.s.close();
        }

    }

    private void bodyTextEffect() {
        if(this.textDone){
            return;
        }
        if (this.s.hasNext()) {
            String word = this.s.next();
            if (word.equals("NL")) {
                this.curLine += 1;
                this.curLineWidth = 0.0F;
                return;
            }
            DialogWord.WordColor color = DialogWord.identifyWordColor(word);
            //DialogWord.WordColor color = DialogWord.WordColor.GOLD;
            if (color != DialogWord.WordColor.DEFAULT) {
                word = word.substring(2);
            }
            DialogWord.WordEffect effect = DialogWord.identifyWordEffect(word);
            if (effect != DialogWord.WordEffect.NONE) {
                word = word.substring(1, word.length() - 1);
            }
            this.gl.setText(FontHelper.charDescFont, word);
            if (this.curLineWidth + this.gl.width > DIALOG_MSG_W) {
                this.curLine += 1;
                this.curLineWidth = (this.gl.width + CHAR_SPACING);
            } else {
                this.curLineWidth += this.gl.width + CHAR_SPACING;
            }
            this.words.add(new DialogWord(FontHelper.charDescFont, word, DialogWord.AppearEffect.NONE, effect, color, DIALOG_MSG_X + this.curLineWidth - this.gl.width, DIALOG_MSG_Y - LINE_SPACING * this.curLine, this.curLine));
        } else {
            this.textDone = true;
            this.s.close();
        }
    }

    public void dispose(){

    }
}
