package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.LaLunaPerformance;

/**
 * @author hoykj
 */
public class LaLuna extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "LaLuna";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private LaLunaPerformance effect;
    private int counter;

    public LaLuna() {
        super("LaLuna", LaLuna.NAME, LaLuna.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 105, 3, -2, CardTarget.NONE);
        initInfo();
        this.counter = 0;
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
        if((this.effect == null) || (this.effect.isDone)) {
            this.counter++;
            if (this.counter >= 3) {
                this.counter = 0;
                this.effect = new LaLunaPerformance(true);
                AbstractDungeon.effectList.add(this.effect);

            } else {
                this.effect = new LaLunaPerformance(false);
                AbstractDungeon.effectList.add(this.effect);
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
        return new LaLuna();
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
        this.name = LaLuna.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = LaLuna.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("LaLuna");
        NAME = LaLuna.cardStrings.NAME;
        DESCRIPTION = LaLuna.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = LaLuna.cardStrings.EXTENDED_DESCRIPTION;
    }
}
