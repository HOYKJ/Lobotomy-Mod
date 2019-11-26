package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.code.AbstractCodeCard;
import lobotomyMod.card.commonCard.MeatLantern;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.patch.MeatLanternField;

/**
 * @author hoykj
 */
public class EndTurnPatch {

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "applyEndOfTurnRelics"
    )
    public static class applyEndOfTurnRelics {
        @SpireInsertPatch(rloc=0)
        public static void Insert(AbstractRoom _inst){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).endOfTurn(false);
                }
                else if(card instanceof AbstractDeriveCard){
                    ((AbstractDeriveCard) card).endOfTurn(false);
                }
                else if(card instanceof AbstractCodeCard){
                    ((AbstractCodeCard) card).endOfTurn(false);
                }
                else if(card instanceof AbstractEgoCard){
                    ((AbstractEgoCard) card).endOfTurn(false);
                }
                if (MeatLanternField.hasLantern.get(card)) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, new MeatLantern().baseBlock));
                }
            }
            for(AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).endOfTurn(true);
                }
                else if(card instanceof AbstractDeriveCard){
                    ((AbstractDeriveCard) card).endOfTurn(true);
                }
                else if(card instanceof AbstractCodeCard){
                    ((AbstractCodeCard) card).endOfTurn(true);
                }
                else if(card instanceof AbstractEgoCard){
                    ((AbstractEgoCard) card).endOfTurn(true);
                }
                if (MeatLanternField.hasLantern.get(card)) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, new MeatLantern().baseBlock));
                }
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).endOfTurn(false);
                }
                else if(card instanceof AbstractDeriveCard){
                    ((AbstractDeriveCard) card).endOfTurn(false);
                }
                else if(card instanceof AbstractCodeCard){
                    ((AbstractCodeCard) card).endOfTurn(false);
                }
                else if(card instanceof AbstractEgoCard){
                    ((AbstractEgoCard) card).endOfTurn(false);
                }
                if (MeatLanternField.hasLantern.get(card)) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, new MeatLantern().baseBlock));
                }
            }
        }
    }
}
