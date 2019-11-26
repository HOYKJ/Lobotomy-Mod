package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.card.commonCard.Wellcheers;
import lobotomyMod.card.deriveCard.Apostles.AbstractApostleCard;
import lobotomyMod.card.deriveCard.NestCard;
import lobotomyMod.card.uncommonCard.FieryBird;
import lobotomyMod.card.uncommonCard.LittlePrince;
import lobotomyMod.card.uncommonCard.NakedNest;
import lobotomyMod.card.uncommonCard.QueenBee;
import lobotomyMod.power.DeepSleep;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class CardGlowPatch {

    @SpirePatch(cls="com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder", method="<ctor>")
    public static class colorFix
    {
        public static void Postfix(CardGlowBorder _inst, AbstractCard card) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
        {
            AbstractPlayer p = AbstractDungeon.player;
            Field f = SuperclassFinder.getSuperclassField(CardGlowBorder.class, "color");
            f.setAccessible(true);

            if(card instanceof Wellcheers){
                switch (((Wellcheers) card).flavor){
                    case 0: default:
                        f.set(_inst, Color.RED.cpy());
                        break;
                    case 1:
                        f.set(_inst, Color.BLUE.cpy());
                        break;
                    case 2:
                        f.set(_inst, Color.PURPLE.cpy());
                        break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if((c instanceof QueenBee) && (((QueenBee) c).list.contains(card))){
                    f.set(_inst, Color.YELLOW.cpy());
                    break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if((c instanceof QueenBee) && (((QueenBee) c).list.contains(card))){
                    f.set(_inst, Color.YELLOW.cpy());
                    break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.discardPile.group){
                if((c instanceof QueenBee) && (((QueenBee) c).list.contains(card))){
                    f.set(_inst, Color.YELLOW.cpy());
                    break;
                }
            }

            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if((c instanceof LittlePrince) && (((LittlePrince) c).prince.contains(card))){
                    f.set(_inst, Color.BLUE.cpy());
                    break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if((c instanceof LittlePrince) && (((LittlePrince) c).prince.contains(card))){
                    f.set(_inst, Color.BLUE.cpy());
                    break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.discardPile.group){
                if((c instanceof LittlePrince) && (((LittlePrince) c).prince.contains(card))){
                    f.set(_inst, Color.BLUE.cpy());
                    break;
                }
            }

            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if((c instanceof NakedNest) && (((NakedNest) c).targets.contains(card))){
                    f.set(_inst, Color.GREEN.cpy());
                    break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if((c instanceof NakedNest) && (((NakedNest) c).targets.contains(card))){
                    f.set(_inst, Color.GREEN.cpy());
                    break;
                }
            }
            for(AbstractCard c : AbstractDungeon.player.discardPile.group){
                if((c instanceof NakedNest) && (((NakedNest) c).targets.contains(card))){
                    f.set(_inst, Color.GREEN.cpy());
                    break;
                }
            }
            if(card instanceof NestCard){
                f.set(_inst, Color.GREEN.cpy());
            }

            if((card instanceof FieryBird) && (((FieryBird) card).realCost == 3)){
                f.set(_inst, Color.YELLOW.cpy());
            }
        }
    }
}
