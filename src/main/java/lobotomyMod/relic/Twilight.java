package lobotomyMod.relic;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.rareCard.ApocalypseBird;
import lobotomyMod.power.TwilightPower;

/**
 * @author hoykj
 */
public class Twilight extends AbstractLobotomyRelic{
    public static final String ID = "Twilight";

    public Twilight()
    {
        super("Twilight",  RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if(AbstractDungeon.player.masterDeck.findCardById(ApocalypseBird.ID) == null){
            return;
        }
        AbstractDungeon.player.powers.add(new TwilightPower(AbstractDungeon.player));
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if(AbstractDungeon.player.masterDeck.findCardById(ApocalypseBird.ID) == null){
            return;
        }
        if(target instanceof AbstractMonster) {
            target.maxHealth -= info.base;
            if (target.maxHealth <= 0) {
                target.maxHealth = 0;
                target.hideHealthBar();
                AbstractDungeon.getCurrRoom().cannotLose = false;
                ((AbstractMonster) target).die();
            }
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Twilight();
    }
}
