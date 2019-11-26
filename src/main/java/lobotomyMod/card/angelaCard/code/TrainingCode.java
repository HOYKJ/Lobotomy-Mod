package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.actions.unique.ReprogramAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.department.Hod;
import lobotomyMod.card.angelaCard.department.Malkuth;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class TrainingCode extends AbstractCodeCard {
    public static final String ID = "TrainingCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public TrainingCode() {
        super("TrainingCode", TrainingCode.NAME, Hod.ID, 0, TrainingCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 3 + this.timesUpgraded;
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
        this.baseMagicNumber = 3 + this.timesUpgraded;
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true, this.magicNumber, true);
        for(AbstractCard card : AbstractDungeon.player.hand.group) {
            if(!card.canUpgrade()){
                continue;
            }
            choice.add(card, card::upgrade);
        }
        if(AbstractDungeon.player instanceof Angela && ((Angela) AbstractDungeon.player).departments[3] >= 3){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if(!card.canUpgrade()){
                    continue;
                }
                choice.add(card, card::upgrade);
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if(!card.canUpgrade()){
                    continue;
                }
                choice.add(card, card::upgrade);
            }
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

    @Override
    public void expand() {
        super.expand();
        if(AbstractDungeon.player instanceof Angela && ((Angela) AbstractDungeon.player).departments[3] >= 3) {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new TrainingCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("TrainingCode");
        NAME = TrainingCode.cardStrings.NAME;
        DESCRIPTION = TrainingCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = TrainingCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
