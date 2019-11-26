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
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;

/**
 * @author hoykj
 */
public class Apo_SoulSlash extends AbstractDeriveCard {
    public static final String ID = "Apo_SoulSlash";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Apo_SoulSlash() {
        super("Apo_SoulSlash", Apo_SoulSlash.NAME, 2, Apo_SoulSlash.DESCRIPTION, CardColor.COLORLESS, CardType.ATTACK, CardTarget.ENEMY);
        this.baseDamage = 18;
        this.exhaust = false;
        this.isEthereal = false;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new LatterAction(()->{
            m.maxHealth -= this.damage;
        }));
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));
        AbstractDungeon.actionManager.addToTop(new LatterAction(()->{
            m.maxHealth -= this.damage;
        }));
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Apo_SoulSlash();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apo_SoulSlash");
        NAME = Apo_SoulSlash.cardStrings.NAME;
        DESCRIPTION = Apo_SoulSlash.cardStrings.DESCRIPTION;
    }
}
