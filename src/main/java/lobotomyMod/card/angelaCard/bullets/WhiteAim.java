package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

/**
 * @author hoykj
 */
public class WhiteAim extends AbstractBulletCard {
    public static final String ID = "WhiteAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public WhiteAim() {
        super("WhiteAim", WhiteAim.NAME, 1, WhiteAim.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new WhiteAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WhiteAim");
        NAME = WhiteAim.cardStrings.NAME;
        DESCRIPTION = WhiteAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = WhiteAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
