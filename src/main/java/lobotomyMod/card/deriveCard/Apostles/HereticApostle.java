package lobotomyMod.card.deriveCard.Apostles;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.commonCard.OSAHOGD;
import lobotomyMod.card.deriveCard.SilentAdagio;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.monster.WhiteNightMonster;

/**
 * @author hoykj
 */
public class HereticApostle extends AbstractApostleCard {
    public static final String ID = "HereticApostle";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public HereticApostle() {
        super("HereticApostle", HereticApostle.NAME, 1, HereticApostle.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.exhaust = false;
        this.purgeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof WhiteNight){
                ((WhiteNight) card).die();
                return;
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof WhiteNight){
                ((WhiteNight) card).die();
                return;
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof WhiteNight){
                ((WhiteNight) card).die();
                return;
            }
        }
        if(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof WhiteNightMonster){
            AbstractDungeon.getCurrRoom().monsters.monsters.get(0).damage(new DamageInfo(AbstractDungeon.player, 666, DamageInfo.DamageType.HP_LOSS));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof OSAHOGD){
                return true;
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof OSAHOGD){
                return true;
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof OSAHOGD){
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HereticApostle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("HereticApostle");
        NAME = HereticApostle.cardStrings.NAME;
        DESCRIPTION = HereticApostle.cardStrings.DESCRIPTION;
    }
}
