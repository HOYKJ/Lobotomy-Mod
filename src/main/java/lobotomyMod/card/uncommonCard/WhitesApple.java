package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class WhitesApple extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "WhitesApple";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public WhitesApple() {
        super("WhitesApple", WhitesApple.NAME, WhitesApple.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 42, 2, -2);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
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
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            card.retain = true;
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card != this) {
                card.retain = true;
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            card.retain = true;
        }
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(CardLibrary.getCard("Parasite").makeCopy()));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new WhitesApple();
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
        this.name = WhitesApple.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = WhitesApple.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WhitesApple");
        NAME = WhitesApple.cardStrings.NAME;
        DESCRIPTION = WhitesApple.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = WhitesApple.cardStrings.EXTENDED_DESCRIPTION;
    }
}
