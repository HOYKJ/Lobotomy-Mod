package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author hoykj
 */
public class Sheep extends AbstractDeriveCard{
    public static final String ID = "Sheep";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Sheep() {
        super("Sheep", Sheep.NAME, 0, Sheep.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.baseMagicNumber = 9;
        this.magicNumber = this.baseMagicNumber;
        this.purgeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        p.maxHealth -= 3;
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sheep();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Sheep");
        NAME = Sheep.cardStrings.NAME;
        DESCRIPTION = Sheep.cardStrings.DESCRIPTION;
    }
}
