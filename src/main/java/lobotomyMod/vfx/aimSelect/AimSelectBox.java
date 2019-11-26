package lobotomyMod.vfx.aimSelect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.card.angelaCard.department.Chesed;
import lobotomyMod.card.angelaCard.department.Geburah;
import lobotomyMod.card.angelaCard.department.Tiphereth;
import lobotomyMod.character.Angela;
import lobotomyMod.helper.LobotomyImageMaster;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class AimSelectBox extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AimSelect");
    public static final String[] TEXT = uiStrings.TEXT;
    private AimSelectBack screen;
    private float x, y;
    private int code;
    private Hitbox hb;
    private Color color2, color3;
    private boolean lock, active;
    private ArrayList<PowerTip> tips = new ArrayList<>();

    public AimSelectBox(AimSelectBack screen, int code){
        this.screen = screen;
        this.code = code;
        this.x = Settings.WIDTH * 0.008F;
        this.color = new Color(0.95F, 0.58F, 0.28F, 1);
        this.color3 = new Color(0.12F, 0.90F, 0.68F, 1);
        switch (this.code){
            case 1:
                this.y = Settings.HEIGHT * 0.604F;
                this.color2 = new Color(0.17F, 0.87F, 0.36F, 1);
                break;
            case 2:
                this.y = Settings.HEIGHT * 0.520F;
                this.color2 = new Color(0.22F, 0.62F, 0.84F, 1);
                break;
            case 3:
                this.y = Settings.HEIGHT * 0.436F;
                this.color2 = new Color(0.79F, 0.15F, 0.25F, 1);
                break;
            case 4:
                this.y = Settings.HEIGHT * 0.352F;
                this.color2 = new Color(0.93F, 0.91F, 0.76F, 1);
                break;
            case 5:
                this.y = Settings.HEIGHT * 0.268F;
                this.color2 = new Color(0.61F, 0.37F, 0.64F, 1);
                break;
            case 6:
                this.y = Settings.HEIGHT * 0.184F;
                this.color2 = new Color(0.27F, 0.78F, 0.72F, 1);
                break;
            case 7:
                this.y = Settings.HEIGHT * 0.100F;
                this.color2 = new Color(0.97F, 0.85F, 0.18F, 1);
                break;
            case 8:
                this.y = Settings.HEIGHT * 0.016F;
                this.color2 = new Color(0.98F, 0.09F, 0.00F, 1);
                break;
        }
        this.y += Settings.HEIGHT * 0.15F;
//        Texture img = LobotomyImageMaster.AIM_SELECT_UI[this.code];
//        this.hb = new Hitbox(img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.08F);
        //this.hb.translate(this.x - this.screen.inside, this.y);
    }

    public void update(){
        switch (this.code){
            case 1:
                this.lock = Angela.departments[Chesed.departmentCode[0]] < 2;
                break;
            case 2:
                this.lock = Angela.departments[Chesed.departmentCode[0]] < 3;
                break;
            case 3:
                this.lock = Angela.departments[Tiphereth.departmentCode[0]] < 2;
                break;
            case 4:
                this.lock = Angela.departments[Tiphereth.departmentCode[0]] < 3;
                break;
            case 5:
                this.lock = Angela.departments[Tiphereth.departmentCode[0]] < 4;
                break;
            case 6:
                this.lock = Angela.departments[Tiphereth.departmentCode[0]] < 5;
                break;
            case 7:
                this.lock = Angela.departments[Geburah.departmentCode[0]] < 2;
                break;
            case 8:
                this.lock = Angela.departments[Geburah.departmentCode[0]] < 3;
                break;
        }
        if(this.hb == null){
            Texture img = LobotomyImageMaster.AIM_SELECT_UI[this.code];
            this.hb = new Hitbox(img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.08F);
        }
        this.hb.translate(this.x - this.screen.inside, this.y);
        this.hb.update();

        if(this.lock){
            this.tips.clear();
            if (this.hb.hovered) {
                this.tips.add(new PowerTip(TEXT[16], TEXT[17]));
            }
            return;
        }

        if (this.hb.justHovered) {
            CardCrawlGame.sound.play("UI_HOVER");
        }
        if (this.hb.clicked) {
            this.hb.clicked = false;
            if(!this.active) {
                this.screen.clearSelect();
                this.active = true;
                switch (this.code){
                    case 1:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.HP);
                        break;
                    case 2:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.SP);
                        break;
                    case 3:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.RED);
                        break;
                    case 4:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.WHITE);
                        break;
                    case 5:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.BLACK);
                        break;
                    case 6:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.PALE);
                        break;
                    case 7:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.SLOW);
                        break;
                    case 8:
                        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.EXECUTE);
                        break;
                }
            }
            else {
                this.screen.clearSelect();
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

        this.tips.clear();
        if (this.hb.hovered) {
            this.tips.add(new PowerTip(TEXT[this.code * 2 - 2], TEXT[this.code * 2 - 1]));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.lock){
            sb.setColor(this.color);
            Texture img = LobotomyImageMaster.AIM_SELECT_UI[0];
            sb.draw(img, this.x - this.screen.inside, this.y, img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.08F);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, this.x - this.screen.inside, this.y, img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.07F);
            img = LobotomyImageMaster.AIM_SELECT_UI[9];
            sb.draw(img, this.x - this.screen.inside, this.y, img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.08F);

            if (!this.tips.isEmpty()) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + 20.0F * Settings.scale,
                        this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            }
            return;
        }

        if(this.active){
            sb.setColor(this.color3);
        }
        else {
            sb.setColor(this.color2);
        }
        Texture img = LobotomyImageMaster.AIM_SELECT_UI[this.code];
        sb.draw(img, this.x - this.screen.inside, this.y, img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.08F);
        if(this.active){
            sb.setColor(this.color3);
        }
        else {
            sb.setColor(this.color);
        }
        img = LobotomyImageMaster.AIM_SELECT_UI[0];
        sb.draw(img, this.x - this.screen.inside, this.y, img.getWidth() * Settings.HEIGHT * 0.08F / img.getHeight(), Settings.HEIGHT * 0.08F);

        if (!this.tips.isEmpty()) {
            TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + 20.0F * Settings.scale,
                    this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
        }
    }

    public void clear(){
        this.active = false;
    }

    public void dispose(){

    }
}
