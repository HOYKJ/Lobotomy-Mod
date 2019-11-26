package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.SilentSonata;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.event.WhiteNightEvent;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class PlagueDoctor extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "PlagueDoctor";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public PlagueDoctor() {
        super("PlagueDoctor", PlagueDoctor.NAME, PlagueDoctor.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 45, 2, -2);
        this.baseMagicNumber = 1;
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
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
        LobotomyMod.apostles ++;
        if(LobotomyMod.apostles >= 11){
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
                return;
            }
//            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
//                if(AbstractDungeon.player.drawPile.contains(this)){
//                    AbstractDungeon.player.drawPile.removeCard(this);
//                }
//                if(AbstractDungeon.player.hand.contains(this)){
//                    AbstractDungeon.player.hand.removeCard(this);
//                }
//                if(AbstractDungeon.player.discardPile.contains(this)){
//                    AbstractDungeon.player.discardPile.removeCard(this);
//                }
//                AbstractDungeon.player.masterDeck.removeCard(this);
//                //AbstractDungeon.player.masterDeck.addToBottom(new WhiteNight());
//            }));
//            WhiteNight c = new WhiteNight();
//            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, false));
//            AbstractDungeon.actionManager.addToBottom(new LatterAction(c::atBattleStart));
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                if(AbstractDungeon.player.drawPile.contains(this)){
                    AbstractDungeon.player.drawPile.removeCard(this);
                }
                if(AbstractDungeon.player.hand.contains(this)){
                    AbstractDungeon.player.hand.removeCard(this);
                }
                if(AbstractDungeon.player.discardPile.contains(this)){
                    AbstractDungeon.player.discardPile.removeCard(this);
                }
                AbstractDungeon.player.masterDeck.removeCard(this.cardID);

                AbstractDungeon.currMapNode.room = new EventRoom();
                AbstractDungeon.getCurrRoom().event = new WhiteNightEvent(true);
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                CardCrawlGame.fadeIn(1.5F);
                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                AbstractDungeon.overlayMenu.hideCombatPanels();
            }, 0.1F));
            try {
                LobotomyMod.saveData();
            } catch (IOException e) {
                e.printStackTrace();
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
        return new PlagueDoctor();
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
        this.name = PlagueDoctor.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = PlagueDoctor.EXTENDED_DESCRIPTION[this.infoStage];
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

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        if(CogitoBucket.level[this.AbnormalityID] > 0) {
            return;
        }
        for (DescriptionLine tmp : this.description) {
            try {
                Field text = tmp.getClass().getDeclaredField("text");
                text.setAccessible(true);
                String str = (String) text.get(tmp);
                String updateTmp = "";
                updateTmp = updateTmp + "[#A700A7]";
                updateTmp = updateTmp + str;
                updateTmp = updateTmp + "[]";
                text.set(tmp, updateTmp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if(CogitoBucket.level[this.AbnormalityID] > 0) {
            return;
        }
        for (DescriptionLine tmp : this.description) {
            try {
                Field text = tmp.getClass().getDeclaredField("text");
                text.setAccessible(true);
                String str = (String) text.get(tmp);
                String updateTmp = "";
                updateTmp = updateTmp + "[#A700A7]";
                updateTmp = updateTmp + str;
                updateTmp = updateTmp + "[]";
                text.set(tmp, updateTmp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PlagueDoctor");
        NAME = PlagueDoctor.cardStrings.NAME;
        DESCRIPTION = PlagueDoctor.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = PlagueDoctor.cardStrings.EXTENDED_DESCRIPTION;
    }
}
