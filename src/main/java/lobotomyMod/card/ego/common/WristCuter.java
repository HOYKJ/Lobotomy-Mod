package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.WristCuterPower;

/**
 * @author hoykj
 */
public class WristCuter extends AbstractEgoCard {
    public static final String ID = "WristCuter";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public WristCuter() {
        super("WristCuter", WristCuter.NAME, 1, WristCuter.DESCRIPTION, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WristCuterPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(2);
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new WristCuter();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WristCuter");
        NAME = WristCuter.cardStrings.NAME;
        DESCRIPTION = WristCuter.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = WristCuter.cardStrings.EXTENDED_DESCRIPTION;
    }
}
