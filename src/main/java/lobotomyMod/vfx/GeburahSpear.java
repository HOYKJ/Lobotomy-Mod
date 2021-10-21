package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
public class GeburahSpear extends AbstractGameEffect {
    private Geburah geburah;
    private DelayDamageAction delay;
    private float x, y;
    private boolean out, attacked, start;
    private DamageInfo info;
    private TextureRegion region;

    public GeburahSpear(Geburah geburah, DamageInfo info){
        this.geburah = geburah;
        this.delay = new DelayDamageAction();
        AbstractDungeon.actionManager.addToBottom(this.delay);
        this.out = false;
        this.attacked = false;
        this.start = false;
        this.info = info;
        this.region = LobotomyImageMaster.GEBURAH_SPEAR;
    }

    public void update(){
        if(this.geburah.getAttachment("R_Weapon") == null || !this.geburah.getAttachment("R_Weapon").getName().equals("spear")){
            if(this.start) {
                this.out = true;
            }
        }
        else {
            this.start = true;
        }

        if(!this.out){
            this.x = this.geburah.getSkeleton().getX() + this.geburah.getBone("bone15").getWorldX();
            this.y = this.geburah.getSkeleton().getY() + this.geburah.getBone("bone15").getWorldY();
        }
        else {
            this.x -= 3000 * Gdx.graphics.getDeltaTime();
        }

        if(!this.attacked && this.out && this.x < AbstractDungeon.player.hb.cX){
            AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false));
            this.delay.damage(this.info);
            this.attacked = true;
        }

        if(this.x < -this.region.getRegionWidth() * 1.16F){
            this.isDone = true;
            this.delay.end();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.out){
            sb.setColor(Color.WHITE.cpy());
            sb.draw(this.region, this.x - this.region.getRegionWidth() * 0.58F, this.y - this.region.getRegionHeight() * 0.58F, this.region.getRegionWidth() * 1.16F, this.region.getRegionHeight() * 1.16F);
        }
    }

    public void dispose(){}
}
