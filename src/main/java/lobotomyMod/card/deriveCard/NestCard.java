package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author hoykj
 */
public class NestCard extends AbstractDeriveCard{
    public static final String ID = "NestCard";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public NestCard(AbstractCard card) {
        super("NestCard", NestCard.NAME, 0, NestCard.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.name = card.name;
        this.initializeTitle();
        this.rawDescription = card.rawDescription + NestCard.DESCRIPTION;
        this.cost = card.cost;
        this.costForTurn = card.costForTurn;
        this.type = card.type;
        this.color = card.color;
        this.rarity = card.rarity;
        this.target = card.target;
        this.upgraded = card.upgraded;
        this.timesUpgraded = card.timesUpgraded;
        this.baseDamage = card.baseDamage;
        this.baseBlock = card.baseBlock;
        this.baseMagicNumber = card.baseMagicNumber;
        this.isCostModified = card.isCostModified;
        this.isCostModifiedForTurn = card.isCostModifiedForTurn;
        this.inBottleLightning = card.inBottleLightning;
        this.inBottleFlame = card.inBottleFlame;
        this.inBottleTornado = card.inBottleTornado;
        this.isSeen = card.isSeen;
        this.isLocked = card.isLocked;
        this.misc = card.misc;
        this.freeToPlayOnce = card.freeToPlayOnce;
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 4)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new NestCard(this);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("NestCard");
        NAME = NestCard.cardStrings.NAME;
        DESCRIPTION = NestCard.cardStrings.DESCRIPTION;
    }
}
