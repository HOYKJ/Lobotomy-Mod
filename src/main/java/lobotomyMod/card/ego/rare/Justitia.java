package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
public class Justitia extends AbstractEgoCard {
    public static final String ID = "Justitia";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Justitia() {
        super("Justitia", Justitia.NAME, 3, Justitia.DESCRIPTION, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 20;
        this.isMultiDamage = true;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void applyPowers() {
        int tmp = this.baseDamage;
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()){
                for(AbstractPower p : m.powers){
                    if(p.type == AbstractPower.PowerType.DEBUFF){
                        this.baseDamage += this.magicNumber;
                    }
                }
            }
        }
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                this.baseDamage += this.magicNumber;
            }
        }
        super.applyPowers();
        this.baseDamage = tmp;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = this.baseDamage;
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()){
                for(AbstractPower p : m.powers){
                    if(p.type == AbstractPower.PowerType.DEBUFF){
                        this.baseDamage += this.magicNumber;
                    }
                }
            }
        }
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                this.baseDamage += this.magicNumber;
            }
        }
        super.calculateCardDamage(mo);
        this.baseDamage = tmp;
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(4);
        this.upgradeMagicNumber(2);
    }

    public AbstractCard makeCopy() {
        return new Justitia();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Justitia");
        NAME = Justitia.cardStrings.NAME;
        DESCRIPTION = Justitia.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Justitia.cardStrings.EXTENDED_DESCRIPTION;
    }
}
