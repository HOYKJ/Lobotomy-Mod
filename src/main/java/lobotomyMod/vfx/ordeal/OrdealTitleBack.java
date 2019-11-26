package lobotomyMod.vfx.ordeal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class OrdealTitleBack extends AbstractGameEffect {
    private int code, difficulty;
    private float titleLine;
    private boolean end;

    public OrdealTitleBack(int code, int difficulty, boolean end){
        this.duration = 6;
        this.startingDuration = this.duration;
        this.code = code;
        this.difficulty = difficulty;
        this.end = end;
        this.titleLine = 0.9F;
        switch (code){
            case 1:
                this.color = new Color(113 / 255.0F, 155 / 255.0F, 91 / 255.0F, 1.0F);
                break;
            case 2:
                this.color = new Color(252 / 255.0F, 149 / 255.0F, 11 / 255.0F, 1.0F);
                this.titleLine = 1.1F;
                break;
            case 3:
                this.color = new Color(160 / 255.0F, 105 / 255.0F, 233 / 255.0F, 1.0F);
                this.titleLine = 1.3F;
                break;
            case 4:
                this.color = new Color(177 / 255.0F, 38 / 255.0F, 69 / 255.0F, 1.0F);
                break;
            case 5:
                this.color = new Color(69 / 255.0F, 111 / 255.0F, 255 / 255.0F, 1.0F);
                this.titleLine = 1.1F;
                break;
            default:
                this.color = Color.WHITE.cpy();
                break;
        }
        this.color.a = 0;
    }

    public void update(){
        if(this.duration == this.startingDuration){
            switch (code){
                case 1:
                    if(!this.end){
                        CardCrawlGame.sound.play("Machine_Start");
                    }
                    else {
                        CardCrawlGame.sound.play("Machine_End");
                    }
                    break;
                case 2:
                    if(!this.end){
                        CardCrawlGame.sound.play("Bug_Start");
                    }
                    else {
                        CardCrawlGame.sound.play("Bug_End");
                    }
                    break;
                case 3:
                    if(!this.end){
                        CardCrawlGame.sound.play("OutterGod_Start");
                    }
                    else {
                        CardCrawlGame.sound.play("OutterGod_End");
                    }
                    break;
                case 4:
                    if(!this.end){
                        CardCrawlGame.sound.play("Circus_Start");
                    }
                    else {
                        CardCrawlGame.sound.play("Circus_End");
                    }
                    break;
                case 5:
                    if(!this.end){
                        CardCrawlGame.sound.play("Scavenger_Start");
                    }
                    else {
                        CardCrawlGame.sound.play("Scavenger_End");
                    }
                    break;
                default:
                    if(!this.end){
                        CardCrawlGame.sound.play("WhiteOrdeal_Start");
                    }
                    else {
                        CardCrawlGame.sound.play("WhiteOrdeal_End");
                    }
                    break;
            }
            AbstractDungeon.topLevelEffectsQueue.add(new OrdealTitleWord(this.code, this.difficulty));
            AbstractDungeon.topLevelEffectsQueue.add(new OrdealNameWord(this.code, this.difficulty));
            AbstractDungeon.topLevelEffectsQueue.add(new OrdealIntroWord(this.code, this.difficulty, this.end));
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            this.isDone = true;
        }

        if(this.duration > 5) {
            this.color.a = 6 - this.duration;
        }
        else if(this.duration < 1){
            this.color.a = this.duration;
        }
        else {
            this.color.a = 1;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, this.color.a * 0.8F);
        sb.draw(LobotomyImageMaster.ORDEAL_FRAME, 0.0F, (Settings.HEIGHT - LobotomyImageMaster.ORDEAL_FRAME.getHeight() * 1.5F) / 2.0F, Settings.WIDTH,
                LobotomyImageMaster.ORDEAL_FRAME.getHeight() * 1.5F);
        sb.setColor(this.color);
        sb.draw(LobotomyImageMaster.ORDEAL_TEXT_LINE, (Settings.WIDTH - LobotomyImageMaster.ORDEAL_TEXT_LINE.getWidth() * this.titleLine) / 2.0F,
                (Settings.HEIGHT) / 2.0F + 99.0F * Settings.scale, LobotomyImageMaster.ORDEAL_TEXT_LINE.getWidth() * this.titleLine, LobotomyImageMaster.ORDEAL_TEXT_LINE.getHeight());
        sb.draw(LobotomyImageMaster.ORDEAL_PARTING_LINE, (Settings.WIDTH - LobotomyImageMaster.ORDEAL_PARTING_LINE.getWidth() * 2) / 2.0F,
                (Settings.HEIGHT) / 2.0F - 20.0F * Settings.scale, LobotomyImageMaster.ORDEAL_PARTING_LINE.getWidth() * 2, LobotomyImageMaster.ORDEAL_TEXT_LINE.getHeight());
    }

    public void dispose(){}
}
