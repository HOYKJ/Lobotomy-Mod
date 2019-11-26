package lobotomyMod.character;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.rareCard.PlagueDoctor;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.card.uncommonCard.Yin;
import lobotomyMod.relic.toolAbnormality.AbstractLobotomyAbnRelic;
import lobotomyMod.relic.toolAbnormality.BackwardClock;

import static lobotomyMod.character.LobotomyHandler.*;

/**
 * @author hoykj
 */
public class LobotomyCardPool {
    public static CardGroup abnormalityPoolCommon = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup abnormalityPoolUncommon = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup abnormalityPoolRare = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup abnormalityPoolRelic = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static boolean reload;

    public static void addCardPool(){
        abnormalityPoolCommon.clear();
        abnormalityPoolUncommon.clear();
        abnormalityPoolRare.clear();
        abnormalityPoolRelic.clear();
        for (AbstractCard card : abnormalityListCommon) {
            abnormalityPoolCommon.addToBottom(card.makeCopy());
        }
        for (AbstractCard card : abnormalityListUncommon) {
            abnormalityPoolUncommon.addToBottom(card.makeCopy());
        }
        for (AbstractCard card : abnormalityListRare) {
            if((card instanceof PlagueDoctor) && (LobotomyMod.apostles >= 11)){
                return;
            }
            else if((card instanceof WhiteNight) && (LobotomyMod.apostles < 11)){
                return;
            }
            abnormalityPoolRare.addToBottom(card.makeCopy());
        }
        for (AbstractLobotomyAbnRelic relic : abnormalityListRelic) {
            abnormalityPoolRelic.addToBottom(relic.getCard());
        }
    }

    public static void reloadCardPool(){
        abnormalityPoolCommon.clear();
        abnormalityPoolUncommon.clear();
        abnormalityPoolRare.clear();
        abnormalityPoolRelic.clear();
        for (AbstractCard card : abnormalityListCommon) {
            if(AbstractDungeon.player.masterDeck.findCardById(card.cardID) != null){
                continue;
            }
            abnormalityPoolCommon.addToBottom(card.makeCopy());
        }
        for (AbstractCard card : abnormalityListUncommon) {
            if((card instanceof Yin) && (LobotomyMod.hasYin)){
                continue;
            }
            if(AbstractDungeon.player.masterDeck.findCardById(card.cardID) != null){
                continue;
            }
            abnormalityPoolUncommon.addToBottom(card.makeCopy());
        }
        for (AbstractCard card : abnormalityListRare) {
            if((card instanceof PlagueDoctor) && (LobotomyMod.apostles >= 11)){
                continue;
            }
            else if((card instanceof WhiteNight) && (LobotomyMod.apostles < 11)){
                continue;
            }
            if(AbstractDungeon.player.masterDeck.findCardById(card.cardID) != null){
                continue;
            }
            abnormalityPoolRare.addToBottom(card.makeCopy());
        }

        for (AbstractLobotomyAbnRelic relic : abnormalityListRelic) {
            if(AbstractDungeon.player.hasRelic(relic.relicId)){
                continue;
            }
            if((relic instanceof BackwardClock) && (LobotomyMod.hasBackward)){
                continue;
            }
            abnormalityPoolRelic.addToBottom(relic.getCard());
        }
    }

    public static void removeFromCardPool(AbstractLobotomyCard card){
        abnormalityPoolCommon.removeCard(card.cardID);
        abnormalityPoolUncommon.removeCard(card.cardID);
        abnormalityPoolRare.removeCard(card.cardID);
    }
}
