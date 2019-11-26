package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class CrimsonScar extends AbstractEgoCard {
    public static final String ID = "CrimsonScar";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CrimsonScar() {
        super("CrimsonScar", CrimsonScar.NAME, 1, CrimsonScar.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof BlueScar){
                this.damage *= 2;
            }
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof BlueScar){
                this.damage *= 2;
            }
        }
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof BlueScar){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
    }

    public AbstractCard makeCopy() {
        return new CrimsonScar();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CrimsonScar");
        NAME = CrimsonScar.cardStrings.NAME;
        DESCRIPTION = CrimsonScar.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CrimsonScar.cardStrings.EXTENDED_DESCRIPTION;
    }
}
