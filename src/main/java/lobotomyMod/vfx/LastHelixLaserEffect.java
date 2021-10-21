package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.Set;

/**
 * @author hoykj
 */
public class LastHelixLaserEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private static TextureAtlas.AtlasRegion img;
    private Bone bone;
    private AbstractMonster monster;
    private boolean started;

    public LastHelixLaserEffect(Skeleton skeleton, Bone bone, AbstractMonster monster)
    {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }
        this.monster = monster;
        this.bone = bone;

        this.sX = skeleton.getX() + this.bone.getWorldX();
        this.sY = skeleton.getY() + this.bone.getWorldY();

        this.color = new Color(107 / 255.0F, 1, 173 / 255.0F, 0.9F);

        this.duration = Settings.FAST_MODE? 0.09F: 0.15F;
        this.started = false;
    }

    public void update()
    {
        this.rotation = this.bone.getRotation();

        while(this.rotation < -360){
            this.rotation += 360;
        }

        if(this.rotation <= -358){
            if(this.started) {
                AbstractDungeon.effectsQueue.add(new LatterEffect(() -> {
                    this.monster.changeState("RESTART");
                }, Settings.FAST_MODE ? 4 : 6));
                this.isDone = true;
            }
        }
        else {
            this.started = true;
        }

        float r1 = MathUtils.atan2(this.sX - AbstractDungeon.player.hb.x - AbstractDungeon.player.hb.width, this.sY - AbstractDungeon.player.hb.y) * 57.295776F + 180;
        float r2 = MathUtils.atan2(this.sX - AbstractDungeon.player.hb.x - AbstractDungeon.player.hb.width, this.sY - AbstractDungeon.player.hb.y - AbstractDungeon.player.hb.height) * 57.295776F + 176;
        //LobotomyMod.logger.info("----------r1: " + r1 + "  r2: " + r2);
        if(-this.rotation > r1 && -this.rotation < r2){
            this.duration -= Gdx.graphics.getDeltaTime();
            if(this.duration <= 0){
                this.duration = Settings.FAST_MODE? 0.3F: 0.5F;
//                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this.monster, 10, DamageInfo.DamageType.THORNS),
//                        AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToTop(new LatterAction(()->{
                    AbstractDungeon.player.damage(new DamageInfo(this.monster, 8, DamageInfo.DamageType.THORNS));
                }));
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.sX + 10 * Settings.scale * (float)Math.cos(Math.toRadians(Math.abs(this.rotation))),
                this.sY - img.packedHeight / 2.0F - 10 * Settings.scale * (float)Math.sin(Math.toRadians(Math.abs(this.rotation))), 0.0F,
                img.packedHeight / 2.0F, Settings.WIDTH * 1.2F, 120.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, this.rotation + 90);
        LobotomyMod.logger.info("----------rotation: " + this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}
