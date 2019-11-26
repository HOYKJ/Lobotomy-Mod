package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.DiscardOtherAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class QueenOfHatred extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "QueenOfHatred";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public int counter;

    public QueenOfHatred() {
        super("QueenOfHatred", QueenOfHatred.NAME, QueenOfHatred.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, 4, 3, 1);
        this.baseDamage = 6;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
        initInfo();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.magicNumber = baseMagicNumber;
        if(this.realCost > 1){
            this.damage /= 2;
            this.magicNumber /= 2;
        }
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            if(this.realCost > 1){
                this.changeCost(this.realCost - 1);
            }
        }));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }

    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        if(card != this) {
            this.counter--;
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(this.counter > 0){
            this.changeCost(this.realCost + 1);
        }
        this.counter = 3;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if(this.realCost > 2){
            AbstractDungeon.actionManager.addToBottom(new DiscardOtherAction(this));
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
        return new QueenOfHatred();
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
            this.name = QueenOfHatred.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.name = QueenOfHatred.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = QueenOfHatred.EXTENDED_DESCRIPTION[this.infoStage + 1];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("QueenOfHatred");
        NAME = QueenOfHatred.cardStrings.NAME;
        DESCRIPTION = QueenOfHatred.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = QueenOfHatred.cardStrings.EXTENDED_DESCRIPTION;
    }
}
