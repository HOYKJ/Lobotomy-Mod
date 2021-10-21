package lobotomyMod.action.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.sephirah.Geburah;

/**
 * @author hoykj
 */
public class GeburahDash extends AbstractGameAction {
    private Geburah geburah;
    private boolean end, attacked, attacked2;
    private DamageInfo info;
    private int stage;

    public GeburahDash(Geburah geburah, int stage, DamageInfo info){
        this.geburah = geburah;
        this.stage = stage;
        this.info = info;
        this.end = false;
        this.attacked = false;
        this.attacked2 = false;
        this.target = AbstractDungeon.player;
    }

    public void update(){
        if(this.stage < 4) {
            if (this.geburah.state.getTracks().get(0).getAnimation().getName().equals("Phase_All_Teleport_02")) {
                this.geburah.animX -= 2000.0F * Settings.scale * Gdx.graphics.getDeltaTime();
            }

            if (!this.attacked && this.geburah.drawX + this.geburah.animX < this.target.drawX) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY, false));
                this.target.damage(this.info);
                this.attacked = true;
            }
        }
        else {
            if (this.geburah.state.getTracks().get(0).getAnimation().getName().equals("Phase_04_Attack_Run") ||
                    this.geburah.state.getTracks().get(0).getAnimation().getName().equals("Phase_04_Attack_Run_Attack")) {
                this.geburah.animX -= 2000.0F * Settings.scale * Gdx.graphics.getDeltaTime();
            }

            if (!this.attacked2 && this.geburah.drawX + this.geburah.animX < this.target.drawX + 400) {
                this.geburah.changeState("RUN_ATTACK");
                this.attacked2 = true;
            }

            if (!this.attacked && this.geburah.drawX + this.geburah.animX < this.target.drawX) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL, false));
                this.target.damage(this.info);
                this.attacked = true;
            }
        }

        if (this.geburah.drawX + this.geburah.animX <= 0) {
            this.end = true;
            this.geburah.animX = Settings.WIDTH - this.geburah.drawX;
        }

        if(this.end && this.geburah.animX <= 0){
            this.geburah.animX = 0;
            if(this.stage == 4){
                this.geburah.changeState("RUN_END");
            }
            else {
                this.geburah.changeState("ATTACK_TELEPORT_END" + this.stage);
            }
            this.isDone = true;
        }
    }
}
