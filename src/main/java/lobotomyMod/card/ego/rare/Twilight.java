package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Twilight extends AbstractEgoCard {
    public static final String ID = "Twilight";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Twilight() {
        super("Twilight", Twilight.NAME, 2, Twilight.DESCRIPTION, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseBlock = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.purgeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
//        AbstractCard r = null;
//        for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
//            if(card.uuid.equals(this.uuid)){
//                r = card;
//                break;
//            }
//        }
//        if(r != null){
//            AbstractDungeon.player.masterDeck.removeCard(r);
//        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(2);
        this.upgradeBlock(2);
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new Twilight();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Twilight");
        NAME = Twilight.cardStrings.NAME;
        DESCRIPTION = Twilight.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Twilight.cardStrings.EXTENDED_DESCRIPTION;
    }
}
