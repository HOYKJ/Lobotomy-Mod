package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.AspirationHeartRelic;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class AspirationHeart extends AbstractLobotomyAbnRelic {
    public static final String ID = "AspirationHeart";
    private boolean killed = true;

    public AspirationHeart()
    {
        super("AspirationHeart",  RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(10, true);
        this.killed = true;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
//        if(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof AbstractOrdealMonster){
//            return;
//        }
//        if(!this.killed){
//            AbstractDungeon.player.maxHealth /= 2.0F;
//            if(AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth){
//                AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
//            }
//            AbstractDungeon.player.healthBarUpdatedEvent();
//        }
        this.killed = false;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        super.onMonsterDeath(m);
        this.killed = true;
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (!this.killed) {
            AbstractDungeon.player.maxHealth /= 2.0F;
            if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
            }
            AbstractDungeon.player.healthBarUpdatedEvent();
            this.killed = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD){
            try {
                Field smoke = AbstractDungeon.combatRewardScreen.getClass().getDeclaredField("smoke");
                smoke.setAccessible(true);
                if((boolean)smoke.get(AbstractDungeon.combatRewardScreen)) {
                    if (!this.killed) {
                        AbstractDungeon.player.maxHealth /= 2.0F;
                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }
                        AbstractDungeon.player.healthBarUpdatedEvent();
                        this.killed = true;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new AspirationHeartRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new AspirationHeart();
    }
}
