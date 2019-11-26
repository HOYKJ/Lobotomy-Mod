package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
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
public class Moonlight extends AbstractEgoCard {
    public static final String ID = "Moonlight";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Moonlight() {
        super("Moonlight", Moonlight.NAME, 3, Moonlight.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        this.baseDamage = 6;
        this.baseBlock = 6;
        this.isMultiDamage = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, p.hand.size(), true));
        for(int i = 0; i < this.multiDamage.length; i ++){
            this.multiDamage[i] *= (p.hand.size() - 1);
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block * (p.hand.size() - 1)));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(2);
        this.upgradeBlock(2);
    }

    public AbstractCard makeCopy() {
        return new Moonlight();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Moonlight");
        NAME = Moonlight.cardStrings.NAME;
        DESCRIPTION = Moonlight.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Moonlight.cardStrings.EXTENDED_DESCRIPTION;
    }
}
