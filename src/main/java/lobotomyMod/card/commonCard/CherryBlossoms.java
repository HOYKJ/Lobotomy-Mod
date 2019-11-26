package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.ExhaustAnotherCardAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class CherryBlossoms extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "CherryBlossoms";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public CherryBlossoms() {
        super("CherryBlossoms", CherryBlossoms.NAME, CherryBlossoms.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 100, 2, 0);
        this.baseMagicNumber = 30;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        this.counter ++;
        if(this.counter == 3){
            for (int i = 0; i < 5; i ++) {
                ArrayList<CardGroup> list = new ArrayList<>();
                list.add(p.drawPile);
                list.add(p.hand);
                list.add(p.discardPile);
                CardGroup tmp1 = list.get(AbstractDungeon.cardRng.random(list.size() - 1));
                list.remove(tmp1);
                CardGroup tmp2 = list.get(AbstractDungeon.cardRng.random(list.size() - 1));
                list.remove(tmp2);
                CardGroup tmp3 = list.get(0);
                AbstractDungeon.actionManager.addToBottom(new ExhaustAnotherCardAction(tmp1, tmp2, tmp3, this));
            }
        }
        else if(this.counter == 4) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
            this.purgeOnUse = true;
            AbstractDungeon.player.masterDeck.removeCard(this.cardID);
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
        return new CherryBlossoms();
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
        this.name = CherryBlossoms.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = CherryBlossoms.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CherryBlossoms");
        NAME = CherryBlossoms.cardStrings.NAME;
        DESCRIPTION = CherryBlossoms.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CherryBlossoms.cardStrings.EXTENDED_DESCRIPTION;
    }
}
