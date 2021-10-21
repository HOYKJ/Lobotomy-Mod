package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import lobotomyMod.action.animation.GeburahDash;
import lobotomyMod.action.common.DelayAction;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.sephirah.Geburah;

/**
 * @author hoykj
 */
public class GeburahGreed extends AbstractGameEffect {
    private Geburah geburah;
    private DelayAction delay;
    private float x, y;
    private boolean out, attacked, start;
    private DamageInfo info;
    private TextureRegion region;

    public GeburahGreed(Geburah geburah, DamageInfo info){
        this.geburah = geburah;
        this.delay = new DelayAction();
        AbstractDungeon.actionManager.addToBottom(this.delay);
        this.out = false;
        this.attacked = false;
        this.start = false;
        this.info = info;
        this.region = LobotomyImageMaster.GEBURAH_GREED;
    }

    public void update(){
        if(this.geburah.getAttachment("Weapon_Unique_Greed") == null){
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
            this.x -= 2000 * Gdx.graphics.getDeltaTime();
        }

        if(!this.attacked && this.out && this.x < AbstractDungeon.player.hb.cX){
            //AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
            AbstractDungeon.actionManager.addToTop(new GeburahDash(this.geburah, 4, info));
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.delay.end();
            this.attacked = true;
        }

        if(this.x < -this.region.getRegionWidth() * 0.36F){
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.out){
            sb.setColor(Color.WHITE.cpy());
            sb.draw(this.region, this.x - this.region.getRegionWidth() * 0.18F, this.y - this.region.getRegionHeight() * 0.18F, this.region.getRegionWidth() * 0.18F, this.region.getRegionHeight() * 0.36F, this.region.getRegionWidth() * 0.36F, this.region.getRegionHeight() * 0.36F, 1, 1, -90);
        }
    }

    public void dispose(){}
}
