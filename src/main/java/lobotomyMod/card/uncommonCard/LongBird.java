package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.commonCard.PunishingBird;
import lobotomyMod.card.rareCard.ApocalypseBird;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.event.ApocalypseBirdEvent;
import lobotomyMod.event.WhiteNightEvent;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class LongBird extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "LongBird";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public LongBird() {
        super("LongBird", LongBird.NAME, LongBird.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 62, 3, 1);
        this.baseDamage = 9;
        this.baseMagicNumber = 10;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    @Override
    public void atPreBattle() {
        super.atPreBattle();
        if(AbstractDungeon.player.masterDeck.findCardById(ApocalypseBird.ID) != null){
            return;
        }
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom() instanceof VictoryRoom){
            return;
        }
        if((AbstractDungeon.player.masterDeck.findCardById(PunishingBird.ID) != null) && (AbstractDungeon.player.masterDeck.findCardById(BigBird.ID) != null)){
            //AbstractDungeon.player.masterDeck.addToBottom(new ApocalypseBird());
            AbstractDungeon.topLevelEffects.clear();
            AbstractDungeon.effectList.clear();
            AbstractDungeon.currMapNode.room = new EventRoom();
            AbstractDungeon.getCurrRoom().event = new ApocalypseBirdEvent(CogitoBucket.level[63] > 0);
            AbstractDungeon.getCurrRoom().event.onEnterRoom();
            CardCrawlGame.fadeIn(1.5F);
            AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;
            AbstractDungeon.overlayMenu.hideCombatPanels();
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                AbstractDungeon.player.masterDeck.removeCard(this.cardID);
                AbstractDungeon.player.masterDeck.removeCard(PunishingBird.ID);
                AbstractDungeon.player.masterDeck.removeCard(BigBird.ID);
            }));
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int tmp = this.damage;
        this.damage = Math.max((int) ((float)mo.maxHealth * this.magicNumber / 100F), tmp);
        this.isDamageModified = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        int tmp = this.damage;
        this.damage = Math.max((int) ((float)m.maxHealth * this.magicNumber / 100F), tmp);
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                if((card.type == CardType.STATUS) || (card.type == CardType.CURSE)){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
                }
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
        return new LongBird();
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
        if(CogitoBucket.level[this.AbnormalityID] > 1){
            this.name = LongBird.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.name = LongBird.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = LongBird.EXTENDED_DESCRIPTION[this.infoStage + 1];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("LongBird");
        NAME = LongBird.cardStrings.NAME;
        DESCRIPTION = LongBird.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = LongBird.cardStrings.EXTENDED_DESCRIPTION;
    }
}
