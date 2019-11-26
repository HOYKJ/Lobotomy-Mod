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
import lobotomyMod.card.deriveCard.AbstractDeriveCard;

/**
 * @author hoykj
 */
public class Apo_Slash extends AbstractDeriveCard {
    public static final String ID = "Apo_Slash";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Apo_Slash() {
        super("Apo_Slash", Apo_Slash.NAME, 2, Apo_Slash.DESCRIPTION, CardColor.COLORLESS, CardType.ATTACK, CardTarget.ENEMY);
        this.baseDamage = 16;
        this.exhaust = false;
        this.isEthereal = false;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Apo_Slash();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apo_Slash");
        NAME = Apo_Slash.cardStrings.NAME;
        DESCRIPTION = Apo_Slash.cardStrings.DESCRIPTION;
    }
}
