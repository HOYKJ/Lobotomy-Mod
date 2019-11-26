package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class Diffraction extends AbstractEgoCard {
    public static final String ID = "Diffraction";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Diffraction() {
        super("Diffraction", Diffraction.NAME, 1, Diffraction.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(-2);
    }

    public AbstractCard makeCopy() {
        return new Diffraction();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Diffraction");
        NAME = Diffraction.cardStrings.NAME;
        DESCRIPTION = Diffraction.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Diffraction.cardStrings.EXTENDED_DESCRIPTION;
    }
}
