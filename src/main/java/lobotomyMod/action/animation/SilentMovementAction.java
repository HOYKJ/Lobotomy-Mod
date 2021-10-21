package lobotomyMod.action.animation;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.animation.CurtainEffect;
import lobotomyMod.vfx.animation.MovementWord;

/**
 * @author hoykj
 */
public class SilentMovementAction extends AbstractGameAction {
    private int movement;
    private boolean did;

    public SilentMovementAction(int movement){
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        this.movement = movement;
        switch (this.movement){
            case 1: case 5:
                this.duration = 10;
                break;
            case 2: case 3: case 4:
                this.duration = 4;
                break;
        }
        this.startDuration = this.duration;
        this.did = false;
    }

    public void update(){

        switch (this.movement){
            case 1:
                if(this.duration == this.startDuration) {
                    AbstractDungeon.topLevelEffects.add(new CurtainEffect());
                    CardCrawlGame.sound.play("movement_clap");
                }
                this.duration -= Gdx.graphics.getDeltaTime();
                if(this.duration <= this.startDuration - 6) {
                    if(!this.did) {
                        AbstractDungeon.topLevelEffects.add(new MovementWord(1));
                        CardCrawlGame.sound.play("movement_1");
                        this.did = true;
                    }
                }
                if(this.duration < 0){
                    this.isDone = true;
                }
                break;
            case 2: case 3: case 4:
                if(this.duration == this.startDuration) {
                    AbstractDungeon.topLevelEffects.add(new MovementWord(this.movement));
                    CardCrawlGame.sound.play("movement_" + this.movement);
                }
                this.duration -= Gdx.graphics.getDeltaTime();
                if(this.duration < 0){
                    this.isDone = true;
                }
                break;
            case 5:
                if(this.duration == this.startDuration) {
                    AbstractDungeon.topLevelEffects.add(new MovementWord(5));
                    CardCrawlGame.sound.play("movement_final");
                    AbstractDungeon.effectList.add(new LatterEffect(()->{
                        CardCrawlGame.music.unsilenceBGM();
                    }, 32.0F));
                }
                this.duration -= Gdx.graphics.getDeltaTime();
                if(this.duration <= this.startDuration - 4) {
                    if(!this.did) {
                        for(AbstractGameEffect effect : AbstractDungeon.topLevelEffects){
                            if(effect instanceof CurtainEffect){
                                ((CurtainEffect) effect).end();
                            }
                        }
                        this.did = true;
                    }
                }
                if(this.duration < 0){
                    this.isDone = true;
                }
                break;
        }
    }
}
