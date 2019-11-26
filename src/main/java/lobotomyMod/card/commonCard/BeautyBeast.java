package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class BeautyBeast extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BeautyBeast";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BeautyBeast() {
        super("BeautyBeast", BeautyBeast.NAME, BeautyBeast.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 44, 3, 0);
        this.baseBlock = 6;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            this.exhaust = true;
            this.rawDescription += AbstractLobotomyCard.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            if(AbstractDungeon.player.drawPile.size() > 0){
                int i = AbstractDungeon.cardRng.random(AbstractDungeon.player.drawPile.size() - 1);
                AbstractDungeon.player.drawPile.group.set(i, this.makeCopy());
            }
            else if(AbstractDungeon.player.hand.size() > 1){
                int i = AbstractDungeon.cardRng.random(AbstractDungeon.player.hand.size() - 1);
                AbstractDungeon.player.hand.group.set(i, this.makeCopy());
            }
            else if(AbstractDungeon.player.discardPile.size() > 0){
                int i = AbstractDungeon.cardRng.random(AbstractDungeon.player.discardPile.size() - 1);
                AbstractDungeon.player.discardPile.group.set(i, this.makeCopy());
            }
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
        return new BeautyBeast();
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
        this.name = BeautyBeast.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = BeautyBeast.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BeautyBeast");
        NAME = BeautyBeast.cardStrings.NAME;
        DESCRIPTION = BeautyBeast.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BeautyBeast.cardStrings.EXTENDED_DESCRIPTION;
    }
}
