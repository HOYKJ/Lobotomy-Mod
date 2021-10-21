package lobotomyMod.card.deriveCard.ApostleCardGroup;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;

/**
 * @author hoykj
 */
public class Apo_Charge extends AbstractDeriveCard {
    public static final String ID = "Apo_Charge";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Apo_Charge() {
        super("Apo_Charge", Apo_Charge.NAME, 2, Apo_Charge.DESCRIPTION, CardColor.COLORLESS, CardType.ATTACK, CardTarget.ALL_ENEMY);
        this.baseDamage = 25;
        this.isMultiDamage = true;
        this.exhaust = false;
        this.isEthereal = false;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Apo_Charge();
    }

    @Override
    public void obtain() {
        super.obtain();
        UnlockTracker.unlockCard(this.cardID);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apo_Charge");
        NAME = Apo_Charge.cardStrings.NAME;
        DESCRIPTION = Apo_Charge.cardStrings.DESCRIPTION;
    }
}
