package lobotomyMod.action.animation;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.Claw;

/**
 * @author hoykj
 */
public class ClawRedAttackAction extends AbstractGameAction {
    private Claw m;
    private AbstractGameAction action;
    private boolean end;

    public ClawRedAttackAction(Claw m, AbstractGameAction action){
        this.m = m;
        this.action = action;
        this.end = false;
    }

    public void update(){
        this.m.animX -= 3000.0F * Settings.scale * Gdx.graphics.getDeltaTime();
        if(this.m.drawX + this.m.animX <= 0){
            this.end = true;
            this.m.animX = Settings.WIDTH - this.m.drawX;
        }

        if(this.end && this.m.animX <= 0){
            this.m.animX = 0;
            this.m.changeState("RED_END");
            AbstractDungeon.actionManager.addToTop(new LatterAction(()->{
                AbstractDungeon.effectList.add(new ClashEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
                //AbstractDungeon.actionManager.addToTop(new VFXAction(new ClashEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToTop(this.action);
            }, 1.9F));
            this.isDone = true;
        }
    }
}
