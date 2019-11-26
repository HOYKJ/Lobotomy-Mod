package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class LaLunaPerformance extends AbstractGameEffect {
    private boolean spe;
    private float timer, startTimer;

    public LaLunaPerformance(boolean spe){
        this.spe = spe;
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        if(spe){
            this.duration = 110;
            if(Settings.FAST_MODE){
                this.timer = 3.0F;
            }
            else {
                this.timer = 5.0F;
            }
            this.startTimer = this.timer;
        }
        else {
            this.duration = 59;
        }
        this.startingDuration = this.duration;
    }

    public void update(){
        if(this.duration == this.startingDuration){
            if(this.spe) {
                CardCrawlGame.music.playTempBgmInstantly("Moon_Piano_Escape.mp3", false);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2), 2));
            }
            else {
                CardCrawlGame.music.playTempBgmInstantly("Moon_Piano.mp3", false);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            this.isDone = true;
            CardCrawlGame.music.unsilenceBGM();
            if(this.spe) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -2), -2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -2), -2));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -1), -1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -1), -1));
            }
        }

        if(this.spe){
            this.timer -= Gdx.graphics.getDeltaTime();
            if(this.timer <= 0){
                this.timer += this.startTimer;
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 2)));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    public void dispose(){}
}
