package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class HappyTeddy extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "HappyTeddy";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private CardType lastCard;

    public HappyTeddy() {
        super("HappyTeddy", HappyTeddy.NAME, HappyTeddy.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 6, 2, 0);
        this.baseDamage = 7;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        if(card instanceof AbstractLobotomyCard){
            return;
        }
        if(this.lastCard == null){
            this.lastCard = card.type;
        }
        else if(this.lastCard == card.type){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }
        else {
            this.lastCard = card.type;
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new HappyTeddy();
    }


    @Override
    public void unlockSuccess() {
        super.unlockSuccess();
        initInfo();
    }

    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        if(CogitoBucket.level[this.AbnormalityID] > 1){
            this.name = HappyTeddy.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.name = HappyTeddy.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = HappyTeddy.EXTENDED_DESCRIPTION[this.infoStage + 1];
        this.initializeDescription();
        super.initInfo();
    }

    @Override
    public void loadImg() {
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.textureImg = LobotomyHandler.lobotomyCardImage(this.cardID);
        loadCardImage(this.textureImg);
    }

    @Override
    public int[] onSave() {
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        return this.i;
    }

    @Override
    public void onLoad(int[] arg0)
    {
        if (arg0 == null) {
            return;
        }
        if(arg0[0] > 0){
            CogitoBucket.level[this.AbnormalityID] = arg0[0];
        }

        initInfo();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("HappyTeddy");
        NAME = HappyTeddy.cardStrings.NAME;
        DESCRIPTION = HappyTeddy.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = HappyTeddy.cardStrings.EXTENDED_DESCRIPTION;
    }
}
