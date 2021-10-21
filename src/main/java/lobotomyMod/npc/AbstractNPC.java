package lobotomyMod.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/**
 * @author hoykj
 */
public abstract class AbstractNPC implements Disposable {
    public String ID;
    public boolean needRemove;
//    public Hitbox hb;
//    private boolean Rclick;
//    private float startX, startY;

    public void justEnteredRoom(AbstractRoom room) {}

    public void atBattleStart() {}

    public void onLoseHp(int damageAmount) {}

    public void atTurnStartPostDraw(){}

    public void onPlayerEndTurn(){}

    public void onMonsterDeath(AbstractMonster m) {}

    public void onVictory(){}

    public void update(){
//        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
//            this.Rclick = true;
//            this.startX = InputHelper.mX;
//            this.startY = InputHelper.mY;
//        }
//        if (InputHelper.justReleasedClickRight) {
//            this.Rclick = false;
//        }
//
//        if (this.hb != null) {
//            if (this.Rclick) {
//                this.hb.x += InputHelper.mX - this.startX;
//                this.hb.y += InputHelper.mY - this.startY;
//                this.startX = InputHelper.mX;
//                this.startY = InputHelper.mY;
//            }
//            if (this.hb.x < this.hb.width / 2) {
//                this.hb.x = this.hb.width / 2;
//            } else if (this.hb.x > Settings.WIDTH - this.hb.width / 2) {
//                this.hb.x = Settings.WIDTH - this.hb.width / 2;
//            }
//
//            if (this.hb.y < 0) {
//                this.hb.y = 0;
//            } else if (this.hb.y > Settings.HEIGHT - this.hb.height) {
//                this.hb.y = Settings.HEIGHT - this.hb.height;
//            }
//        }
    }

    public void render(SpriteBatch sb){}
}
