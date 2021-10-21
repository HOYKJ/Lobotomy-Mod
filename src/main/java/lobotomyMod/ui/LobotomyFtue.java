package lobotomyMod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.character.Angela;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.reward.CogitoReward;

/**
 * @author hoykj
 */
public class LobotomyFtue extends FtueTip
{
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("LobotomyTutorial");
    public static final String[] MSG = tutorialStrings.TEXT;
    public static final String[] LABEL = tutorialStrings.LABEL;
    private Texture img1;
    private Texture img2;
    private Texture img3;
    private Color screen = Color.valueOf("1c262a00");
    private float x;
    private float x1;
    private float x2;
    private float x3;
    private float targetX;
    private float startX;
    private float scrollTimer = 0.0F;
    private int currentSlot = 0;
    private static String msg1 = MSG[0];
    private static String msg2 = MSG[1];
    private static String msg3 = MSG[2];
    private int closeScreen;
    private int code;
    private CogitoReward cogitoReward;
    private int difCode, difficulty;

    public LobotomyFtue(int code, CogitoReward cogitoReward) {
        this(code);
        this.cogitoReward = cogitoReward;
    }

    public LobotomyFtue(int code, int difCode, int difficulty) {
        this(code);
        this.difCode = difCode;
        this.difficulty = difficulty;
    }

    public LobotomyFtue(int code)
    {
        this.code = code;
        switch (code){
            case 0:
                this.img1 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t1.png");
                this.img2 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t1.png");
                this.img3 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t1.png");
                msg1 = MSG[0];
                msg2 = MSG[1];
                msg3 = "";
                this.closeScreen = -1;
                break;
            case 1:
                this.img1 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t2.png");
                this.img2 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t3.png");
                this.img3 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t4.png");
                msg1 = MSG[3];
                msg2 = MSG[4];
                msg3 = MSG[5];
                this.closeScreen = -2;
                break;
            case 2:
                this.img1 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t5.png");
                this.img2 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t5.png");
                this.img3 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t5.png");
                msg1 = MSG[6];
                this.closeScreen = 0;
                break;
            case 3:
                this.img1 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t6.png");
                this.img2 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t7.png");
                this.img3 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t9.png");
                msg1 = MSG[7];
                msg2 = MSG[8];
                msg3 = MSG[10];
                this.closeScreen = -2;
                break;
            case 4:
                this.img1 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t8.png");
                this.img2 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t8.png");
                this.img3 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t8.png");
                msg1 = MSG[9];
                this.closeScreen = 0;
                break;
            case 5:
                this.img1 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t1.png");
                this.img2 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t1.png");
                this.img3 = ImageMaster.loadImage("lobotomyMod/images/ui/tip/t1.png");
                msg1 = MSG[2];
                this.closeScreen = 0;
                break;
        }

        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.x = 0.0F;
        this.x1 = (567.0F * Settings.scale);
        this.x2 = (this.x1 + Settings.WIDTH);
        this.x3 = (this.x2 + Settings.WIDTH);
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    public void update()
    {
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
        if (this.currentSlot <= this.closeScreen) {
            AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
        }
        if (this.screen.a != 0.8F)
        {
            this.screen.a += Gdx.graphics.getDeltaTime();
            if (this.screen.a > 0.8F) {
                this.screen.a = 0.8F;
            }
        }
        if (((AbstractDungeon.overlayMenu.proceedButton.isHovered) && (InputHelper.justClickedLeft)) ||
                (CInputActionSet.proceed.isJustPressed()))
        {
            CInputActionSet.proceed.unpress();
            if (this.currentSlot <= this.closeScreen)
            {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                if(this.code == 2) {
                    LobotomyUtils.OrdealStart(this.difCode, this.difficulty);
                }
                else if(this.code == 3){
                    LobotomyMod.activeTutorials[3] = false;
                    ((Angela)AbstractDungeon.player).bless();
                }
                else if(this.code == 1){
                    this.cogitoReward.claimReward();
                }
                else if(this.code == 4){
                    LobotomyUtils.hireDepartment();
                }
                return;
            }
            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            this.currentSlot -= 1;
            this.startX = this.x;
            this.targetX = (this.currentSlot * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentSlot <= this.closeScreen) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
            }
        }
        if (this.scrollTimer != 0.0F)
        {
            this.scrollTimer -= Gdx.graphics.getDeltaTime();
            if (this.scrollTimer < 0.0F) {
                this.scrollTimer = 0.0F;
            }
        }
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(this.img1, this.x + this.x1 - 380.0F, Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);

        sb.draw(this.img2, this.x + this.x2 - 380.0F, Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);

        sb.draw(this.img3, this.x + this.x3 - 380.0F, Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);

        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg1, this.x + this.x1 + 400.0F * Settings.scale, Settings.HEIGHT / 2.0F -
                FontHelper.getSmartHeight(FontHelper.panelNameFont, msg1, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg2, this.x + this.x2 + 400.0F * Settings.scale, Settings.HEIGHT / 2.0F -
                FontHelper.getSmartHeight(FontHelper.panelNameFont, msg2, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg3, this.x + this.x3 + 400.0F * Settings.scale, Settings.HEIGHT / 2.0F -
                FontHelper.getSmartHeight(FontHelper.panelNameFont, msg3, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + Integer.toString(Math.abs(this.currentSlot - 1)) + "/" +
                Integer.toString(Math.abs(this.closeScreen - 1)) + LABEL[4], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);

        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }
}
