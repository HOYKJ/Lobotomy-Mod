package lobotomyMod.card.ego.common;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.power.LanternPower;

/**
 * @author hoykj
 */
public class Lantern extends AbstractEgoCard {
    public static final String ID = "Lantern";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Lantern() {
        super("Lantern", Lantern.NAME, 2, Lantern.DESCRIPTION, CardType.ATTACK, CardRarity.COMMON, CardTarget.NONE);
        this.baseDamage = 25;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, 1, false), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LanternPower(p, this.damage), this.damage));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(7);
    }

    public AbstractCard makeCopy() {
        return new Lantern();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Lantern");
        NAME = Lantern.cardStrings.NAME;
        DESCRIPTION = Lantern.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Lantern.cardStrings.EXTENDED_DESCRIPTION;
    }
}
