package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.HellTrainRelic;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.vfx.HellTrainEffect;

/**
 * @author hoykj
 */
public class HellTrain extends AbstractLobotomyAbnRelic {
    public static final String ID = "HellTrain";

    public HellTrain()
    {
        super("HellTrain",  RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();

    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        CardCrawlGame.sound.play("Train_Sell");
        switch (this.counter){
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApotheosisAction());
                break;
            default:
        }
        this.counter = 0;
        this.changeImg();
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.counter ++;
        if(this.counter > 4){
            this.counter = 0;
            AbstractDungeon.effectList.add(new HellTrainEffect());
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                for(int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i --){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getCurrRoom().monsters.monsters.get(i), new DamageInfo(AbstractDungeon.player, 50, DamageInfo.DamageType.THORNS)));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 50, DamageInfo.DamageType.THORNS)));
            }, 2.0F));
        }
        this.changeImg();
    }

    private void changeImg(){
        if(this.counter > 0) {
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("HellTrain_" + this.counter));
        }
        else {
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("HellTrain"));
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new HellTrainRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new HellTrain();
    }
}
