package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DeathScreen;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.FleshIdolRelic;

/**
 * @author hoykj
 */
public class FleshIdol extends AbstractLobotomyAbnRelic {
    public static final String ID = "FleshIdol";

    public FleshIdol()
    {
        super("FleshIdol",  RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 1;
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if(this.counter < 5){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 2)));
        }
        else if(this.counter < 18){
            int tmp = 2;
            if(this.counter > 8){
                tmp = 4;
            }
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, tmp));
        }
        else {
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }
        this.counter ++;
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new FleshIdolRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new FleshIdol();
    }
}
