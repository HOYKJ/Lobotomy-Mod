package lobotomyMod.vfx.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.card.ego.rare.ParadiseLost;
import lobotomyMod.helper.LobotomyUtils;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class CampfirePrayEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CampfirePrayEffect");
    public static final String[] TEXT = uiStrings.TEXT;
    private boolean openedScreen = false;
    private boolean cardChosen = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();
    private ParadiseLost paradiseLost;
    private CardGroup cards;

    public CampfirePrayEffect()
    {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update()
    {
        if (!AbstractDungeon.isScreenUp)
        {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }
        if ((this.duration < 1.0F) && (!this.openedScreen))
        {
            this.openedScreen = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if ((c instanceof ParadiseLost)) {
                    group.group.add(c);
                }
            }
            AbstractDungeon.gridSelectScreen.open(group, 1, TEXT[0], false, false, true, true);
            return;
        }
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !this.cardChosen)
        {
            this.paradiseLost = ((ParadiseLost)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard choice;
            for(int i = 0; i < 17; i ++) {
                if(this.paradiseLost.buffs[i] > 0){
                    continue;
                }
                choice = this.paradiseLost.makeStatEquivalentCopy();
                ((ParadiseLost)choice).tips.clear();
                choice.name = ParadiseLost.EXTENDED_DESCRIPTION[2 * i + 3];
                choice.rawDescription = ParadiseLost.EXTENDED_DESCRIPTION[2 * i + 4];
                ((ParadiseLost)choice).spID = i;
                choice.initializeDescription();
                this.cards.addToTop(choice);
            }
            choice = this.paradiseLost.makeStatEquivalentCopy();
            ((ParadiseLost)choice).tips.clear();
            choice.name = ParadiseLost.EXTENDED_DESCRIPTION[37];
            choice.rawDescription = ParadiseLost.EXTENDED_DESCRIPTION[38];
            ((ParadiseLost)choice).spID = 17;
            choice.initializeDescription();
            this.cards.addToTop(choice);

            LobotomyUtils.openCardRewardsScreen(getGroup(), false, TEXT[1]);
            this.cardChosen = true;
            return;
        }

        if (!AbstractDungeon.isScreenUp && AbstractDungeon.cardRewardScreen.codexCard != null) {
            AbstractCard c = AbstractDungeon.cardRewardScreen.codexCard;
            if(((ParadiseLost)c).spID == 17){
                this.paradiseLost.upgrade();
            }
            else {
                this.paradiseLost.buffs[((ParadiseLost) c).spID]++;
                this.paradiseLost.refresh();
            }
            AbstractDungeon.cardRewardScreen.codexCard = null;
        }

        if (this.duration < 0.0F)
        {
            this.isDone = true;
            if (CampfireUI.hidden)
            {
                com.megacrit.cardcrawl.rooms.AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
    }

    private void updateBlackScreenColor()
    {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }

    public void dispose() {}

    private ArrayList<AbstractCard> getGroup(){
        final ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < 3) {
            AbstractCard card = this.getRandomCard();
            if (!cards.contains(card)) {
                cards.add(card);
            }
        }
        return cards;
    }

    private AbstractCard getRandomCard(){
        return this.cards.getRandomCard(true);
    }
}
