package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class CENSORED_ego extends AbstractEgoCard {
    public static final String ID = "CENSORED_ego";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CENSORED_ego() {
        super("CENSORED_ego", CENSORED_ego.NAME, 3, CENSORED_ego.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!monster.isDeadOrEscaped()){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new StrengthPower(monster, -this.magicNumber), -this.magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new CENSORED_ego();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CENSORED_ego");
        NAME = CENSORED_ego.cardStrings.NAME;
        DESCRIPTION = CENSORED_ego.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CENSORED_ego.cardStrings.EXTENDED_DESCRIPTION;
    }
}
