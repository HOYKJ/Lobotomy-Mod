package lobotomyMod.action.animation;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import lobotomyMod.monster.Ordeal.Claw;

/**
 * @author hoykj
 */
public class ultiAttackAction extends AbstractGameAction {
    private Claw m;
    private DamageInfo info;
    private int attack;
    private boolean delay, end, attacked, changeFlip;
    private float timer;

    public ultiAttackAction(Claw m, AbstractCreature target, DamageInfo info, AttackEffect effect){
        this.m = m;
        this.info = info;
        this.setValues(target, info);
        this.attackEffect = effect;
        this.attack = 1;
        this.delay = false;
        this.end = false;
        this.attacked = false;
        this.changeFlip = false;
        this.timer = 0.0F;
    }

    public void update(){
        this.timer -= Gdx.graphics.getDeltaTime();
        if(this.timer >= 0){
            return;
        }
        else {
            if(this.changeFlip){
                this.m.flipHorizontal = !this.m.flipHorizontal;
                this.changeFlip = false;
            }
        }

        if(this.attack == 2) {
            this.m.animX += 3000.0F * Settings.scale * Gdx.graphics.getDeltaTime();

            if(this.m.drawX + this.m.animX >= this.target.hb.cX){
                if(!this.attacked) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                    this.target.damage(this.info);
                    this.attacked = true;
                }
                if(this.delay){
                    this.duration -= Gdx.graphics.getDeltaTime();
                    if(this.duration <= 0){
                        this.m.changeState("ULTI_ATTACK2");
                        this.timer = 1.0F;
                        this.attack  ++;
                        this.delay = false;
                        this.attacked = false;
                        this.changeFlip = true;
                    }
                }
                else {
                    this.delay = true;
                    this.duration = 0.08F;
                }
            }
        }
        else {
            this.m.animX -= 3000.0F * Settings.scale * Gdx.graphics.getDeltaTime();

            if(this.attack >= 3){
                if(this.m.drawX + this.m.animX <= 0){
                    this.end = true;
                    this.m.animX = Settings.WIDTH - this.m.drawX;
                }

                if(this.end && this.m.animX <= 0){
                    this.m.animX = 0;
                    this.m.changeState("ULTI_END");
                    this.isDone = true;
                }
            }

            if(this.m.drawX + this.m.animX <= this.target.hb.cX){
                if(!this.attacked) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                    this.target.damage(this.info);
                    this.attacked = true;
                }
                if(this.attack >= 3){
                    return;
                }
                if(this.delay){
                    this.duration -= Gdx.graphics.getDeltaTime();
                    if(this.duration <= 0){
                        this.m.changeState("ULTI_ATTACK1");
                        this.timer = 1.0F;
                        this.attack  ++;
                        this.delay = false;
                        this.attacked = false;
                        this.changeFlip = true;
                    }
                }
                else {
                    this.delay = true;
                    this.duration = 0.08F;
                }
            }
        }

    }
}
