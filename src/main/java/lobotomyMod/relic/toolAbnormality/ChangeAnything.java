package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.ChangeAnythingRelic;
import lobotomyMod.character.LobotomyHandler;

/**
 * @author hoykj
 */
public class ChangeAnything extends AbstractLobotomyAbnRelic {
    public static final String ID = "ChangeAnything";
    private boolean canUse;
    private boolean active;
    private boolean used;
    private int turn;

    public ChangeAnything()
    {
        super("ChangeAnything",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.canUse = false;
        this.used = false;
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.canUse = true;
        this.active = false;
        this.counter = 0;
        this.turn = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.canUse = false;
        if(this.active){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.counter, DamageInfo.DamageType.HP_LOSS)));
            if(this.turn > 2){
                this.turn = 0;
                this.counter ++;
            }
            else {
                this.turn ++;
            }
            AbstractDungeon.player.gainGold(10);
        }
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if(this.canUse){
            this.active = true;
            this.used = true;
            this.counter = 1;
            changeImg();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.active = false;
        this.counter = 0;
    }

    private void changeImg(){
        if(this.active) {
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("ChangeAnything_1"));
        }
        else if(this.used){
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("ChangeAnything_2"));
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new ChangeAnythingRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new ChangeAnything();
    }
}
