package lobotomyMod.relic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import lobotomyMod.LobotomyMod;
import lobotomyMod.room.NewVictoryRoom;
import lobotomyMod.vfx.rabbitTeam.order.RabbitOrderScreen;

import java.io.IOException;

/**
 * @author hoykj
 */
public class RabbitCall extends AbstractLobotomyRelic{
    public static final String ID = "RabbitCall";
    private boolean use;

    public RabbitCall()
    {
        super("RabbitCall",  RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.counter = 1;
    }

    @Override
    public void obtain() {
        super.obtain();
        this.counter = 1;
    }

    @Override
    public void onUnequip() {
        super.onUnequip();
        LobotomyMod.activeRabbit = false;
        LobotomyMod.defeatFixer = false;
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        this.use = true;
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.use = false;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if(this.counter < 1 || !this.use){
            LobotomyMod.logger.info("not your turn or run out");
            return;
        }
        if(AbstractDungeon.currMapNode == null || (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !(AbstractDungeon.getCurrRoom() instanceof NewVictoryRoom))){
            LobotomyMod.logger.info("not monster room");
            return;
        }
        LobotomyMod.rabbitOrderScreen = new RabbitOrderScreen();
    }

//    @Override
//    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
//    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new RabbitCall();
    }
}
