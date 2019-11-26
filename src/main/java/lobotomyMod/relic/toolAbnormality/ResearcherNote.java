package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DeathScreen;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.ResearcherNoteRelic;

/**
 * @author hoykj
 */
public class ResearcherNote extends AbstractLobotomyAbnRelic {
    public static final String ID = "ResearcherNote";

    public ResearcherNote()
    {
        super("ResearcherNote",  RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 0;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        super.onLoseHp(damageAmount);
        this.counter += damageAmount;
        if(this.counter >= 60){
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new ResearcherNoteRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new ResearcherNote();
    }
}
