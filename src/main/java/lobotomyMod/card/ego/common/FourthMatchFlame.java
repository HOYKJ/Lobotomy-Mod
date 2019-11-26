package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class FourthMatchFlame extends AbstractEgoCard {
    public static final String ID = "FourthMatchFlame";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public FourthMatchFlame() {
        super("FourthMatchFlame", FourthMatchFlame.NAME, -2, FourthMatchFlame.DESCRIPTION, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 12;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        this.applyPowers();
        this.calculateCardDamage(null);
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(4);
    }

    public AbstractCard makeCopy() {
        return new FourthMatchFlame();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FourthMatchFlame");
        NAME = FourthMatchFlame.cardStrings.NAME;
        DESCRIPTION = FourthMatchFlame.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = FourthMatchFlame.cardStrings.EXTENDED_DESCRIPTION;
    }
}
