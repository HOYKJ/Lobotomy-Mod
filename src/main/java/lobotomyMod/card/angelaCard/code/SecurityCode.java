package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.department.Netzach;
import lobotomyMod.character.Angela;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class SecurityCode extends AbstractCodeCard {
    public static final String ID = "SecurityCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SecurityCode() {
        super("SecurityCode", SecurityCode.NAME, Netzach.ID, -2, SecurityCode.DESCRIPTION, CardTarget.SELF);
        this.baseMagicNumber = 8 + this.timesUpgraded * 3;
        if(AbstractDungeon.player instanceof Angela && Angela.departments[2] >= 2){
            this.baseMagicNumber += 4;
        }
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
        this.baseMagicNumber = 8 + this.timesUpgraded * 3;
        if(AbstractDungeon.player instanceof Angela && Angela.departments[2] >= 2){
            this.baseMagicNumber += 4;
        }
        this.magicNumber = this.baseMagicNumber;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(AbstractDungeon.player instanceof Angela && Angela.departments[2] >= 4){
            AbstractDungeon.player.heal((int)(this.magicNumber * 0.25F));
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        AbstractDungeon.player.heal(this.magicNumber);
        if(AbstractDungeon.player instanceof Angela && Angela.departments[2] >= 3){
            ArrayList<AbstractCard> list = new ArrayList<>();
            for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                if(card.type == CardType.CURSE){
                    list.add(card);
                }
            }
            if(list.size() < 1){
                return;
            }
            AbstractCard tmp = list.get(AbstractDungeon.cardRng.random(list.size() - 1));
            AbstractDungeon.player.masterDeck.removeCard(tmp);
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseMagicNumber = 8 + this.timesUpgraded * 3;
        if(AbstractDungeon.player instanceof Angela && Angela.departments[2] >= 2){
            this.baseMagicNumber += 4;
        }
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void expand() {
        super.expand();
        if(AbstractDungeon.player instanceof Angela) {
            this.baseMagicNumber = 8 + this.timesUpgraded * 4;
            if(Angela.departments[2] >= 2){
                this.baseMagicNumber += 4;
            }
            this.magicNumber = this.baseMagicNumber;
            if(Angela.departments[2] >= 4) {
                this.rawDescription = EXTENDED_DESCRIPTION[1];
                initializeDescription();
            }
            else if(Angela.departments[2] >= 3) {
                this.rawDescription = EXTENDED_DESCRIPTION[0];
                initializeDescription();
            }
        }
    }

    public AbstractCard makeCopy() {
        return new SecurityCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SecurityCode");
        NAME = SecurityCode.cardStrings.NAME;
        DESCRIPTION = SecurityCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SecurityCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
