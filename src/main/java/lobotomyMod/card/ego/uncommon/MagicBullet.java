package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
public class MagicBullet extends AbstractEgoCard {
    public static final String ID = "MagicBullet";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public MagicBullet() {
        super("MagicBullet", MagicBullet.NAME, 1, MagicBullet.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 18;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for(AbstractCard card : p.discardPile.group){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.discardPile));
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(6);
    }

    public AbstractCard makeCopy() {
        return new MagicBullet();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MagicBullet");
        NAME = MagicBullet.cardStrings.NAME;
        DESCRIPTION = MagicBullet.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = MagicBullet.cardStrings.EXTENDED_DESCRIPTION;
    }
}
