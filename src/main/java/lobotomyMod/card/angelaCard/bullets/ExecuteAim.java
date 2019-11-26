package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import lobotomyMod.action.common.LatterAction;

/**
 * @author hoykj
 */
public class ExecuteAim extends AbstractBulletCard {
    public static final String ID = "ExecuteAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public ExecuteAim() {
        super("ExecuteAim", ExecuteAim.NAME, 1, ExecuteAim.DESCRIPTION, CardType.ATTACK, CardTarget.ENEMY);
        this.baseDamage = 30;
        this.purgeOnUse = true;
        this.removeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            m.damage(new DamageInfo(p, 999, DamageInfo.DamageType.HP_LOSS));
        }));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(m == null){
            return false;
        }
        if(m.currentHealth > this.damage){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new ExecuteAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ExecuteAim");
        NAME = ExecuteAim.cardStrings.NAME;
        DESCRIPTION = ExecuteAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ExecuteAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
