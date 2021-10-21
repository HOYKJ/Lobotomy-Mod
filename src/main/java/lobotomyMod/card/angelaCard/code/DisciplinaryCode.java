package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.department.Geburah;

/**
 * @author hoykj
 */
public class DisciplinaryCode extends AbstractCodeCard {
    public static final String ID = "DisciplinaryCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public DisciplinaryCode() {
        super("DisciplinaryCode", DisciplinaryCode.NAME, Geburah.ID, 0, DisciplinaryCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true, this.magicNumber, true);
        for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
            choice.add(card, ()->{
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            });
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group) {
            if(card == this){
                continue;
            }
            choice.add(card, ()->{
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            });
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
            choice.add(card, ()->{
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            });
        }
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
    }

    public AbstractCard makeCopy() {
        return new DisciplinaryCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("DisciplinaryCode");
        NAME = DisciplinaryCode.cardStrings.NAME;
        DESCRIPTION = DisciplinaryCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = DisciplinaryCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
