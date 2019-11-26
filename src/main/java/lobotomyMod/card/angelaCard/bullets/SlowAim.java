package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

/**
 * @author hoykj
 */
public class SlowAim extends AbstractBulletCard {
    public static final String ID = "SlowAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SlowAim() {
        super("SlowAim", SlowAim.NAME, 1, SlowAim.DESCRIPTION, CardType.SKILL, CardTarget.ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new SlowAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SlowAim");
        NAME = SlowAim.cardStrings.NAME;
        DESCRIPTION = SlowAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SlowAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
