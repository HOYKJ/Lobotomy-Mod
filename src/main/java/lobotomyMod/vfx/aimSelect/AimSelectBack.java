package lobotomyMod.vfx.aimSelect;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.department.Chesed;
import lobotomyMod.card.angelaCard.department.Geburah;
import lobotomyMod.card.angelaCard.department.Tiphereth;
import lobotomyMod.character.Angela;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class AimSelectBack extends AbstractGameEffect {
    public float inside;
    private boolean close;
    private ArrayList<AimSelectBox> box = new ArrayList<>();
    private Color color2;
    private Hitbox hide;

    public AimSelectBack(){
        this.color = new Color(0.95F, 0.58F, 0.28F, 1);
        this.color2 = new Color(0.12F, 0.90F, 0.68F, 1);
        this.inside = 0;
        for(int i = 1; i < 9; i ++) {
            this.box.add(new AimSelectBox(this, i));
        }
        this.hide = new Hitbox(Settings.WIDTH * 0.028F + 12 * Settings.scale, Settings.HEIGHT * 0.08F);
        this.hide.translate(Settings.WIDTH * 0.06F - this.inside - 12 * Settings.scale, Settings.HEIGHT * 0.46F);
    }

    public void update(){
        if(this.close){
            if(this.inside < Settings.WIDTH * 0.06F){
                this.inside += 1000 * Settings.scale * Gdx.graphics.getDeltaTime();
            }
            else {
                this.inside = Settings.WIDTH * 0.06F;
            }
            if(this.hide.hovered && this.inside >= Settings.WIDTH * 0.06F - 12 * Settings.scale){
                this.inside = Settings.WIDTH * 0.06F - 12 * Settings.scale;
            }
        }
        else {
            if(this.inside > 0){
                this.inside -= 1000 * Settings.scale * Gdx.graphics.getDeltaTime();
            }
            else {
                this.inside = 0;
            }
        }
        for (AimSelectBox aBox : this.box) {
            aBox.update();
        }
//        LobotomyMod.logger.info("test-----" + Chesed.departmentCode[0] + ((Angela) AbstractDungeon.player).departments[Chesed.departmentCode[0]] +
//                ((Angela) AbstractDungeon.player).departments[Tiphereth.departmentCode[0]] +((Angela) AbstractDungeon.player).departments[Geburah.departmentCode[0]]);
        this.hide.translate(Settings.WIDTH * 0.06F - this.inside - 16 * Settings.scale, Settings.HEIGHT * 0.46F);
        this.hide.update();
        this.updateInput();
    }

    private void updateInput() {
        if (this.hide.justHovered) {
            CardCrawlGame.sound.play("UI_HOVER");
        }
        if (this.hide.clicked) {
            this.close = !this.close;
            this.hide.clicked = false;
        }
        if (InputHelper.justClickedLeft) {
            if (this.hide.hovered)
            {
                this.hide.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                InputHelper.justClickedLeft = false;
                return;
            }
        }
    }

    public void clearSelect(){
        for (AimSelectBox aBox : this.box) {
            aBox.clear();
        }
        ((Angela)AbstractDungeon.player).changeAim(Angela.AimType.NORMAL);
    }

    @Override
    public void render(SpriteBatch sb) {
        float width = Settings.WIDTH * 0.06F;
        sb.setColor(this.color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F - this.inside, Settings.HEIGHT * 0.15F - 3 * Settings.scale, width + 3 * Settings.scale,  Settings.HEIGHT * 0.7F+ 6 * Settings.scale);
        sb.setColor(Color.BLACK.cpy());
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F - this.inside, Settings.HEIGHT * 0.15F, width, Settings.HEIGHT * 0.7F);

        sb.setColor(this.color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, width - this.inside, Settings.HEIGHT * 0.46F - 3 * Settings.scale, Settings.WIDTH * 0.028F + 3 * Settings.scale, Settings.HEIGHT * 0.08F + 6 * Settings.scale);
        sb.setColor(Color.BLACK.cpy());
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, width - this.inside, Settings.HEIGHT * 0.46F, Settings.WIDTH * 0.028F, Settings.HEIGHT * 0.08F);

        for (AimSelectBox aBox : this.box) {
            aBox.render(sb);
        }

        Texture img;
        if(this.hide.hovered){
            sb.setColor(this.color2);
            img = LobotomyImageMaster.AIM_SELECT_UI[11];
        }
        else {
            sb.setColor(this.color);
            img = LobotomyImageMaster.AIM_SELECT_UI[10];
        }
        sb.draw(img, width + Settings.WIDTH * 0.014F - Settings.HEIGHT * 0.025F - this.inside, Settings.HEIGHT * 0.475F,
                Settings.HEIGHT * 0.05F, Settings.HEIGHT * 0.05F, 0, 0, img.getWidth(), img.getHeight(), this.close, false);
        this.hide.render(sb);
    }

    public void dispose(){}

    @SpirePatch(
            clz= CardCrawlGame.class,
            method = "render"
    )
    public static class render {
        @SpireInsertPatch(rloc=58)
        public static void Insert(CardCrawlGame _inst) throws NoSuchFieldException, IllegalAccessException {
            if(AbstractDungeon.player instanceof Angela){
                if(((Angela) AbstractDungeon.player).aimSelectBack == null){
                    return;
                }
                if(AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
                    return;
                }
                Field sb = SuperclassFinder.getSuperclassField(_inst.getClass(), "sb");
                sb.setAccessible(true);
                ((Angela) AbstractDungeon.player).aimSelectBack.render((SpriteBatch) sb.get(_inst));
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
            if(AbstractDungeon.player instanceof Angela){
                if(((Angela) AbstractDungeon.player).aimSelectBack == null){
                    return;
                }
                if(AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
                    return;
                }
                ((Angela) AbstractDungeon.player).aimSelectBack.update();
            }
        }
    }
}
