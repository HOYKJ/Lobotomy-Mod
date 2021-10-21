package lobotomyMod.card;

import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import basemod.helpers.SuperclassFinder;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.deriveCard.Duel;
import lobotomyMod.character.LobotomyCardPool;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.AbstractCardEnum;
import lobotomyMod.relic.CogitoBucket;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public abstract class AbstractLobotomyCard extends CustomCard{
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    protected int [] i = {0};
    public int realCost;
    public int AbnormalityID;
    public int infoStage = 1;
    public int maxInfo;
    public CardRarity realRarity;
    public boolean canActivated;
    public CardTarget realTarget;

    public AbstractLobotomyCard(final String id, final String name, final String description, final CardRarity rarity, final CardTarget target, final int AbnormalityID, final int maxInfo, final int realCost) {
        super(id, name, LobotomyHandler.lobotomyLockCardImage(), -1, description, CardType.SKILL, AbstractCardEnum.Lobotomy, CardRarity.SPECIAL, target);
        this.AbnormalityID = AbnormalityID;
        this.maxInfo = maxInfo;
        this.realCost = realCost;
        this.realRarity = rarity;
        this.canActivated = false;
        this.realTarget = target;
    }

    public AbstractLobotomyCard(final String id, final String name, final String description, final CardRarity rarity, final CardTarget target, final int AbnormalityID, final int maxInfo, final int realCost, final CardTarget realTarget) {
        super(id, name, LobotomyHandler.lobotomyLockCardImage(), -1, description, CardType.SKILL, AbstractCardEnum.Lobotomy, CardRarity.SPECIAL, target);
        this.AbnormalityID = AbnormalityID;
        this.maxInfo = maxInfo;
        this.realCost = realCost;
        this.realRarity = rarity;
        this.canActivated = false;
        this.realTarget = realTarget;
    }

    public void obtain(){
        CardCrawlGame.sound.play("DoorClick");
//        LobotomyCardPool.reload = true;
//        LobotomyCardPool.removeFromCardPool(this);
    }

    public void atPreBattle(){}

    public void atBattleStart(){}

    public void onUsedCard(AbstractCard card, boolean hand){}

    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target){}

    public void endOfTurn(boolean hand){}

    public void ExhaustCard(AbstractCard card, boolean hand){}

    public void onVictory(){}

    public void onShuffle(){}

    public void onMonsterDeath(AbstractMonster monster){}

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, boolean hand){}

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount, boolean hand)
    {
        return damageAmount;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type){
        return damage;
    }

    public void onLoseHP(int damageAmount, boolean hand){}

    public void onEnterRoom(AbstractRoom room){}

    public void loadImg(){}

    public void initInfo(){
        this.cost = this.realCost;
        this.costForTurn = this.realCost;
        this.rarity = this.realRarity;
        this.target = this.realTarget;
    }

    public void changeCost(int cost){
        this.realCost = cost;
        if(this.cost != -1) {
            this.cost = this.realCost;
            this.costForTurn = this.realCost;
        }
    }

    public void changeCostForTurn(int cost){
        if(this.cost != -1) {
            this.costForTurn = cost;
        }
    }

    public boolean canUnlockInfo(){
        if (AbstractDungeon.player == null){
            return false;
        }
        if(!(AbstractDungeon.player.hasRelic(CogitoBucket.ID))){
            return false;
        }
        int unlockCost = 0;
        switch (this.realRarity){
            case COMMON:
                unlockCost = 30;
                break;
            case UNCOMMON:
                unlockCost = 60;
                break;
            case RARE:
                unlockCost = 90;
                break;
        }
        return AbstractDungeon.player.getRelic(CogitoBucket.ID).counter >= unlockCost;
    }

    public void unlockInfo(){
        int unlockCost = 0;
        switch (this.realRarity){
            case COMMON:
                unlockCost = 30;
                break;
            case UNCOMMON:
                unlockCost = 60;
                break;
            case RARE:
                unlockCost = 90;
                break;
        }
        if(AbstractDungeon.player.getRelic(CogitoBucket.ID).counter < unlockCost){
            return;
        }
        AbstractDungeon.player.getRelic(CogitoBucket.ID).counter -= unlockCost;
        CogitoBucket.level[this.AbnormalityID] += 1;
        this.unlockSuccess();
        for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
            if((card instanceof AbstractLobotomyCard) && (card.cardID.equals(this.cardID))){
                ((AbstractLobotomyCard) card).unlockSuccess();
            }
        }
    }

    protected void unlockSuccess(){
        LobotomyMod.logger.info("level: " + CogitoBucket.level[this.AbnormalityID]);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("AbstractLobotomyCard");
        NAME = AbstractLobotomyCard.cardStrings.NAME;
        DESCRIPTION = AbstractLobotomyCard.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = AbstractLobotomyCard.cardStrings.EXTENDED_DESCRIPTION;
    }
}
