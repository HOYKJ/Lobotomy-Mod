package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Scorched extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Scorched";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Scorched() {
        super("Scorched", Scorched.NAME, Scorched.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 2, 2, 2);
        this.baseDamage = 20;
        this.isMultiDamage = true;
        this.isEthereal = true;
        initInfo();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        for(int i = 0; i < this.multiDamage.length; i ++){
            this.multiDamage[i] += AbstractDungeon.player.hand.size();
            this.damage = this.multiDamage[0];
            if (this.baseDamage != this.multiDamage[i]) {
                this.isDamageModified = true;
            }
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        for(int i = 0; i < this.multiDamage.length; i ++){
            this.multiDamage[i] += AbstractDungeon.player.hand.size();
            this.damage = this.multiDamage[0];
            if (this.baseDamage != this.multiDamage[i]) {
                this.isDamageModified = true;
            }
        }
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 30, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new Scorched();
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
        this.name = Scorched.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Scorched.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Scorched");
        NAME = Scorched.cardStrings.NAME;
        DESCRIPTION = Scorched.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Scorched.cardStrings.EXTENDED_DESCRIPTION;
    }
}
