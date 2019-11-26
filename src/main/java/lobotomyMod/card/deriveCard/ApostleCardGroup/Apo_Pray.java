package lobotomyMod.card.deriveCard.ApostleCardGroup;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;
import lobotomyMod.power.Gospel;

/**
 * @author hoykj
 */
public class Apo_Pray extends AbstractDeriveCard {
    public static final String ID = "Apo_Pray";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Apo_Pray() {
        super("Apo_Pray", Apo_Pray.NAME, 3, Apo_Pray.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.purgeOnUse = true;
        this.exhaust = false;
        this.isEthereal = false;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Gospel(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Apo_Pray();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apo_Pray");
        NAME = Apo_Pray.cardStrings.NAME;
        DESCRIPTION = Apo_Pray.cardStrings.DESCRIPTION;
    }
}
