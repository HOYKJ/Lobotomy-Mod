package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class Spore extends AbstractEgoCard {
    public static final String ID = "Spore";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Spore() {
        super("Spore", Spore.NAME, 1, Spore.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DiscoveryAction());
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.exhaust = false;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Spore();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Spore");
        NAME = Spore.cardStrings.NAME;
        DESCRIPTION = Spore.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Spore.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Spore.cardStrings.EXTENDED_DESCRIPTION;
    }
}
