package lobotomyMod.relic;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.helper.LobotomyUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class AtMidnight extends AbstractLobotomyRelic{
    public static final String ID = "AtMidnight";
    private boolean active, end;

    public AtMidnight()
    {
        super("AtMidnight",  RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.active = true;
        this.end = false;
    }

    @Override
    public void obtain() {
        super.obtain();
        this.active = true;
        this.end = false;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        this.active = !this.active;
        if(this.active){
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("AtMidnight"));
        }
        else {
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("AtMidnight_1"));
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        LobotomyMod.logger.info(this.active + "  " + this.end);
        if(!this.active || this.end){
            return;
        }
        if(!(AbstractDungeon.id.equals("TheBeyond")) || !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)){
            return;
        }
        for(String str : AbstractDungeon.bossList){
            LobotomyMod.logger.info(str);
        }
        boolean tmp[] = {true, true, true};
        switch (AbstractDungeon.bossList.get(0)) {
            case "Donu and Deca":
                tmp[0] = false;
                break;
            case "Time Eater":
                tmp[1] = false;
                break;
            case "Awakened One":
                tmp[2] = false;
                break;
        }
        if(AbstractDungeon.bossList.size() > 1) {
            switch (AbstractDungeon.bossList.get(1)) {
                case "Donu and Deca":
                    tmp[0] = false;
                    break;
                case "Time Eater":
                    tmp[1] = false;
                    break;
                case "Awakened One":
                    tmp[2] = false;
                    break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()-> {
            this.end = true;
            if(tmp[0]){
                LobotomyUtils.OrdealStart(1, 4);
            }
            else if(tmp[1]){
                LobotomyUtils.OrdealStart(2, 4);
            }
            else {
                LobotomyUtils.OrdealStart(3, 4);
            }
        }));
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new AtMidnight();
    }
}
