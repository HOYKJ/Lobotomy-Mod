package lobotomyMod.room;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import lobotomyMod.event.BossRushEvent;

/**
 * @author hoykj
 */
public class NewVictoryRoom extends VictoryRoom {

    public NewVictoryRoom(EventType type){
        super(type);
    }

    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new BossRushEvent();
        this.event.onEnterRoom();
//        CardCrawlGame.fadeIn(1.5F);
//        AbstractDungeon.closeCurrentScreen();
//        AbstractDungeon.player.isDying = true;
//        AbstractDungeon.player.isDead = true;
//        AbstractDungeon.deathScreen = new DeathScreen(null);
    }

    @Override
    public void update() {
        super.update();
        if ((this.event.waitTimer == 0.0F) && (!this.event.hasFocus) && (this.phase != AbstractRoom.RoomPhase.COMBAT)) {
            this.phase = AbstractRoom.RoomPhase.COMPLETE;
            this.event.reopen();
        }
    }
}
