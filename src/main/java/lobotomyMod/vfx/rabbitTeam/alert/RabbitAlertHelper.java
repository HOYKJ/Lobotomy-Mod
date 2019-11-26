package lobotomyMod.vfx.rabbitTeam.alert;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * @author hoykj
 */
public class RabbitAlertHelper extends AbstractGameEffect {

    public RabbitAlertHelper(){
    }

    public void update(){
        AbstractDungeon.topLevelEffectsQueue.add(new RabbitAlertSubtitle());
        AbstractDungeon.topLevelEffectsQueue.add(new RabbitAlertBack());
        AbstractDungeon.topLevelEffectsQueue.add(new RabbitAlterTitle());
        CardCrawlGame.sound.play("RabbitTeam_Alert");
        this.isDone = true;
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    public void dispose(){}
}
