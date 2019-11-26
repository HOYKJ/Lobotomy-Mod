package lobotomyMod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;

/**
 * @author hoykj
 */
public class WhiteFixerFogCore extends AbstractGameEffect {
    private Bone b1, b2;
    private Skeleton skeleton;
    private boolean one;

    public WhiteFixerFogCore(Skeleton skeleton, Bone b1, Bone b2, boolean one){
        this.skeleton = skeleton;
        this.b1 = b1;
        this.b2 = b2;
        this.one = one;
    }

    public void update(){
        float sX = this.skeleton.getX() + this.b1.getWorldX();
        float sY = this.skeleton.getY() + this.b1.getWorldY();
        float dX = this.skeleton.getX() + this.b2.getWorldX();
        float dY = this.skeleton.getY() + this.b2.getWorldY();
        LobotomyMod.logger.info(sX + "    " + sY + "    " + dX + "    " + dY);

        this.rotation = MathUtils.atan2(sY - dY, sX - dX);
        //this.rotation += 0.5 * MathUtils.PI;
//        this.rotation *= 57.295776F;
//        this.rotation = this.rotation + 90.0F;
//        float radians = (float) Math.toRadians(this.rotation);
        LobotomyMod.logger.info(this.rotation);
        float l = 0;
        while (l < Settings.WIDTH){
            l += 10.0F;
            AbstractDungeon.effectsQueue.add(new WhiteFixerFogEffect(sX + l * MathUtils.cos(this.rotation), sY + l * MathUtils.sin(this.rotation)));
        }

        if(this.one){
            this.isDone = true;
        }
    }

    public void end(){
        this.one = true;
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    public void dispose(){

    }
}
