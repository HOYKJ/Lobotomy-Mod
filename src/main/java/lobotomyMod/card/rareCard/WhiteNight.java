package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.Apostles.*;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.DeathAngel;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class WhiteNight extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "WhiteNight";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static boolean canHeal = false;
    public static boolean active;

    public WhiteNight() {
        super("WhiteNight", WhiteNight.NAME, WhiteNight.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 46, 5, 2, CardTarget.NONE);
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
        if(AbstractDungeon.player.hasRelic(DeathAngel.ID)){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 16));
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        active = true;
        canHeal = false;
//        CardCrawlGame.music.silenceTempBgmInstantly();
//        CardCrawlGame.music.silenceBGMInstantly();
        //AbstractDungeon.getCurrRoom().playBgmInstantly("Lucifer_standbg0.mp3");
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new GuardApostle());
        list.add(new GuardApostle());
        list.add(new ScytheApostle());
        list.add(new ScytheApostle());
        list.add(new WandApostle());
        list.add(new WandApostle());
        list.add(new WandApostle());
        list.add(new SpearApostle());
        list.add(new SpearApostle());
        list.add(new SpearApostle());
        list.add(new SpearApostle());
        for(int i = 0; i < 3; i ++){
            int roll = AbstractDungeon.cardRng.random(list.size() - 1);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(list.get(roll), 1, true, false));
            list.remove(roll);
        }

        for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
            if(card instanceof HereticApostle){
                return;
            }
        }
        if(AbstractDungeon.player.masterDeck.size() > 1) {
            int i = AbstractDungeon.cardRng.random(AbstractDungeon.player.masterDeck.size() - 1);
            while (AbstractDungeon.player.masterDeck.group.get(i) == this){
                i = AbstractDungeon.cardRng.random(AbstractDungeon.player.masterDeck.size() - 1);
            }
            for(int i1 = 0; i1 < AbstractDungeon.player.drawPile.size(); i1 ++){
                if(AbstractDungeon.player.drawPile.group.get(i1).uuid.equals(AbstractDungeon.player.masterDeck.group.get(i).uuid)){
                    AbstractDungeon.player.drawPile.group.set(i1, new HereticApostle());
                }
            }
            AbstractDungeon.player.masterDeck.group.set(i, new HereticApostle());
        }
        else {
            AbstractDungeon.player.masterDeck.group.add(new HereticApostle());
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HereticApostle(), 1, true, false));
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if((hand) && (active)){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 4));
            for(AbstractCard card : AbstractDungeon.player.exhaustPile.group){
                if(card instanceof AbstractApostleCard){
                    AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                        AbstractDungeon.player.discardPile.addToRandomSpot(card.makeCopy());
                        AbstractDungeon.player.exhaustPile.removeCard(card);
                    }));
                }
            }
        }
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
//        if(!active){
//            return;
//        }
        if((card instanceof AbstractApostleCard) || (card == this)){
            return;
        }
        canHeal = true;
        AbstractDungeon.player.heal(this.magicNumber);
        canHeal = false;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, boolean hand) {
        super.onAttack(info, damageAmount, target, hand);
//        if(!active){
//            return;
//        }
        canHeal = true;
        AbstractDungeon.player.heal(1);
        canHeal = false;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount, boolean hand) {
        if((info.type == DamageInfo.DamageType.HP_LOSS)){
            return super.onAttackedToChangeDamage(info, damageAmount, hand);
        }
        if(damageAmount < (AbstractDungeon.player.hasRelic(DeathAngel.ID)? 11: 6)){
            return 0;
        }
        return super.onAttackedToChangeDamage(info, damageAmount, hand);
    }

    public void die(){
        active = false;
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        CardCrawlGame.music.unsilenceBGM();
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof AbstractApostleCard){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof AbstractApostleCard){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof AbstractApostleCard){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            }
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        active = true;
        canHeal = false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new WhiteNight();
    }

    @Override
    public void obtain() {
        super.obtain();
        UnlockTracker.unlockCard(this.cardID);
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
        this.name = WhiteNight.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = WhiteNight.EXTENDED_DESCRIPTION[this.infoStage];
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
                updateTmp = updateTmp + "[#FFFFA1]";
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
                updateTmp = updateTmp + "[#FFFFA1]";
                updateTmp = updateTmp + str;
                updateTmp = updateTmp + "[]";
                text.set(tmp, updateTmp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WhiteNight");
        NAME = WhiteNight.cardStrings.NAME;
        DESCRIPTION = WhiteNight.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = WhiteNight.cardStrings.EXTENDED_DESCRIPTION;
    }
}
