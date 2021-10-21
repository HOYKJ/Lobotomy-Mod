package lobotomyMod.patch;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ScrapeAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.actions.unique.SkillFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StasisPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class HandSizePatch {
    @SpirePatch(cls="com.megacrit.cardcrawl.actions.common.DrawCardAction", method="update")
    public static class DrawCardActionFix
    {
        @SpireInsertPatch(rloc=29)
        public static SpireReturn Insert(DrawCardAction _inst)
                throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            int deckSize = AbstractDungeon.player.drawPile.size();

            Field f = DrawCardAction.class.getDeclaredField("shuffleCheck");
            f.setAccessible(true);
            boolean shuffleCheck = f.getBoolean(_inst);
            if (!shuffleCheck)
            {
                if (_inst.amount > deckSize)
                {
                    int tmp = _inst.amount - deckSize;
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, tmp));
                    AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                    if (deckSize != 0) {
                        AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, deckSize));
                    }
                    _inst.amount = 0;
                    _inst.isDone = true;
                }
                f.set(_inst, Boolean.TRUE);
            }
            f = SuperclassFinder.getSuperclassField(DrawCardAction.class, "duration");
            f.setAccessible(true);
            f.set(_inst, f.getFloat(_inst) - Gdx.graphics.getDeltaTime());
            if ((_inst.amount != 0) && (f.getFloat(_inst) < 0.0F))
            {
                if (Settings.FAST_MODE) {
                    f.set(_inst, Settings.ACTION_DUR_XFAST);
                } else {
                    f.set(_inst, Settings.ACTION_DUR_FASTER);
                }
                _inst.amount -= 1;
                if (!AbstractDungeon.player.drawPile.isEmpty())
                {
                    AbstractDungeon.player.draw();
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
                else
                {
                    _inst.isDone = true;
                }
                if (_inst.amount == 0) {
                    _inst.isDone = true;
                }
            }
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="draw", paramtypes={})
    public static class PlayerDrawFix
    {
        @SpireInsertPatch(rloc=0)
        public static SpireReturn Insert(AbstractPlayer _inst)
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
            _inst.draw(1);
            _inst.onCardDrawOrDiscard();
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction", method="update")
    public static class MakeTempCardInHandFix
    {
        @SpireInsertPatch(rloc=10)
        public static SpireReturn Insert(MakeTempCardInHandAction _inst)
                throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            Method m = MakeTempCardInHandAction.class.getDeclaredMethod("addToHand", Integer.TYPE);
            m.setAccessible(true);
            m.invoke(_inst, _inst.amount);
            if (_inst.amount > 0) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
            }
            _inst.isDone = true;
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.defect.ScrapeAction", method="update")
    public static class ScrapeActionFix
    {
        @SpireInsertPatch(rloc=20)
        public static SpireReturn Insert(ScrapeAction _inst)
                throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            int deckSize = AbstractDungeon.player.drawPile.size();

            Field f = ScrapeAction.class.getDeclaredField("shuffleCheck");
            f.setAccessible(true);
            boolean shuffleCheck = f.getBoolean(_inst);
            if (!shuffleCheck)
            {
                if (_inst.amount > deckSize)
                {
                    int tmp = _inst.amount - deckSize;
                    AbstractDungeon.actionManager.addToTop(new ScrapeAction(AbstractDungeon.player, tmp));
                    AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                    if (deckSize != 0) {
                        AbstractDungeon.actionManager.addToTop(new ScrapeAction(AbstractDungeon.player, deckSize));
                    }
                    _inst.amount = 0;
                    _inst.isDone = true;
                }
                f.set(_inst, Boolean.TRUE);
            }
            f = SuperclassFinder.getSuperclassField(ScrapeAction.class, "duration");
            f.setAccessible(true);
            f.set(_inst, f.getFloat(_inst) - Gdx.graphics.getDeltaTime());
            if ((_inst.amount != 0) && (f.getFloat(_inst) < 0.0F))
            {
                if (Settings.FAST_MODE) {
                    f.set(_inst, Settings.ACTION_DUR_XFAST);
                } else {
                    f.set(_inst, Settings.ACTION_DUR_FASTER);
                }
                _inst.amount -= 1;
                if (!AbstractDungeon.player.drawPile.isEmpty())
                {
                    ScrapeAction.scrapedCards.add(AbstractDungeon.player.drawPile.getTopCard());
                    AbstractDungeon.player.draw();
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
                else
                {
                    _inst.isDone = true;
                }
                if (_inst.amount == 0) {
                    _inst.isDone = true;
                }
            }
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction", method="update")
    public static class AttackFromDeckToHandActionFix1
    {
        @SpireInsertPatch(rloc=16, localvars={"card"})
        public static SpireReturn Insert(AttackFromDeckToHandAction _inst, AbstractCard card)
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            card.unhover();
            card.lighten(true);
            card.setAngle(0.0F);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.current_x = CardGroup.DRAW_PILE_X;
            card.current_y = CardGroup.DRAW_PILE_Y;
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.addToTop(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            _inst.isDone = true;
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction", method="update")
    public static class AttackFromDeckToHandActionFix2
    {
        @SpireInsertPatch(rloc=42)
        public static SpireReturn Insert(AttackFromDeckToHandAction _inst)
                throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                c.unhover();
                if ((AbstractDungeon.player.hand.size() == 10) && (!AbstractDungeon.player.hasRelic("ResearcherNote")))
                {
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                    AbstractDungeon.player.createHandIsFullDialog();
                }
                else
                {
                    AbstractDungeon.player.drawPile.removeCard(c);
                    AbstractDungeon.player.hand.addToTop(c);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
            Method m = SuperclassFinder.getSuperClassMethod(AttackFromDeckToHandAction.class, "tickDuration");
            m.setAccessible(true);
            m.invoke(_inst);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.SkillFromDeckToHandAction", method="update")
    public static class SkillFromDeckToHandActionFix1
    {
        @SpireInsertPatch(rloc=16, localvars={"card"})
        public static SpireReturn Insert(SkillFromDeckToHandAction _inst, AbstractCard card)
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            card.unhover();
            card.lighten(true);
            card.setAngle(0.0F);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.current_x = CardGroup.DRAW_PILE_X;
            card.current_y = CardGroup.DRAW_PILE_Y;
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.addToTop(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            _inst.isDone = true;
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.SkillFromDeckToHandAction", method="update")
    public static class SkillFromDeckToHandActionFix2
    {
        @SpireInsertPatch(rloc=42)
        public static SpireReturn Insert(SkillFromDeckToHandAction _inst)
                throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                c.unhover();
                if ((AbstractDungeon.player.hand.size() == 10) && (!AbstractDungeon.player.hasRelic("ResearcherNote")))
                {
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                    AbstractDungeon.player.createHandIsFullDialog();
                }
                else
                {
                    AbstractDungeon.player.drawPile.removeCard(c);
                    AbstractDungeon.player.hand.addToTop(c);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
            Method m = SuperclassFinder.getSuperClassMethod(SkillFromDeckToHandAction.class, "tickDuration");
            m.setAccessible(true);
            m.invoke(_inst);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.ExhumeAction", method="update")
    public static class ExhumeActionFix
    {
        @SpireInsertPatch(rloc=3, localvars={"upgrade", "exhumes"})
        public static SpireReturn Insert(ExhumeAction _inst, boolean upgrade, ArrayList<AbstractCard> exhumes)
                throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            if (AbstractDungeon.player.exhaustPile.isEmpty())
            {
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            if (AbstractDungeon.player.exhaustPile.size() == 1)
            {
                if (AbstractDungeon.player.exhaustPile.group.get(0).cardID.equals("Exhume"))
                {
                    _inst.isDone = true;
                    return SpireReturn.Return(null);
                }
                AbstractCard c = AbstractDungeon.player.exhaustPile.getTopCard();
                c.unfadeOut();
                AbstractDungeon.player.hand.addToHand(c);
                if ((AbstractDungeon.player.hasPower("Corruption")) && (c.type == AbstractCard.CardType.SKILL)) {
                    c.setCostForTurn(-9);
                }
                AbstractDungeon.player.exhaustPile.removeCard(c);
                if ((upgrade) && (c.canUpgrade())) {
                    c.upgrade();
                }
                c.unhover();
                c.fadingOut = false;
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            for (AbstractCard c1 : AbstractDungeon.player.exhaustPile.group)
            {
                c1.stopGlowing();
                c1.unhover();
                c1.unfadeOut();
            }
            for (AbstractCard c = (AbstractCard)AbstractDungeon.player.exhaustPile.group.iterator(); ((Iterator)c).hasNext();)
            {
                AbstractCard derp = (AbstractCard)((Iterator)c).next();
                if (derp.cardID.equals("Exhume"))
                {
                    ((Iterator)c).remove();
                    exhumes.add(derp);
                }
            }
            if (AbstractDungeon.player.exhaustPile.isEmpty())
            {
                AbstractDungeon.player.exhaustPile.group.addAll(exhumes);
                exhumes.clear();
                _inst.isDone = true;
                return SpireReturn.Return(null);
            }
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.exhaustPile, 1, ExhumeAction.TEXT[0], false);
            Method m = SuperclassFinder.getSuperClassMethod(ExhumeAction.class, "tickDuration");
            m.setAccessible(true);
            m.invoke(_inst);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction", method="update")
    public static class DrawPileToHandActionFix
    {
        @SpireInsertPatch(rloc=19, localvars={"tmp"})
        public static SpireReturn Insert(DrawPileToHandAction _inst, CardGroup tmp)
                throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
        {
            for (int i = 0; i < _inst.amount; i++) {
                if (!tmp.isEmpty())
                {
                    tmp.shuffle();
                    AbstractCard card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if ((AbstractDungeon.player.hand.size() == 10) && (!AbstractDungeon.player.hasRelic("ResearcherNote")))
                    {
                        AbstractDungeon.player.drawPile.moveToDiscardPile(card);
                        AbstractDungeon.player.createHandIsFullDialog();
                    }
                    else
                    {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        AbstractDungeon.player.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }
            _inst.isDone = true;
            Method m = SuperclassFinder.getSuperClassMethod(DrawPileToHandAction.class, "tickDuration");
            m.setAccessible(true);
            m.invoke(_inst);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.defect.SeekAction", method="update")
    public static class SeekActionFix1
    {
        @SpireInsertPatch(rloc=12, localvars={"card"})
        public static SpireReturn Insert(SeekAction _inst, AbstractCard card)
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            card.unhover();
            card.lighten(true);
            card.setAngle(0.0F);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.current_x = CardGroup.DRAW_PILE_X;
            card.current_y = CardGroup.DRAW_PILE_Y;
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.addToTop(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            _inst.isDone = true;
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.defect.SeekAction", method="update")
    public static class SeekActionFix2
    {
        @SpireInsertPatch(rloc=30, localvars={"tmp"})
        public static SpireReturn Insert(SeekAction _inst, CardGroup tmp)
        {
            for (int i = 0; i < tmp.size(); i++)
            {
                AbstractCard card = tmp.getNCardFromTop(i);
                if ((AbstractDungeon.player.hand.size() == 10) && (!AbstractDungeon.player.hasRelic("ResearcherNote")))
                {
                    AbstractDungeon.player.drawPile.moveToDiscardPile(card);
                    AbstractDungeon.player.createHandIsFullDialog();
                }
                else
                {
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    AbstractDungeon.player.drawPile.removeCard(card);
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
            }
            _inst.isDone = true;
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.defect.SeekAction", method="update")
    public static class SeekActionFix3
    {
        @SpireInsertPatch(rloc=65)
        public static SpireReturn Insert(SeekAction _inst)
                throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                c.unhover();
                if ((AbstractDungeon.player.hand.size() == 10) && (!AbstractDungeon.player.hasRelic("ResearcherNote")))
                {
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                    AbstractDungeon.player.createHandIsFullDialog();
                }
                else
                {
                    AbstractDungeon.player.drawPile.removeCard(c);
                    AbstractDungeon.player.hand.addToTop(c);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            Method m = SuperclassFinder.getSuperClassMethod(SeekAction.class, "tickDuration");
            m.setAccessible(true);
            m.invoke(_inst);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.DiscoveryAction", method="update")
    public static class DiscoveryActionFix
    {
        @SpireInsertPatch(rloc=20, localvars={"disCard"})
        public static SpireReturn Insert(DiscoveryAction _inst, AbstractCard disCard)
                throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
        {
            if (!AbstractDungeon.player.hasRelic("ResearcherNote")) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            disCard.setCostForTurn(0);
            AbstractDungeon.cardRewardScreen.discoveryCard = null;
            Field f = DiscoveryAction.class.getDeclaredField("retrieveCard");
            f.setAccessible(true);
            f.set(_inst, Boolean.TRUE);
            Method m = SuperclassFinder.getSuperClassMethod(DiscoveryAction.class, "tickDuration");
            m.setAccessible(true);
            m.invoke(_inst);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.powers.StasisPower", method="onDeath")
    public static class StasisPowerPatch
    {
        @SpireInsertPatch(rloc=3, localvars={"card"})
        public static SpireReturn Insert(StasisPower _inst, AbstractCard card)
        {
            if (AbstractDungeon.player.hasRelic("ResearcherNote"))
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1, false));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
