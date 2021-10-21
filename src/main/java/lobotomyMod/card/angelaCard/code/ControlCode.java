package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.department.Malkuth;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class ControlCode extends AbstractCodeCard {
    public static final String ID = "ControlCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public ControlCode() {
        super("ControlCode", ControlCode.NAME, Malkuth.ID, 1, ControlCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 2 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        this.expand();
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 2 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true, this.magicNumber, true);
        for(AbstractCard card : AbstractDungeon.player.hand.group) {
            if(card.costForTurn < 1 || card == this){
                continue;
            }
            choice.add(card, ()->{
//                if(card instanceof AbstractLobotomyCard){
//                    ((AbstractLobotomyCard) card).changeCost(((AbstractLobotomyCard) card).realCost - 1);
//                }
                card.setCostForTurn(card.costForTurn - 1);
            });
        }
        if(AbstractDungeon.player instanceof Angela && Angela.departments[0] >= 3){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if(card.costForTurn < 1){
                    continue;
                }
                choice.add(card, ()->{
                    card.setCostForTurn(card.costForTurn - 1);
                });
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if(card.costForTurn < 1){
                    continue;
                }
                choice.add(card, ()->{
                    card.setCostForTurn(card.costForTurn - 1);
                });
            }
        }
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseMagicNumber = 2 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void expand() {
        super.expand();
        if(AbstractDungeon.player instanceof Angela && Angela.departments[0] >= 3) {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new ControlCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ControlCode");
        NAME = ControlCode.cardStrings.NAME;
        DESCRIPTION = ControlCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ControlCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
