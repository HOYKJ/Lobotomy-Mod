package lobotomyMod.vfx.rabbitTeam.order;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.helper.LobotomyFontHelper;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.friendlyMonster.RabbitTeam;
import lobotomyMod.patch.CursorEnum;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.RabbitCall;
import lobotomyMod.vfx.rabbitTeam.alert.RabbitAlertHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class RabbitOrderScreen extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RabbitTeam");
    public static final String[] TEXT = uiStrings.TEXT;
    private GlyphLayout gl = new GlyphLayout();
    public float up;
    private AbstractGameEffect manual;
    private ArrayList<AbstractGameEffect> box = new ArrayList<>();
    private int code;
    private String word[] = new String[7];
    private float x[] = new float[word.length], y[] = new float[word.length];
    private int selected;
    private Hitbox exit, authorization;
    private boolean close, call;
    public ArrayList<AbstractCreature> killList = new ArrayList<>();
    private Texture callImg;

    public RabbitOrderScreen(){
        this.color = Color.BLACK.cpy();
        this.color.a = 0;
        this.up = Settings.WIDTH;
        this.selected = 0;
        this.code = 0;
        CardCrawlGame.isPopupOpen = true;
        this.callImg = LobotomyImageMaster.RABBIT_TEAM_UI[7];

        this.word[0] = TEXT[5];
        this.word[1] = TEXT[6];
        this.word[2] = this.selected + "/4";
        this.word[3] = TEXT[7];
        this.word[4] = this.selected * 250 + "";
        this.word[5] = TEXT[11];
        this.word[6] = TEXT[10];
        if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            this.gl.setText(LobotomyFontHelper.Rabbit_Manual_34, this.word[0]);
            this.x[0] = Settings.WIDTH * 0.73F;
            this.y[0] = Settings.HEIGHT * 0.8F + this.gl.height / 2.0F;
        }
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_58, this.word[2]);
        this.x[2] = Settings.WIDTH * 0.89F - this.gl.width;
        this.y[2] = Settings.HEIGHT * 0.8F + this.gl.height / 2.0F;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_34, this.word[3]);
        this.x[3] = Settings.WIDTH * 0.73F;
        this.y[3] = Settings.HEIGHT * 0.7F + this.gl.height / 2.0F;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_58, this.word[4]);
        this.x[4] = Settings.WIDTH * 0.89F - this.gl.width;
        this.y[4] = Settings.HEIGHT * 0.7F + this.gl.height / 2.0F;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_32, this.word[5]);
        this.x[5] = Settings.WIDTH * 0.894F - this.gl.width;
        this.y[5] = Settings.HEIGHT * 0.124F + this.gl.height / 2.0F;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_34, this.word[6]);
        this.x[6] = Settings.WIDTH * 0.5F - this.gl.width / 2.0F;
        this.y[6] = Settings.HEIGHT * 0.124F + this.gl.height / 2.0F;

        this.manual = new RabbitOrderManual(this);
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(!monster.isDeadOrEscaped()) {
                if(this.code > 9){
                    break;
                }
                this.box.add(new RabbitOrderBox(this, this.code, monster));
                this.code ++;
            }
        }
        for(int i = this.code; i < 10; i ++){
            this.box.add(new RabbitOrderBox(this, this.code, null));
            this.code ++;
        }

        this.exit = new Hitbox(Settings.WIDTH * 0.12F, Settings.HEIGHT * 0.06F);
        this.exit.translate(Settings.WIDTH * 0.78F, Settings.HEIGHT * 0.1F + this.up);
        this.authorization = new Hitbox(Settings.WIDTH * 0.38F, Settings.HEIGHT * 0.05F);
        this.authorization.translate(Settings.WIDTH * 0.31F, Settings.HEIGHT * 0.1F + this.up);
        this.close = false;
        this.call = false;
    }

    public void update(){
        CardCrawlGame.cursor.changeType(CursorEnum.LOBOTOMY);
        if(this.close){
            if (this.up < Settings.HEIGHT) {
                this.up += (this.up / Settings.WIDTH * 2000 + 400) * Gdx.graphics.getDeltaTime();
                this.color.a = (1 - this.up / Settings.WIDTH) * 0.5F;
            } else {
                this.up = Settings.HEIGHT;
                this.color.a = 0F;
                this.isDone = true;
                CardCrawlGame.isPopupOpen = false;
                if(this.call){
                    AbstractDungeon.topLevelEffectsQueue.add(new RabbitAlertHelper());
                    AbstractMonster monster = new RabbitTeam(-720, 0, this.killList);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(monster, false, 0));
                    AbstractDungeon.actionManager.addToBottom(new LatterAction(monster::createIntent));
                    monster.drawX = AbstractDungeon.player.drawX + AbstractDungeon.player.hb.width + 20;
                    if(AbstractDungeon.player.hasRelic(RabbitCall.ID)){
                        AbstractDungeon.player.getRelic(RabbitCall.ID).counter --;
                    }
                    if(AbstractDungeon.player.hasRelic(CogitoBucket.ID)){
                        AbstractDungeon.player.getRelic(CogitoBucket.ID).counter -= this.selected * 250;
                    }
                }
            }
        }
        else {
            if (this.up > 0) {
                this.up -= (this.up / Settings.WIDTH * 2000 + 400) * Gdx.graphics.getDeltaTime();
                this.color.a = (1 - this.up / Settings.WIDTH) * 0.5F;
            } else {
                this.up = 0;
                this.color.a = 0.5F;
            }
        }

        this.selected = this.killList.size();
        this.word[2] = this.selected + "/4";
        this.word[4] = this.selected * 250 + "";
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_58, this.word[2]);
        this.x[2] = Settings.WIDTH * 0.896F - this.gl.width;
        this.y[2] = Settings.HEIGHT * 0.8F + this.gl.height / 2.0F;
        this.gl.setText(LobotomyFontHelper.Rabbit_Manual_58, this.word[4]);
        this.x[4] = Settings.WIDTH * 0.896F - this.gl.width;
        this.y[4] = Settings.HEIGHT * 0.7F + this.gl.height / 2.0F;

        this.manual.update();
        for (AbstractGameEffect aBox : this.box) {
            aBox.update();
        }

        this.exit.translate(Settings.WIDTH * 0.78F, Settings.HEIGHT * 0.1F + this.up);
        this.exit.update();
        this.authorization.translate(Settings.WIDTH * 0.31F, Settings.HEIGHT * 0.1F + this.up);
        this.authorization.update();
        this.updateInput();
    }

    private void updateInput() {
        if(this.close){
            return;
        }
        if (this.exit.justHovered) {
            CardCrawlGame.sound.play("UI_HOVER");
        }
        if (this.exit.clicked) {
            this.close = true;
        }
        if (InputHelper.justClickedLeft) {
            if (this.exit.hovered)
            {
                this.exit.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                InputHelper.justClickedLeft = false;
                return;
            }
        }

        if(this.selected < 1 || !AbstractDungeon.player.hasRelic(CogitoBucket.ID) || AbstractDungeon.player.getRelic(CogitoBucket.ID).counter < this.selected * 250){
            return;
        }
        if (this.authorization.justHovered) {
            CardCrawlGame.sound.play("UI_HOVER");
            this.callImg = LobotomyImageMaster.RABBIT_TEAM_UI[10];
        }
        if(!this.authorization.hovered){
            this.callImg = LobotomyImageMaster.RABBIT_TEAM_UI[7];
        }
        if (this.authorization.clicked) {
            this.close = true;
            this.call = true;
            this.callImg = LobotomyImageMaster.RABBIT_TEAM_UI[10];
        }
        if (InputHelper.justClickedLeft) {
            if (this.authorization.hovered)
            {
                this.authorization.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.callImg = LobotomyImageMaster.RABBIT_TEAM_UI[11];
                InputHelper.justClickedLeft = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE.cpy());
        float width = Settings.WIDTH * 0.8F / 1172 * 1200, height = Settings.HEIGHT * 0.8F / 872 * 900;
        sb.draw(LobotomyImageMaster.RABBIT_TEAM_UI[5], (Settings.WIDTH - width) / 2.0F, (Settings.HEIGHT - height) / 2.0F + this.up, width, height);
        sb.draw(LobotomyImageMaster.RABBIT_TEAM_UI[0], Settings.WIDTH * 0.1F, Settings.HEIGHT * 0.7F + this.up,
                Settings.WIDTH * 0.4F, Settings.HEIGHT * 0.2F);
        sb.draw(LobotomyImageMaster.RABBIT_TEAM_UI[12], Settings.WIDTH * 0.73F, Settings.HEIGHT * 0.1F + this.up,
                Settings.WIDTH * 0.17F, Settings.HEIGHT * 0.08F);
        width = Settings.WIDTH * 0.38F / 774 * this.callImg.getWidth();
        height = Settings.HEIGHT * 0.05F / 56 * this.callImg.getHeight();
        sb.draw(this.callImg, Settings.WIDTH * 0.50F - width / 2, Settings.HEIGHT * 0.125F - height / 2 + this.up, width, height);

        if(this.selected > 0 && !this.authorization.hovered
                && AbstractDungeon.player.hasRelic(CogitoBucket.ID) && AbstractDungeon.player.getRelic(CogitoBucket.ID).counter >= this.selected * 250) {
            Texture tmp = LobotomyImageMaster.RABBIT_TEAM_UI[9];
            width = Settings.WIDTH * 0.38F / 774 * tmp.getWidth();
            height = Settings.HEIGHT * 0.05F / 56 * tmp.getHeight();
            sb.draw(tmp, Settings.WIDTH * 0.50F - width / 2, Settings.HEIGHT * 0.125F - height / 2 + this.up, width, height);
        }

        for(int i = 0; i < this.word.length; i ++){
            switch (i){
                case 0: case 1: case 3:
                    if((Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) && i == 1) {
                        continue;
                    }
                    FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_34, this.word[i], this.x[i], this.y[i] + this.up, Settings.WIDTH, 0.0F, new Color(1, 0.21F, 0, 1));
                    break;
                case 5:
                    if(this.exit.hovered){
                        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_32, this.word[i], this.x[i], this.y[i] + this.up, Settings.WIDTH, 0.0F, Settings.GOLD_COLOR);
                    }
                    else {
                        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_32, this.word[i], this.x[i], this.y[i] + this.up, Settings.WIDTH, 0.0F, new Color(1, 0.21F, 0, 1));
                    }
                    break;
                case 6:
                    if(this.authorization.hovered && this.selected > 0){
                        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_34, this.word[i], this.x[i], this.y[i] + this.up, Settings.WIDTH, 0.0F, Color.BLACK.cpy());
                    }
                    else {
                        FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_34, this.word[i], this.x[i], this.y[i] + this.up, Settings.WIDTH, 0.0F, new Color(1, 0.21F, 0, 1));
                    }
                    break;
                default:
                    FontHelper.renderSmartText(sb, LobotomyFontHelper.Rabbit_Manual_58, this.word[i], this.x[i], this.y[i] + this.up, Settings.WIDTH, 0.0F, new Color(1, 0.21F, 0, 1));
                    break;
            }
        }

        this.exit.render(sb);
        this.authorization.render(sb);
        this.manual.render(sb);
        for (AbstractGameEffect aBox : this.box) {
            aBox.render(sb);
        }
    }

    public void dispose(){}

    @SpirePatch(
            clz= CardCrawlGame.class,
            method = "render"
    )
    public static class render {
        @SpireInsertPatch(rloc=58)
        public static void Insert(CardCrawlGame _inst) throws NoSuchFieldException, IllegalAccessException {
            if(LobotomyMod.rabbitOrderScreen != null && !LobotomyMod.rabbitOrderScreen.isDone){
                Field sb = SuperclassFinder.getSuperclassField(_inst.getClass(), "sb");
                sb.setAccessible(true);
                LobotomyMod.rabbitOrderScreen.render((SpriteBatch) sb.get(_inst));
                //LobotomyMod.logger.info("test");
            }
        }
    }

    @SpirePatch(
            clz= CardCrawlGame.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=26)
        public static void Insert(CardCrawlGame _inst) {
            if(LobotomyMod.rabbitOrderScreen != null && !LobotomyMod.rabbitOrderScreen.isDone){
                LobotomyMod.rabbitOrderScreen.update();
            }
        }
    }

    @SpirePatch(
            clz= GameCursor.class,
            method="render"
    )
    public static class render2 {
        @SpireInsertPatch(rloc=4)
        public static void Insert(GameCursor _inst, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException {
            if (!Settings.isTouchScreen)
            {
                Field type = _inst.getClass().getDeclaredField("type");
                type.setAccessible(true);

                if(type.get(_inst) == CursorEnum.LOBOTOMY){
                    sb.setColor(Color.WHITE);
                    if (InputHelper.isMouseDown) {
                        sb.draw(LobotomyImageMaster.LOBOTOMY_CURSOR_DOWN, InputHelper.mX - 32.0F + (24.0F * Settings.scale), InputHelper.mY - 32.0F - (24.0F * Settings.scale), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 6.0F, 0, 0, 64, 64, false, false);
                    }
                    else {
                        sb.draw(LobotomyImageMaster.LOBOTOMY_CURSOR, InputHelper.mX - 32.0F + (24.0F * Settings.scale), InputHelper.mY - 32.0F - (24.0F * Settings.scale), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
                    }
                }
            }
        }
    }
}
