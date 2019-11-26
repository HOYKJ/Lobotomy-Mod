package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class DaCapo extends AbstractEgoCard {
    public static final String ID = "DaCapo";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public ArrayList<CardQueueItem> queue = new ArrayList<>();

    public DaCapo() {
        super("DaCapo", DaCapo.NAME, 2, DaCapo.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(CardQueueItem tmp : this.queue){
            AbstractDungeon.player.limbo.addToBottom(tmp.card);
            AbstractDungeon.actionManager.cardQueue.add(tmp);
        }
        //AbstractDungeon.actionManager.cardQueue.addAll(this.queue);
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        if(card instanceof DaCapo){
            return;
        }

            flash();
            AbstractMonster mo = null;
            if (target != null) {
                mo = (AbstractMonster)target;
            }
            AbstractCard tmp = card.makeSameInstanceOf();
            //AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmp.target_y = (Settings.HEIGHT / 2.0F);
            if (tmp.cost > 0) {
                tmp.freeToPlayOnce = true;
            }
            if (mo != null) {
                tmp.calculateCardDamage(mo);
            }
            tmp.purgeOnUse = true;
            this.queue.add(new CardQueueItem(tmp, mo, card.energyOnUse, true));

    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        this.queue.clear();
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(1);
    }

    public AbstractCard makeCopy() {
        return new DaCapo();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("DaCapo");
        NAME = DaCapo.cardStrings.NAME;
        DESCRIPTION = DaCapo.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = DaCapo.cardStrings.EXTENDED_DESCRIPTION;
    }
}
