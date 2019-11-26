package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class TodaysExpression extends AbstractEgoCard {
    public static final String ID = "TodaysExpression";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public TodaysExpression() {
        super("TodaysExpression", TodaysExpression.NAME, 1, TodaysExpression.DESCRIPTION, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 2;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 0;
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                this.baseMagicNumber += this.block * (p.amount > 0? p.amount: 1);
            }
        }
        this.magicNumber = this.baseMagicNumber;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.baseMagicNumber = 0;
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                this.baseMagicNumber += this.block * (p.amount > 0? p.amount: 1);
            }
        }
        this.magicNumber = this.baseMagicNumber;
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(1);
    }

    public AbstractCard makeCopy() {
        return new TodaysExpression();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("TodaysExpression");
        NAME = TodaysExpression.cardStrings.NAME;
        DESCRIPTION = TodaysExpression.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = TodaysExpression.cardStrings.EXTENDED_DESCRIPTION;
    }
}
