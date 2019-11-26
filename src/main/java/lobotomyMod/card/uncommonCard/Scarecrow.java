package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Scarecrow extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Scarecrow";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Scarecrow() {
        super("Scarecrow", Scarecrow.NAME, Scarecrow.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 87, 2, -2);
        this.baseBlock = 6;
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
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        if(card.type == CardType.POWER){
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 4));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.applyPowers();
        if(AbstractDungeon.player.drawPile.getPowers().size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getPowers().getRandomCard(true), AbstractDungeon.player.drawPile));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
        else if(AbstractDungeon.player.hand.getPowers().size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.hand.getPowers().getRandomCard(true), AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
        else if(AbstractDungeon.player.discardPile.getPowers().size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getPowers().getRandomCard(true), AbstractDungeon.player.discardPile));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new Scarecrow();
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
        this.name = Scarecrow.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Scarecrow.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Scarecrow");
        NAME = Scarecrow.cardStrings.NAME;
        DESCRIPTION = Scarecrow.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Scarecrow.cardStrings.EXTENDED_DESCRIPTION;
    }
}
