package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.power.InspiredPower;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class CrumblingArmor extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "CrumblingArmor";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CrumblingArmor() {
        super("CrumblingArmor", CrumblingArmor.NAME, CrumblingArmor.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 61, 4, 1, CardTarget.SELF);
        this.baseBlock = 6;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
        initInfo();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(InspiredPower.POWER_ID)){
            this.block += AbstractDungeon.player.getPower(InspiredPower.POWER_ID).amount * 3;
            this.isBlockModified = true;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(AbstractDungeon.player.hasPower(InspiredPower.POWER_ID)){
            this.block += AbstractDungeon.player.getPower(InspiredPower.POWER_ID).amount * 3;
            this.isBlockModified = true;
        }
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        if(AbstractDungeon.player.hasPower(InspiredPower.POWER_ID)) {
            AbstractDungeon.player.getPower(InspiredPower.POWER_ID).stackPower(1);
        }
        else {
            AbstractDungeon.player.powers.add(new InspiredPower(AbstractDungeon.player, 1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this, 1, true, false));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new CrumblingArmor();
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
        this.name = CrumblingArmor.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = CrumblingArmor.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CrumblingArmor");
        NAME = CrumblingArmor.cardStrings.NAME;
        DESCRIPTION = CrumblingArmor.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CrumblingArmor.cardStrings.EXTENDED_DESCRIPTION;
    }
}
