package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.HonorFeather;

/**
 * @author hoykj
 */
public class FieryBird extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "FieryBird";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private boolean used;

    public FieryBird() {
        super("FieryBird", FieryBird.NAME, FieryBird.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 101, 6, 0);
        this.baseDamage = 4;
        this.isMultiDamage = true;
        initInfo();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.damage += this.realCost * 6;
        if(AbstractDungeon.player.hasRelic(HonorFeather.ID)){
            this.damage *= 1.5F;
        }
        for(int i = 0; i < this.multiDamage.length; i ++){
            this.multiDamage[i] += this.realCost * 6;
            if(AbstractDungeon.player.hasRelic(HonorFeather.ID)){
                this.multiDamage[i] *= 1.5F;
            }
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.damage += this.realCost * 6;
        if(AbstractDungeon.player.hasRelic(HonorFeather.ID)){
            this.damage *= 1.5F;
        }
        for(int i = 0; i < this.multiDamage.length; i ++){
            this.multiDamage[i] += this.realCost * 6;
            if(AbstractDungeon.player.hasRelic(HonorFeather.ID)){
                this.multiDamage[i] *= 1.5F;
            }
        }
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        this.used = true;
        if(this.realCost >= 3){
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                this.changeCost(0);
            }));
            if(p.currentHealth < p.maxHealth / 4.0F){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, 5), 5));
            }
            if(!p.hasRelic(HonorFeather.ID)){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            if(p.hasRelic(HonorFeather.ID)){
                return;
            }
            p.maxHealth -= 20;
            if(p.maxHealth < 1){
                p.maxHealth = 1;
            }
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,  RelicLibrary.getRelic(HonorFeather.ID).makeCopy());
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                this.changeCost(this.realCost + 1);
            }));
            if((this.realCost == 2) || (p.currentHealth < p.maxHealth / 4.0F)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, 5), 5));
            }
            if(!p.hasRelic(HonorFeather.ID)){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(this.used){
            this.used = false;
        }
        else {
            if(this.realCost == 3){
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
            }
            if(this.realCost > 0) {
                this.changeCost(this.realCost - 1);
            }
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
        return new FieryBird();
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
        this.name = FieryBird.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = FieryBird.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FieryBird");
        NAME = FieryBird.cardStrings.NAME;
        DESCRIPTION = FieryBird.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = FieryBird.cardStrings.EXTENDED_DESCRIPTION;
    }
}
