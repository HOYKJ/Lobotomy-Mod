package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.power.DeepSleep;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Wellcheers extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Wellcheers";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public int flavor;

    public Wellcheers() {
        super("Wellcheers", Wellcheers.NAME, Wellcheers.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 52, 3, 0, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        switch (this.flavor){
            case 0: case 3:
                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(p));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DeepSleep(p, 2)));
                AbstractDungeon.actionManager.addToBottom(new EndTurnAction());
                break;
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.flavor = AbstractDungeon.cardRng.random(3);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new Wellcheers();
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
        this.name = Wellcheers.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Wellcheers.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wellcheers");
        NAME = Wellcheers.cardStrings.NAME;
        DESCRIPTION = Wellcheers.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Wellcheers.cardStrings.EXTENDED_DESCRIPTION;
    }
}
