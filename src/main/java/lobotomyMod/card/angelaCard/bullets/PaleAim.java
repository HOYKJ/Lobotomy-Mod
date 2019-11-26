package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

/**
 * @author hoykj
 */
public class PaleAim extends AbstractBulletCard {
    public static final String ID = "PaleAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public PaleAim() {
        super("PaleAim", PaleAim.NAME, 1, PaleAim.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.purgeOnUse = true;
        this.removeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new PaleAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PaleAim");
        NAME = PaleAim.cardStrings.NAME;
        DESCRIPTION = PaleAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = PaleAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
