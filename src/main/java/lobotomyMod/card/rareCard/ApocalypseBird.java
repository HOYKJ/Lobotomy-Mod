package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.*;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.animation.CurtainEffect;

/**
 * @author hoykj
 */
public class ApocalypseBird extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "ApocalypseBird";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public ApocalypseBird() {
        super("ApocalypseBird", ApocalypseBird.NAME, ApocalypseBird.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 63, 1, -2, CardTarget.NONE);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 0;
        this.isMultiDamage = true;
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
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 0;
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        this.counter ++;
        if(this.counter >= 5){
            this.applyPowers();
            this.counter = 0;
            for(int i = 0; i < this.multiDamage.length; i ++){
                this.multiDamage[i] = this.magicNumber;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
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
        return new ApocalypseBird();
    }

    @Override
    public void obtain() {
        super.obtain();
        UnlockTracker.unlockCard(this.cardID);
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
        this.name = ApocalypseBird.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = ApocalypseBird.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ApocalypseBird");
        NAME = ApocalypseBird.cardStrings.NAME;
        DESCRIPTION = ApocalypseBird.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ApocalypseBird.cardStrings.EXTENDED_DESCRIPTION;
    }
}
