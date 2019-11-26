package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.BlackArmyDerive;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.power.PinkArmyPower;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class BlackArmy extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BlackArmy";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public BlackArmy() {
        super("BlackArmy", BlackArmy.NAME, BlackArmy.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 106, 4, 0);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PinkArmyPower(p)));
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            this.changeCost(this.realCost + 1);
        }));
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        this.counter ++;
        if(this.counter >= 5){
            this.counter = 0;
            this.changeCost(this.realCost + 1);
        }
    }

    @Override
    public void changeCost(int cost) {
        super.changeCost(cost);
        if(this.realCost >= 3){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new BlackArmyDerive(), 4, true, false));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage * 1.15F;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new BlackArmy();
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
        if(CogitoBucket.level[this.AbnormalityID] > 2){
            this.name = BlackArmy.EXTENDED_DESCRIPTION[1];
            this.realRarity = CardRarity.RARE;
            this.rarity = CardRarity.RARE;
        }
        else {
            this.name = BlackArmy.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = BlackArmy.EXTENDED_DESCRIPTION[this.infoStage + 1];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlackArmy");
        NAME = BlackArmy.cardStrings.NAME;
        DESCRIPTION = BlackArmy.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BlackArmy.cardStrings.EXTENDED_DESCRIPTION;
    }
}
