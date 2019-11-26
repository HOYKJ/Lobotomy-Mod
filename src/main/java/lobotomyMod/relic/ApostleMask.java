package lobotomyMod.relic;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.deriveCard.ApostleCardGroup.*;
import lobotomyMod.character.LobotomyHandler;

/**
 * @author hoykj
 */
public class ApostleMask extends AbstractLobotomyRelic{
    public static final String ID = "ApostleMask";
    public static boolean heal;

    public ApostleMask()
    {
        super("ApostleMask",  RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        super.obtain();
        int i = MathUtils.random(10);
        if(i != 0) {
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("ApostleMask_" + i));
            this.outlineImg = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicOutlineImage("ApostleMask_" + i));
        }
        this.counter = 0;
        heal = false;
        AbstractDungeon.player.masterDeck.clear();
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_SoulSlash());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Slash());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Slash());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Execute());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Beam());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Beam());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Beam());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Charge());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Charge());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Charge());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Charge());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_SoulSlash());
        AbstractDungeon.player.masterDeck.addToBottom(new Apo_Pray());
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if(AbstractDungeon.player.masterDeck.findCardById(Apo_Pray.ID) != null){
            return;
        }
        this.counter ++;
        if(this.counter >= 6){
            this.counter = 0;
            AbstractDungeon.player.masterDeck.addToBottom(new Apo_Pray());
        }
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if(heal) {
            heal = false;
            return super.onPlayerHeal(healAmount);
        }
        return 0;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new ApostleMask();
    }
}
