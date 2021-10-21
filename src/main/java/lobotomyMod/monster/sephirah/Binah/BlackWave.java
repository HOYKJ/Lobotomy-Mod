package lobotomyMod.monster.sephirah.Binah;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;

/**
 * @author hoykj
 */
public class BlackWave extends AbstractMonster {
    public static final String ID = "BlackWave";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BlackWave");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private float timer;

    public BlackWave(float x, float y) {
        super("", "BlackWave", 100, 20.0F, -20.0F, 240.0F, 300.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Binah/blackWave.atlas", "lobotomyMod/images/monsters/Sephirah/Binah/blackWave.json", 2.2F);
        this.state.setAnimation(0, "Create", false);
        this.state.addAnimation(0, "Move", true, 0.0F);
        this.stateData.setMix("Move", "End", 0.1F);
    }

    protected void getMove(int num) {
        setMove((byte) 0, Intent.NONE);
    }

    public void takeTurn() {
    }

    @Override
    public void update() {
        super.update();
        if(this.isDeadOrEscaped() || AbstractDungeon.player.isDead){
            return;
        }
        this.drawX -= Settings.WIDTH / 12.0F * Gdx.graphics.getDeltaTime();
        if(this.drawX + this.hb.width < 0){
            this.drawX += Settings.WIDTH * 1.1F;
            this.timer = 0;
        }

        if(this.drawX < AbstractDungeon.player.hb.x + AbstractDungeon.player.hb.width && this.drawX > AbstractDungeon.player.hb.x){
            if(this.timer <= 0){
                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, false));
                AbstractDungeon.player.damage(new DamageInfo(this, 6));
                this.timer += (Settings.FAST_MODE? 0.6F: 0.8F);
            }
            this.timer -= Gdx.graphics.getDeltaTime();
        }
    }

    public void die() {
        super.die(false);
        this.changeState("DIE");
        this.deathTimer += 0.5F;
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            AbstractDungeon.getCurrRoom().monsters.monsters.remove(this);
        }));
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "DIE":
                this.state.setAnimation(0, "End", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
