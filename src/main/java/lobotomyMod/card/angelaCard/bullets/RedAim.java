package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author hoykj
 */
public class RedAim extends AbstractBulletCard {
    public static final String ID = "RedAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public RedAim() {
        super("RedAim", RedAim.NAME, 1, RedAim.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        this.baseBlock = 10;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    public AbstractCard makeCopy() {
        return new RedAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("RedAim");
        NAME = RedAim.cardStrings.NAME;
        DESCRIPTION = RedAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = RedAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
