package lobotomyMod.room;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rooms.EventRoom;
import lobotomyMod.LobotomyMod;
import lobotomyMod.event.BossRushEvent;

/**
 * @author hoykj
 */
public class BossRushRoom extends EventRoom {
    public BossRushRoom(){
        super();
    }

    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new BossRushEvent();
        LobotomyMod.logger.info("test 2");
        this.event.onEnterRoom();
    }
}
