package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.ExhaustOtherAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Porccubus extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Porccubus";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private boolean active;

    public Porccubus() {
        super("Porccubus", Porccubus.NAME, Porccubus.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 98, 3, -2);
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
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            if(AbstractDungeon.player.hand.size() > 6){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new VulnerablePower(AbstractDungeon.player, 1, false), 1));
            }
        }));
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(!hand){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new ExhaustOtherAction(this));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new Porccubus();
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
        this.name = Porccubus.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Porccubus.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Porccubus");
        NAME = Porccubus.cardStrings.NAME;
        DESCRIPTION = Porccubus.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Porccubus.cardStrings.EXTENDED_DESCRIPTION;
    }
}
