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

/**
 * @author hoykj
 */
public class BlueScar extends AbstractEgoCard {
    public static final String ID = "BlueScar";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BlueScar() {
        super("BlueScar", BlueScar.NAME, 1, BlueScar.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 9;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof CrimsonScar){
                this.block *= 2;
            }
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof CrimsonScar){
                this.block *= 2;
            }
        }
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof CrimsonScar){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
    }

    public AbstractCard makeCopy() {
        return new BlueScar();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlueScar");
        NAME = BlueScar.cardStrings.NAME;
        DESCRIPTION = BlueScar.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BlueScar.cardStrings.EXTENDED_DESCRIPTION;
    }
}
