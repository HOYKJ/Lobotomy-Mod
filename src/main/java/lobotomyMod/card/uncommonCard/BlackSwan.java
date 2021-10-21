package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.Elijah;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class BlackSwan extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BlackSwan";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public boolean[] factor = {false, false, false, false, false};
    private int counter, counter2;

    public BlackSwan() {
        super("BlackSwan", BlackSwan.NAME, BlackSwan.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 70, 5, -2, CardTarget.NONE);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 3), 3));
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        if(this.counter >= 5){
            if(!this.factor[2]){
                this.factor[2] = true;
                this.baseMagicNumber --;
                this.magicNumber = this.baseMagicNumber;
                trigger();
            }
        }
        else {
            this.counter ++;
        }
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        if(!this.factor[0]){
            this.factor[0] = true;
            this.baseMagicNumber --;
            this.magicNumber = this.baseMagicNumber;
            trigger();
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(hand){
            if(!this.factor[1]){
                this.factor[1] = true;
                this.baseMagicNumber --;
                this.magicNumber = this.baseMagicNumber;
                trigger();
            }
        }

        for(AbstractPower power : AbstractDungeon.player.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF){
                if(!this.factor[3]){
                    this.factor[3] = true;
                    this.baseMagicNumber --;
                    this.magicNumber = this.baseMagicNumber;
                    trigger();
                }
                break;
            }
        }
    }

    @Override
    public void onLoseHP(int damageAmount, boolean hand) {
        super.onLoseHP(damageAmount, hand);
        if(this.counter2 >= 3){
            if(!this.factor[4]){
                this.factor[4] = true;
                this.baseMagicNumber --;
                this.magicNumber = this.baseMagicNumber;
                trigger();
            }
        }
        else {
            this.counter2 ++;
        }
    }

    private void trigger(){
        if(this.baseMagicNumber <= 0){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Elijah(), 1, true, false));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new BlackSwan();
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
        this.name = BlackSwan.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = BlackSwan.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlackSwan");
        NAME = BlackSwan.cardStrings.NAME;
        DESCRIPTION = BlackSwan.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BlackSwan.cardStrings.EXTENDED_DESCRIPTION;
    }
}
