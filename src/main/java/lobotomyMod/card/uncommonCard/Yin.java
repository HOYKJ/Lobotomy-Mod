package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.monster.YinMonster;
import lobotomyMod.power.YinYangPower;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.Yang;
import lobotomyMod.vfx.YinYangMergeEffect;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class Yin extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Yin";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public Yin() {
        super("Yin", Yin.NAME, Yin.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 102, 3, 2, CardTarget.SELF);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if((p.hasRelic(Yang.ID)) && (((Yang)p.getRelic(Yang.ID)).active)){
            AbstractDungeon.topLevelEffects.add(new YinYangMergeEffect());
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.drawPile));
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.hand));
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.discardPile));
            }));
            p.loseRelic(Yang.ID);
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
        }
    }

    @Override
    public void obtain() {
        super.obtain();
        LobotomyMod.hasYin = true;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 2;
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if((AbstractDungeon.player.hasRelic(Yang.ID)) && (((Yang)AbstractDungeon.player.getRelic(Yang.ID)).active)){
            this.counter --;
            if(this.counter <= 0) {
                change();
                ((Yang) AbstractDungeon.player.getRelic(Yang.ID)).change();
                AbstractDungeon.effectList.add(new LatterEffect(() -> {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
                }));
                AbstractDungeon.player.loseRelic(Yang.ID);
            }
        }
    }

    private void change(){
        AbstractMonster m = new YinMonster(Settings.WIDTH * 0.2F, 0.0F);
        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(m, false));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new YinYangPower(m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new ThornsPower(m, 4), 4));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new Yin();
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
        this.name = Yin.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Yin.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Yin");
        NAME = Yin.cardStrings.NAME;
        DESCRIPTION = Yin.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Yin.cardStrings.EXTENDED_DESCRIPTION;
    }
}
