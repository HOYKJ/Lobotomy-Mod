package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

/**
 * @author hoykj
 */
public class BlackAim extends AbstractBulletCard {
    public static final String ID = "BlackAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BlackAim() {
        super("BlackAim", BlackAim.NAME, 1, BlackAim.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new BlackAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlackAim");
        NAME = BlackAim.cardStrings.NAME;
        DESCRIPTION = BlackAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BlackAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
