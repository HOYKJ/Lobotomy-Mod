package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.card.ego.uncommon.Pleasure;

/**
 * @author hoykj
 */
public class CUTE extends AbstractEgoCard {
    public static final String ID = "CUTE";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CUTE() {
        super("CUTE", CUTE.NAME, 1, CUTE.DESCRIPTION, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getRandomMonster(), false));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.exhaust = false;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new CUTE();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CUTE");
        NAME = CUTE.cardStrings.NAME;
        DESCRIPTION = CUTE.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = CUTE.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = CUTE.cardStrings.EXTENDED_DESCRIPTION;
    }
}
