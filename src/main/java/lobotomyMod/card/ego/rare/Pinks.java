package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Pinks extends AbstractEgoCard {
    public static final String ID = "Pinks";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Pinks() {
        super("Pinks", Pinks.NAME, 2, Pinks.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            for(AbstractCard card : p.hand.group){
                if(card.baseDamage > 0){
                    card.baseDamage += this.magicNumber;
                    card.isDamageModified = true;
                }
                if(card.baseBlock > 0){
                    card.baseBlock += this.magicNumber;
                    card.isBlockModified = true;
                }
                card.applyPowers();
            }
        }));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(2);
    }

    public AbstractCard makeCopy() {
        return new Pinks();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Pinks");
        NAME = Pinks.cardStrings.NAME;
        DESCRIPTION = Pinks.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Pinks.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Pinks.cardStrings.EXTENDED_DESCRIPTION;
    }
}
