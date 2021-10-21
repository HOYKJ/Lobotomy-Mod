package lobotomyMod.card.deriveCard.ApostleCardGroup;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;

/**
 * @author hoykj
 */
public class Apo_Execute extends AbstractDeriveCard {
    public static final String ID = "Apo_Execute";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Apo_Execute() {
        super("Apo_Execute", Apo_Execute.NAME, 3, Apo_Execute.DESCRIPTION, CardColor.COLORLESS, CardType.ATTACK, CardTarget.ENEMY);
        this.exhaust = false;
        this.isEthereal = false;
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
        if(m.currentHealth > 100){
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Apo_Execute();
    }

    @Override
    public void obtain() {
        super.obtain();
        UnlockTracker.unlockCard(this.cardID);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apo_Execute");
        NAME = Apo_Execute.cardStrings.NAME;
        DESCRIPTION = Apo_Execute.cardStrings.DESCRIPTION;
    }
}
