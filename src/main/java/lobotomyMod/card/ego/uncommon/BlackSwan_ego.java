package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class BlackSwan_ego extends AbstractEgoCard {
    public static final String ID = "BlackSwan_ego";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BlackSwan_ego() {
        super("BlackSwan_ego", BlackSwan_ego.NAME, 6, BlackSwan_ego.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 9;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void tookDamage() {
        super.tookDamage();
        this.updateCost(-1);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(3);
    }

    public AbstractCard makeCopy() {
        return new BlackSwan_ego();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlackSwan_ego");
        NAME = BlackSwan_ego.cardStrings.NAME;
        DESCRIPTION = BlackSwan_ego.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BlackSwan_ego.cardStrings.EXTENDED_DESCRIPTION;
    }
}
