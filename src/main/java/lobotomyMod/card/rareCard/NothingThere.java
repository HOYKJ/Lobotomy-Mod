package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class NothingThere extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "NothingThere";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private AbstractCard targetCard;

    public NothingThere() {
        super("NothingThere", NothingThere.NAME, NothingThere.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 20, 3, -2);
        this.targetCard = null;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToTop(new DamageAction(p, new DamageInfo(p, 30, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        this.atBattleStart();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, boolean hand) {
        super.onAttack(info, damageAmount, target, hand);
        if(target == AbstractDungeon.player){
            return;
        }
        if(info.owner == AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) (info.output * 0.2)));
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        getRandomCard();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            AbstractDungeon.player.drawPile.removeCard(this.targetCard);
            AbstractDungeon.player.hand.removeCard(this.targetCard);
            AbstractDungeon.player.discardPile.removeCard(this.targetCard);

            this.name = this.targetCard.name;
            this.initializeTitle();
            this.rawDescription = this.targetCard.rawDescription;
            this.changeCost(this.targetCard.cost);
            this.cost = this.targetCard.cost;
            this.costForTurn = this.targetCard.costForTurn;
            this.portrait = this.targetCard.portrait;
            this.type = this.targetCard.type;
            this.color = this.targetCard.color;
            this.rarity = this.targetCard.rarity;
            this.target = this.targetCard.target;
            this.upgraded = this.targetCard.upgraded;
            this.timesUpgraded = this.targetCard.timesUpgraded;
            this.baseDamage = this.targetCard.baseDamage;
            this.baseBlock = this.targetCard.baseBlock;
            this.baseMagicNumber = this.targetCard.baseMagicNumber;
            this.isCostModified = this.targetCard.isCostModified;
            this.isCostModifiedForTurn = this.targetCard.isCostModifiedForTurn;
            this.inBottleLightning = this.targetCard.inBottleLightning;
            this.inBottleFlame = this.targetCard.inBottleFlame;
            this.inBottleTornado = this.targetCard.inBottleTornado;
            this.isSeen = this.targetCard.isSeen;
            this.isLocked = this.targetCard.isLocked;
            this.misc = this.targetCard.misc;
            this.freeToPlayOnce = this.targetCard.freeToPlayOnce;
            this.initializeDescription();
        }, 0F));
    }

    @Override
    public void onShuffle() {
        super.onShuffle();
        this.atBattleStart();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.targetCard = null;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new NothingThere();
    }

    private void getRandomCard(){
//        if(AbstractDungeon.player.drawPile.contains(this)){
//            if(AbstractDungeon.player.drawPile.group.size() > 1) {
//                this.targetCard = AbstractDungeon.player.drawPile.getRandomCard(true);
//                while (this.targetCard == this){
//                    this.targetCard = AbstractDungeon.player.drawPile.getRandomCard(true);
//                }
//            }
//            else if(AbstractDungeon.player.hand.group.size() > 0){
//                this.targetCard = AbstractDungeon.player.hand.getRandomCard(true);
//            }
//            else if(AbstractDungeon.player.discardPile.group.size() > 0){
//                this.targetCard = AbstractDungeon.player.discardPile.getRandomCard(true);
//            }
//        }
//        else if(AbstractDungeon.player.hand.contains(this)){
//            if(AbstractDungeon.player.hand.group.size() > 1) {
//                this.targetCard = AbstractDungeon.player.hand.getRandomCard(true);
//                while (this.targetCard == this){
//                    this.targetCard = AbstractDungeon.player.hand.getRandomCard(true);
//                }
//            }
//            else if(AbstractDungeon.player.drawPile.group.size() > 0){
//                this.targetCard = AbstractDungeon.player.drawPile.getRandomCard(true);
//            }
//            else if(AbstractDungeon.player.discardPile.group.size() > 0){
//                this.targetCard = AbstractDungeon.player.discardPile.getRandomCard(true);
//            }
//        }
//        else if(AbstractDungeon.player.discardPile.contains(this)){
//            if(AbstractDungeon.player.discardPile.group.size() > 1) {
//                this.targetCard = AbstractDungeon.player.discardPile.getRandomCard(true);
//                while (this.targetCard == this){
//                    this.targetCard = AbstractDungeon.player.discardPile.getRandomCard(true);
//                }
//            }
//            else if(AbstractDungeon.player.drawPile.group.size() > 0){
//                this.targetCard = AbstractDungeon.player.drawPile.getRandomCard(true);
//            }
//            else if(AbstractDungeon.player.hand.group.size() > 0){
//                this.targetCard = AbstractDungeon.player.hand.getRandomCard(true);
//            }
//        }
        ArrayList<AbstractCard> draw = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        ArrayList<AbstractCard> hand = new ArrayList<>(AbstractDungeon.player.hand.group);
        ArrayList<AbstractCard> discard = new ArrayList<>(AbstractDungeon.player.discardPile.group);
        ArrayList<AbstractCard> rem = new ArrayList<>();
        int code = 0;

        for(AbstractCard card : draw){
            if(draw.contains(this)){
                code = 1;
            }
            if(card instanceof AbstractLobotomyCard){
                rem.add(card);
            }
        }
        for(AbstractCard card : rem){
            draw.remove(card);
        }
        rem.clear();
        for(AbstractCard card : hand){
            if(draw.contains(this)){
                code = 2;
            }
            if(card instanceof AbstractLobotomyCard){
                rem.add(card);
            }
        }
        for(AbstractCard card : rem){
            hand.remove(card);
        }
        rem.clear();
        for(AbstractCard card : discard){
            if(draw.contains(this)){
                code = 3;
            }
            if(card instanceof AbstractLobotomyCard){
                rem.add(card);
            }
        }
        for(AbstractCard card : rem){
            discard.remove(card);
        }
        rem.clear();

//        switch (code){
//            case 0: case 1:
//                if(!draw.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(draw.size()));
//                }
//                else if(!hand.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(hand.size()));
//                }
//                else if(!discard.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(discard.size()));
//                }
//                break;
//            case 2:
//                if(!hand.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(hand.size()));
//                }
//                else if(!discard.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(discard.size()));
//                }
//                else if(!draw.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(draw.size()));
//                }
//                break;
//            case 3:
//                if(!discard.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(discard.size()));
//                }
//                else if(!draw.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(draw.size()));
//                }
//                else if(!hand.isEmpty()) {
//                    this.targetCard = draw.get(AbstractDungeon.cardRng.random(hand.size()));
//                }
//                break;
//        }

        if(!draw.isEmpty()) {
            this.targetCard = draw.get(AbstractDungeon.cardRng.random(draw.size() - 1));
        }
        else if(!discard.isEmpty()) {
            this.targetCard = discard.get(AbstractDungeon.cardRng.random(discard.size() - 1));
        }
        else if(!hand.isEmpty()) {
            this.targetCard = hand.get(AbstractDungeon.cardRng.random(hand.size() - 1));
        }
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
        if(this.targetCard != null){
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = NothingThere.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = NothingThere.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("NothingThere");
        NAME = NothingThere.cardStrings.NAME;
        DESCRIPTION = NothingThere.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = NothingThere.cardStrings.EXTENDED_DESCRIPTION;
    }
}
