package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Beak extends AbstractEgoCard {
    public static final String ID = "Beak";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private boolean lose;

    public Beak() {
        super("Beak", Beak.NAME, 0, Beak.DESCRIPTION, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 3;
        this.lose = false;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                (this.lose? AbstractGameAction.AttackEffect.SLASH_HEAVY: AbstractGameAction.AttackEffect.BLUNT_LIGHT)));
    }

    @Override
    public void onLoseHP(int damageAmount, boolean hand) {
        super.onLoseHP(damageAmount, hand);
        this.lose = true;
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        this.lose = false;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(this.lose){
            this.damage *= 3;
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(this.lose){
            this.damage *= 3;
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new Beak();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Beak");
        NAME = Beak.cardStrings.NAME;
        DESCRIPTION = Beak.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Beak.cardStrings.EXTENDED_DESCRIPTION;
    }
}
