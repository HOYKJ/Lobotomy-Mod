package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.ego.AbstractEgoCard;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class SomewhereSpear extends AbstractEgoCard {
    public static final String ID = "SomewhereSpear";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private AbstractCard card;

    public SomewhereSpear() {
        super("SomewhereSpear", SomewhereSpear.NAME, 1, SomewhereSpear.DESCRIPTION, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.card = null;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.card != null){
            this.card.use(p, m);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            ArrayList<AbstractCard> list = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if(!(c instanceof SomewhereSpear)){
                    list.add(c.makeCopy());
                }
            }
            if(list.size() < 1){
                return;
            }
            this.card = list.get(AbstractDungeon.cardRng.random(list.size() - 1)).makeCopy();
            this.initCard();
        }));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(this.card == null){
            return;
        }
        this.card.applyPowers();
        this.initCard();
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(this.card == null){
            return;
        }
        this.card.applyPowers();
        this.initCard();
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);
    }

    private void initCard(){
        this.baseDamage = this.card.baseDamage;
        this.baseBlock = this.card.baseBlock;
        this.baseMagicNumber = this.card.baseMagicNumber;
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = this.card.rawDescription;
        this.target = this.card.target;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new SomewhereSpear();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SomewhereSpear");
        NAME = SomewhereSpear.cardStrings.NAME;
        DESCRIPTION = SomewhereSpear.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SomewhereSpear.cardStrings.EXTENDED_DESCRIPTION;
    }
}
