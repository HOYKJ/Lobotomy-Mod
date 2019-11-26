package lobotomyMod.patch.triggerPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.AbstractDeriveCard;
import lobotomyMod.card.ego.AbstractEgoCard;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class UsedCardPatch {
    @SpirePatch(
            clz= UseCardAction.class,
            method="update"
    )
    public static class updatePatch {
        @SpireInsertPatch(rloc=1)
        public static void Insert(UseCardAction _inst) throws NoSuchFieldException, IllegalAccessException {
            Field targetCard;
            targetCard = _inst.getClass().getDeclaredField("targetCard");
            targetCard.setAccessible(true);
            Field target;
            target = _inst.getClass().getDeclaredField("target");
            target.setAccessible(true);

            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), true);
                    ((AbstractLobotomyCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), true, (AbstractCreature) target.get(_inst));
                }
                else if(card instanceof AbstractDeriveCard){
                    ((AbstractDeriveCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), true, (AbstractCreature) target.get(_inst));
                }
                else if(card instanceof AbstractEgoCard){
                    ((AbstractEgoCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), true, (AbstractCreature) target.get(_inst));
                }
            }
            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false);
                    ((AbstractLobotomyCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false, (AbstractCreature) target.get(_inst));
                }
                else if(card instanceof AbstractDeriveCard){
                    ((AbstractDeriveCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false, (AbstractCreature) target.get(_inst));
                }
                else if(card instanceof AbstractEgoCard){
                    ((AbstractEgoCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false, (AbstractCreature) target.get(_inst));
                }
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractLobotomyCard) {
                    ((AbstractLobotomyCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false);
                    ((AbstractLobotomyCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false, (AbstractCreature) target.get(_inst));
                }
                else if(card instanceof AbstractDeriveCard){
                    ((AbstractDeriveCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false, (AbstractCreature) target.get(_inst));
                }
                else if(card instanceof AbstractEgoCard){
                    ((AbstractEgoCard) card).onUsedCard((AbstractCard) targetCard.get(_inst), false, (AbstractCreature) target.get(_inst));
                }
            }
        }
    }
}
