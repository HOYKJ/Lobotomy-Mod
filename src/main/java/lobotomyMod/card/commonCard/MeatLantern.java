package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.MeatLanternField;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class MeatLantern extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "MeatLantern";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public MeatLantern() {
        super("MeatLantern", MeatLantern.NAME, MeatLantern.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 84, 2, 0);
        this.baseBlock = 5;
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
        AbstractDungeon.effectList.add(new LatterEffect(()->{
            AbstractDungeon.player.drawPile.removeCard(this);
            MeatLanternField.hasLantern.set(AbstractDungeon.player.drawPile.getRandomCard(true), true);
        }));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new MeatLantern();
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
        this.name = MeatLantern.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = MeatLantern.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MeatLantern");
        NAME = MeatLantern.cardStrings.NAME;
        DESCRIPTION = MeatLantern.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = MeatLantern.cardStrings.EXTENDED_DESCRIPTION;
    }
}
