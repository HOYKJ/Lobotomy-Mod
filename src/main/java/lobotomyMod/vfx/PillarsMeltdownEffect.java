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
public class PillarsMeltdownEffect extends AbstractGameEffect {
    private float x, y, distance;
    private DamageInfo damageInfo;
    private boolean damaged, active;
    private DelayAction action;
    private float interval = 0.0F;
    private Color color2[] = new Color[8];

    public PillarsMeltdownEffect(float x, float y, DamageInfo damageInfo){
        this.color = Color.WHITE.cpy();
        this.color.a = 0;
        this.duration = 1.2F;
        this.startingDuration = this.duration;
        this.x = x;
        this.y = y;
        this.damageInfo = damageInfo;
        this.distance = 0;
        this.damaged = false;
        this.active = false;

        for(int i = 0; i < 8; i ++){
            switch (MathUtils.random(3)){
                case 0:
                    this.color2[i] = new Color(0.79F, 0.15F, 0.25F, 1);
                    break;
                case 1:
                    this.color2[i] = new Color(0.93F, 0.91F, 0.76F, 1);
                    break;
                case 2:
                    this.color2[i] = new Color(0.61F, 0.37F, 0.64F, 1);
                    break;
                default:
                    this.color2[i] = new Color(0.27F, 0.78F, 0.72F, 1);
                    break;
            }
        }
    }

    public void update(){
        this.rotation += 36 * Gdx.graphics.getDeltaTime();

        if(this.duration <= 0){
            this.isDone = true;
        }

        this.color.a = 0;

        if(this.startingDuration - this.duration > 1){
            this.color.a = 1;
            if(this.active){
                if(this.action == null){
                    this.action = new DelayAction();
                    AbstractDungeon.actionManager.addToBottom(this.action);
                }
                this.distance += Settings.WIDTH / 0.18F * Gdx.graphics.getDeltaTime();
                this.duration -= Gdx.graphics.getDeltaTime();
            }
        }
        else {
            this.color.a = 1.2F - this.duration;
            this.duration -= Gdx.graphics.getDeltaTime();
        }

        for(int i = 0; i < 8; i ++) {
            if (this.color.a > 0) {
                if (this.color.a > 0.7F) {
                    this.color2[i].a = 0.8F;
                } else {
                    this.color2[i].a = this.color.a + 0.1F;
                }
            } else {
                this.color2[i].a = 0;
            }
        }
        this.interval -= Gdx.graphics.getDeltaTime();
        if (this.interval < 0.0F) {
            this.interval = 0.01F;
            int derp = MathUtils.random(2, 3);
            for (int i = 0; i < derp; i++) {
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x - this.distance - 240 * Settings.scale, this.y, this.color2[0], 180));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)),
                        this.y + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)), this.color2[1], 135));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x, this.y + this.distance + 240 * Settings.scale, this.color2[2], 90));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)),
                        this.y + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)), this.color2[3], 45));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x + this.distance + 240 * Settings.scale, this.y, this.color2[4], 0));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)),
                        this.y - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)), this.color2[5], -45));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x, this.y - this.distance - 240 * Settings.scale, this.color2[6], -90));
                AbstractDungeon.effectsQueue.add(new PillarSmokeEffect(this.x - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)),
                        this.y - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)), this.color2[7], -135));
            }
        }

        if(!this.damaged && this.x - this.distance - 240 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F <= AbstractDungeon.player.hb.cX){
            this.damaged = true;
            this.action.end();
            for(int i = 0; i < 8; i ++){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damageInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
            //AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, damageInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if(AbstractDungeon.player.drawPile.size() > 0){
                AbstractCard card = AbstractDungeon.player.drawPile.getRandomCard(true);
                if(card != null){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                }
            }

            if(AbstractDungeon.player.hand.size() > 0){
                AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
                if(card != null){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
            }

            if(AbstractDungeon.player.discardPile.size() > 0){
                AbstractCard card = AbstractDungeon.player.discardPile.getRandomCard(true);
                if(card != null){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                }
            }
        }
    }

    public void active(){
        this.active = true;
    }

    public void end(){
        this.isDone = true;
        if(this.action != null) {
            this.action.end();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
//        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - this.distance - 300 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F, this.y,
//                (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F, (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 1.5F);
//        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - this.distance * MathUtils.sin((float) Math.toRadians(45)) - 300 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F,
//                this.y + this.distance * MathUtils.sin((float) Math.toRadians(45)), (float)LobotomyImageMaster.TOMB_STONE.getWidth() / 1.5F, (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 1.5F);
        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - this.distance - 240 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, 0, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, -45, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y + this.distance + 240 * Settings.scale - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, -90, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, -135, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x + this.distance + 240 * Settings.scale - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, 180, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x + (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, 135, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y - this.distance - 240 * Settings.scale - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, 90, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);

        sb.draw(LobotomyImageMaster.TOMB_STONE, this.x - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - (float)LobotomyImageMaster.TOMB_STONE.getWidth(), this.y - (this.distance + 240 * Settings.scale) * MathUtils.sin((float) Math.toRadians(45)) - LobotomyImageMaster.TOMB_STONE.getHeight() / 2.0F,
                (float)LobotomyImageMaster.TOMB_STONE.getWidth(), (float)LobotomyImageMaster.TOMB_STONE.getHeight() / 2F, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), 1 / 1.5F, 1 / 1.5F, 45, 0, 0, LobotomyImageMaster.TOMB_STONE.getWidth(),
                LobotomyImageMaster.TOMB_STONE.getHeight(), false, false);
    }

    public void dispose(){

    }
}
