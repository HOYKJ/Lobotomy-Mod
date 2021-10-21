package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import lobotomyMod.action.common.DelayAction;
import lobotomyMod.action.common.DelayDamageAction;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.sephirah.Geburah;

/**
 * @author hoykj
 */
public class GeburahDaCapo extends AbstractGameEffect {
    private Geburah geburah;
    private DelayDamageAction delay;
    private float x, y, x2, y2;
    private boolean out, attacked, start, mimicry;
    private DamageInfo info;
    private TextureRegion region, region2;

    public GeburahDaCapo(Geburah geburah, DamageInfo info, boolean mimicry){
        this.geburah = geburah;
        this.delay = new DelayDamageAction();
        AbstractDungeon.actionManager.addToBottom(this.delay);
        this.out = false;
        this.attacked = false;
        this.start = false;
        this.info = info;
        this.region = LobotomyImageMaster.GEBURAH_DACAPO;
        this.region2 = LobotomyImageMaster.GEBURAH_MIMICRY;
        this.rotation = -180;
        this.mimicry = mimicry;
    }

    public void update(){
        if(this.geburah.getAttachment("L_Weapon") == null || !this.geburah.getAttachment("L_Weapon").getName().equals("Weapon_Unique_Silent")){
            if(this.start) {
                this.out = true;
            }
        }
        else {
            this.start = true;
        }

        if(!this.out){
            this.x = this.geburah.getSkeleton().getX() + this.geburah.getBone("bone16").getWorldX();
            this.y = this.geburah.getSkeleton().getY() + this.geburah.getBone("bone16").getWorldY();
            if(this.mimicry){
                this.x2 = this.geburah.getSkeleton().getX() + this.geburah.getBone("bone15").getWorldX();
                this.y2 = this.geburah.getSkeleton().getY() + this.geburah.getBone("bone15").getWorldY();
            }
        }
        else {
            this.x -= 2000 * Gdx.graphics.getDeltaTime();
            this.rotation += 720 * Gdx.graphics.getDeltaTime();
            if(this.mimicry){
                this.x2 -= 2000 * Gdx.graphics.getDeltaTime();
            }
        }

        if(!this.attacked && this.out && this.x < AbstractDungeon.player.hb.cX){
            AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false));
            this.delay.damage(this.info);
            this.attacked = true;
        }


        if(this.mimicry){
            if(this.x < -this.region.getRegionWidth() * 0.54F){
                this.isDone = true;
                this.delay.end();
            }
        }
        else {
            if (this.geburah.state.getTracks().get(0).getAnimation().getName().equals("Phase_02_Attack_03_End")) {
                //AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.SLASH_HEAVY, false));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.info, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                this.isDone = true;
                this.delay.end();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.out){
            sb.setColor(Color.WHITE.cpy());
            sb.draw(this.region, this.x - this.region.getRegionWidth() * 0.27F, this.y - this.region.getRegionHeight() * 0.27F, this.region.getRegionWidth() * 0.27F, this.region.getRegionHeight() * 0.27F, this.region.getRegionWidth() * 0.54F, this.region.getRegionHeight() * 0.54F, 1, 1, this.rotation);
            if(this.mimicry){
                sb.draw(this.region2, this.x2 - this.region2.getRegionWidth() * 0.33F, this.y2 - this.region2.getRegionHeight() * 0.33F, this.region2.getRegionWidth() * 0.33F, this.region2.getRegionHeight() * 0.33F, this.region2.getRegionWidth() * 0.66F, this.region2.getRegionHeight() * 0.66F, 1, 1, this.rotation);
            }
        }
    }

    public void dispose(){}
}
