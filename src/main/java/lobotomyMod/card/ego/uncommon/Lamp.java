package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.unique.LampAttackAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Lamp extends AbstractEgoCard {
    public static final String ID = "Lamp";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Lamp() {
        super("Lamp", Lamp.NAME, 2, Lamp.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 20;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LampAttackAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.uuid));
    }

    @Override
    public void upgrade()
    {
        this.upgradeDamage(4);
        this.timesUpgraded ++;
        this.upgraded = true;
        this.name = (NAME + "+" + this.timesUpgraded);
        this.initializeTitle();
    }

    public boolean canUpgrade()
    {
        return true;
    }

    public AbstractCard makeCopy() {
        return new Lamp();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Lamp");
        NAME = Lamp.cardStrings.NAME;
        DESCRIPTION = Lamp.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Lamp.cardStrings.EXTENDED_DESCRIPTION;
    }
}
