package lobotomyMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.helper.LobotomyImageMaster;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class FreischutzSnipEffect extends AbstractGameEffect {
    @SpireEnum
    public static GameCursor.CursorType BULLET;
    private boolean seven;

    public FreischutzSnipEffect(boolean seven){
        this.duration = 0.1F;
        this.seven = seven;
    }

    public void update(){
        CardCrawlGame.cursor.changeType(FreischutzSnipEffect.BULLET);
        this.duration -= Gdx.graphics.getDeltaTime();

        if(this.duration <= 0){
            this.duration += 0.1F;
            AbstractDungeon.topLevelEffectsQueue.add(new SnipTrailEffect(InputHelper.mX, InputHelper.mY));
        }

        if(InputHelper.justClickedLeft){
            InputHelper.justClickedLeft = false;
            this.isDone = true;
            for(AbstractGameEffect effect : AbstractDungeon.topLevelEffects){
                if(effect instanceof SnipTrailEffect){
                    effect.isDone = true;
                }
            }
            CardCrawlGame.sound.play("Freischutz_Shot");

            AbstractCreature target = null;
            if(AbstractDungeon.player.hb.hovered){
                target = AbstractDungeon.player;
            }
            for (int i = (AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1); i >= 0; i--) {
                AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if ((!(m.isDying)) && (m.currentHealth > 0) && (!(m.isEscaping))) {
                    if(m.hb.hovered){
                        target = m;
                    }
                }
            }

            if(target != null){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(AbstractDungeon.player, 18), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }

            if(this.seven){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 18), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(LobotomyImageMaster.FREISCHUTZ_FILTER, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose(){

    }

    @SpirePatch(
            clz= GameCursor.class,
            method="render"
    )
    public static class render {
        @SpireInsertPatch(rloc=4)
        public static void Insert(GameCursor _inst, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException {
            if (!Settings.isTouchScreen)
            {
                Field type = _inst.getClass().getDeclaredField("type");
                type.setAccessible(true);

                if(type.get(_inst) == FreischutzSnipEffect.BULLET){
                    sb.setColor(Color.WHITE);
                    sb.draw(LobotomyImageMaster.FREISCHUTZ_CURSOR, InputHelper.mX - 32.0F + (24.0F * Settings.scale), InputHelper.mY - 32.0F - (24.0F * Settings.scale), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
                }
            }
        }
    }
}
