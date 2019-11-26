package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class AutoPlayStory extends AbstractGameEffect {
    private int scene;

    public AutoPlayStory(){
        this.duration = 0;
        this.startingDuration = 6;
        this.scene = 0;
    }

    public void update(){
        if(this.duration <= 0){
            switch (this.scene){
                case 0:
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(2));
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(3));
                    break;
                case 1:
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(4));
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(5));
                    break;
                case 2:
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(6));
                    break;
                case 3:
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(7));
                    AbstractDungeon.topLevelEffectsQueue.add(new BlackForestStory(8));
                    this.isDone = true;
                    break;
                default:
                    this.isDone = true;
            }
            this.scene ++;
            this.duration = this.startingDuration;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    public void dispose(){}
}
