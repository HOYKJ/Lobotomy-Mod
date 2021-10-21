package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.DelayAction;
import lobotomyMod.helper.LobotomyImageMaster;

/**
 * @author hoykj
 */
public class TombStoneEffect extends AbstractGameEffect {
    private float x, y, distance;
    private DamageInfo damageInfo;
    private boolean damaged;
    private DelayAction action;
    private float interval = 0.0F;
    private Color color2;

    public TombStoneEffect(float x, float y, DamageInfo damageInfo){
        this.color = Color.WHITE.cpy();
        this.color.a = 0;
        this.duration = 3.2F;
        this.startingDuration = this.duration;
        this.x = x;
        this.y = y;
        this.damageInfo = damageInfo;
        this.distance = 0;
        this.damaged = false;
        switch (MathUtils.random(3)){
            case 0:
                this.color2 = new Color(0.79F, 0.15F, 0.25F, 1);
                break;
            case 1:
                this.color2 = new Color(0.93F, 0.91F, 0.76F, 1);
                break;
            case 2:
                this.color2 = new Color(0.61F, 0.37F, 0.64F, 1);
                break;
            default:
                this.color2 = new Color(0.27F, 0.78F, 0.72F, 1);
                break;
        }
    }

    public void update(){
        if(this.action == null){
            this.action = new DelayAction();
            AbstractDungeon.actionManager.addToBottom(this.action);
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if(this.duration <= 0){
            this.isDone = true;
        }

        this.color.a = 0;

        if(this.startingDuration - this.duration > 3){
            this.distance += Settings.WIDTH / 0.18F * Gdx.graphics.getDeltaTime();
            this.color.a = 1;
        }
        else if(this.startingDuration - this.duration > 1.3){
            this.color.a = (1.9F - this.duration) / 1.7F;
        }

        if(this.color.a > 0) {
            if(this.color.a > 0.7F){
                this.color2.a = 0.8F;
            }
            else {
                this.color2.a = this.color.a + 0.1F;
            }
        }
        else {
            this.color2.a = 0;
        }
        this.interval -= Gdx.graphics.getDeltaTime();
        if (this.interval < 0.0F) {
            this.interval = 0.01F;
            int derp = MathUtils.random(2, 3);
            for (int i = 0; i < derp; i++) {
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x - this.distance - 240 * Settings.scale,
                        this.y + LobotomyImageMaster.TOMB_STONE.getHeight() / 3.0F, this.color2, 180));
            }
        }

        if(!this.damaged && this.x - this.distance - 240 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F <= AbstractDungeon.player.hb.cX){
            this.damaged = true;
            this.action.end();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, damageInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if(AbstractDungeon.player.drawPile.size() < 1){
                return;
            }
            AbstractCard card = AbstractDungeon.player.drawPile.getRandomCard(true);
            if(card != null){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - this.distance - 240 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F, this.y,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F, (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 1.5F);
    }

    public void dispose(){

    }
}
