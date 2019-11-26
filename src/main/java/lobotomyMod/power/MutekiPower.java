package lobotomyMod.power;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.monster.Ordeal.Fixer.WhiteFixer;

/**
 * @author hoykj
 */
public class MutekiPower extends AbstractPower {
    public static final String POWER_ID = "MutekiPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MutekiPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MutekiPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "MutekiPower";
        this.owner = owner;
        this.amount = -1;
//        this.img = ImageMaster.loadImage("lobotomyMod/images/powers/32/MutekiPower.png");
        loadRegion("thorns");
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.owner != null && info.owner != this.owner) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(info.owner, info));
        }
        return 0;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        if(this.owner instanceof WhiteFixer){
            ((WhiteFixer) this.owner).changeState("PRAY_END");
        }
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }
}
