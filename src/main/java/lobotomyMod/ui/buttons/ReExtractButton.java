package lobotomyMod.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.reward.CogitoReward;

/**
 * @author hoykj
 */
public class ReExtractButton {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float TAKE_Y;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private Color textColor;
    private Color btnColor;
    private boolean isHidden;
    private float controllerImgTextWidth;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;

    public ReExtractButton() {
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.textColor = Color.WHITE.cpy();
        this.btnColor = Color.WHITE.cpy();
        this.isHidden = true;
        this.controllerImgTextWidth = 0.0F;
        this.hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        this.hb.move((float) Settings.WIDTH / 2.0F, TAKE_Y);
    }

    public void update() {
        if (!this.isHidden) {
            this.hb.update();
            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (this.hb.hovered && InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.clicked || CInputActionSet.proceed.isJustPressed()) {
                CInputActionSet.proceed.unpress();
                this.hb.clicked = false;
                AbstractDungeon.cardRewardScreen.closeFromBowlButton();
                AbstractDungeon.closeCurrentScreen();
                this.hide();
                this.onClick();
            }

            if (this.current_x != this.target_x) {
                this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
                if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                    this.current_x = this.target_x;
                    this.hb.move(this.current_x, TAKE_Y);
                }
            }

            this.textColor.a = MathHelper.fadeLerpSnap(this.textColor.a, 1.0F);
            this.btnColor.a = this.textColor.a;
        }
    }

    public void onClick() {
        ((CogitoBucket)AbstractDungeon.player.getRelic(CogitoBucket.ID)).counter -= 20;
        CogitoBucket.reExtract = false;
        CogitoReward reward = new CogitoReward();
        reward.getAbnormality();
    }

    public void hide() {
        if (!this.isHidden) {
            this.isHidden = true;
        }
    }

    public void show() {
        this.isHidden = false;
        this.textColor.a = 0.0F;
        this.btnColor.a = 0.0F;
        this.current_x = HIDE_X;
        this.target_x = SHOW_X;
    }

    public void render(SpriteBatch sb) {
        if (!this.isHidden) {
            this.renderButton(sb);
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], this.current_x, TAKE_Y, this.textColor);
        }
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    private void renderButton(SpriteBatch sb) {
        sb.setColor(this.btnColor);
        sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - 256.0F, TAKE_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - 256.0F, TAKE_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            sb.setBlendFunction(770, 771);
        }

        if (Settings.isControllerMode) {
            if (this.controllerImgTextWidth == 0.0F) {
                this.controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[0], 9999.0F, 0.0F);
            }

            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.proceed.getKeyImg(), this.current_x - 32.0F - this.controllerImgTextWidth / 2.0F - 38.0F * Settings.scale, TAKE_Y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        this.hb.render(sb);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReExtractButton");
        TEXT = uiStrings.TEXT;
        TAKE_Y = 220.0F * Settings.scale;
        SHOW_X = (float)Settings.WIDTH / 2.0F + 165.0F * Settings.scale;
        HIDE_X = (float)Settings.WIDTH / 2.0F;
        HITBOX_W = 260.0F * Settings.scale;
        HITBOX_H = 80.0F * Settings.scale;
    }
}
