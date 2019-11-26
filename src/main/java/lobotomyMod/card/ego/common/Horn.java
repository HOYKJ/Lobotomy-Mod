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
public class Horn extends AbstractEgoCard {
    public static final String ID = "Horn";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Horn() {
        super("Horn", Horn.NAME, 1, Horn.DESCRIPTION, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 0;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.damage += (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) / 2;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.damage += (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) / 2;
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);
    }

    public AbstractCard makeCopy() {
        return new Horn();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Horn");
        NAME = Horn.cardStrings.NAME;
        DESCRIPTION = Horn.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Horn.cardStrings.EXTENDED_DESCRIPTION;
    }
}
