package lobotomyMod.vfx.rabbitTeam.order;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyFontHelper;
import lobotomyMod.helper.LobotomyImageMaster;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 * @author hoykj
 */
public class RabbitOrderBox extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RabbitTeam");
    public static final String[] TEXT = uiStrings.TEXT;
    private RabbitOrderScreen screen;
    private AbstractMonster monster;
    private String word[] = new String[3];
    private GlyphLayout gl = new GlyphLayout();
    private float bX, bY;
    private float x, y, x2, y2, x3, y3;
    private Hitbox hb;
    private boolean active;

    public RabbitOrderBox(RabbitOrderScreen screen, int code, AbstractMonster monster){
        this.screen = screen;
        this.active = false;
        switch (code){
            case 0:
                this.bX = Settings.WIDTH * 0.436F;
                this.bY = Settings.HEIGHT * 0.7F;
                break;
            case 1:
                this.bX = Settings.WIDTH * 0.296F;
                this.bY = Settings.HEIGHT * 0.56F;
                break;
            case 2:
                this.bX = Settings.WIDTH * 0.436F;
                this.bY = Settings.HEIGHT * 0.56F;
                break;
            case 3:
                this.bX = Settings.WIDTH * 0.58F;
                this.bY = Settings.HEIGHT * 0.56F;
                break;
            case 4:
                this.bX = Settings.WIDTH * 0.296F;
                this.bY = Settings.HEIGHT * 0.42F;
                break;
            case 5:
                this.bX = Settings.WIDTH * 0.436F;
                this.bY = Settings.HEIGHT * 0.42F;
                break;
            case 6:
                this.bX = Settings.WIDTH * 0.58F;
                this.bY = Settings.HEIGHT * 0.42F;
                break;
            case 7:
                this.bX = Settings.WIDTH * 0.296F;
                this.bY = Settings.HEIGHT * 0.28F;
                break;
            case 8:
                this.bX = Settings.WIDTH * 0.436F;
                this.bY = Settings.HEIGHT * 0.28F;
                break;
            case 9:
                this.bX = Settings.WIDTH * 0.58F;
                this.bY = Settings.HEIGHT * 0.28F;
                break;
        }
        this.monster = monster;

        this.color = new Color(1, 0.21F, 0, 1F);
        if(monster == null){
            this.word[0] = TEXT[12];
            this.gl.setText(LobotomyFontHelper.Rabbit_Manual_28, this.word[0]);
            this.x = this.bX + Settings.WIDTH * 0.062F - this.gl.width / 2.0F;
            this.y = this.bY + Settings.HEIGHT * 0.1F + this.gl.height / 2.0F;
            this.word[1] = null;
            //this.color = Color.BLACK.cpy();
            return;
        }
        this.word[0] = monster.name;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_28, this.word[0]);
        this.x = this.bX + Settings.WIDTH * 0.062F - this.gl.width / 2.0F;
        this.y = this.bY + Settings.HEIGHT * 0.09F + this.gl.height / 2.0F;

        this.word[1] = TEXT[8] + monster.currentHealth;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_30, this.word[1]);
        this.x2 = this.bX + Settings.WIDTH * 0.062F - this.gl.width / 2.0F;
        this.y2 = this.bY + Settings.HEIGHT * 0.03F + this.gl.height / 2.0F;

        this.word[2] = TEXT[9];
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_28, this.word[2]);
        this.x3 = this.bX + Settings.WIDTH * 0.062F - this.gl.width / 2.0F;
        this.y3 = this.bY + Settings.HEIGHT * 0.06F + this.gl.height / 2.0F;

        this.hb = new Hitbox(Settings.WIDTH * 0.124F, Settings.HEIGHT * 0.12F);
        this.hb.translate(this.bX, this.bY + this.screen.up);
    }

    public void update(){
        if(this.monster == null){
            return;
        }
        this.hb.translate(this.bX, this.bY + this.screen.up);
        this.hb.update();

        if (this.hb.justHovered) {
            CardCrawlGame.sound.play("UI_HOVER");
        }
        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.active = !this.active;
            if(this.active) {
                if(this.screen.killList.size() >= 4){
                    this.active = false;
                    return;
                }
                this.screen.killList.add(this.monster);
            }
            else {
                this.screen.killList.remove(this.monster);
            }
        }
        if (InputHelper.justClickedLeft) {
            if (this.hb.hovered)
            {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                InputHelper.justClickedLeft = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        float width = Settings.WIDTH * 0.124F / 247 * 270, height = Settings.HEIGHT * 0.12F / 130 * 154;

        if(this.active){
            sb.draw(LobotomyImageMaster.RABBIT_TEAM_UI[4], this.bX + Settings.WIDTH * 0.062F - width / 2, this.bY + Settings.HEIGHT * 0.06F - height / 2 + this.screen.up, width, height);
            sb.setColor(this.color);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.bX, this.bY + Settings.HEIGHT * 0.04F + this.screen.up, Settings.WIDTH * 0.124F, Settings.HEIGHT * 0.04F);
            FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_28, this.word[2], this.x3, this.y3 + this.screen.up, Settings.WIDTH, 0.0F, Color.WHITE.cpy());
        }
        else {
            sb.draw(this.monster != null ? LobotomyImageMaster.RABBIT_TEAM_UI[3] : LobotomyImageMaster.RABBIT_TEAM_UI[13], this.bX + Settings.WIDTH * 0.062F - width / 2, this.bY + Settings.HEIGHT * 0.06F - height / 2 + this.screen.up, width, height);
        }

        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_28, this.word[0], this.x, this.y + this.screen.up, Settings.WIDTH, 0.0F, Settings.GOLD_COLOR.cpy());
        if(this.word[1] == null){
            return;
        }
        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_30, this.word[1], this.x2, this.y2 + this.screen.up, Settings.WIDTH, 0.0F, Settings.RED_TEXT_COLOR.cpy());
    }

    public void dispose(){

    }
}
