package lobotomyMod.monster.Ordeal.Bug;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class BugMidnight extends AbstractOrdealMonster {
    public static final String ID = "BugMidnight";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BugMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private float targetY, underY;
    private int turns;
    private int left;
    private boolean under, start;
    private float[] POSX;
    private float[] POSY;
    private BugNight[] bugs = new BugNight[2];

    public BugMidnight(float x, float y, int left) {
        super(NAME, "BugMidnight", 300, -36.0F, 0.0F, 450.0F, 600.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Bug/Midnight/ThirdType.atlas", "lobotomyMod/images/monsters/Ordeal/Bug/Midnight/ThirdType.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        if(this.left == 1){
            e = this.state.setAnimation(0, "Default_Under", true);
        }
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Spawn", 0.9F);
        this.stateData.setMix("Default", "DigIn", 0.9F);
        this.stateData.setMix("Default_Under", "Digout", 0.9F);
        this.damage.add(new DamageInfo(this, 20));
        this.targetY = this.drawY;
        this.underY = -620;
        this.left = left;
        this.turns = this.left - 1;
        if(this.left == 1){
            this.under = true;
            this.start = false;
            //this.POSX = new float[]{this.drawX - 100, this.drawX, this.drawX + 100, this.drawX + 200, this.drawX + 300, this.drawX + 400};
            this.POSX = new float[]{this.drawX - 100, this.drawX};
            this.hb_x = 36 * Settings.scale;
            this.refreshHitboxLocation();
        }
        else {
            this.flipHorizontal = true;
            this.under = false;
            this.start = true;
            //this.POSX = new float[]{this.drawX + 350, this.drawX + 250, this.drawX + 150, this.drawX + 50, this.drawX - 50, this.drawX - 150};
            this.POSX = new float[]{this.drawX + 350, this.drawX + 250};
        }
        this.POSY = new float[]{this.drawY + 20, this.drawY, this.drawY + 20, this.drawY, this.drawY + 20, this.drawY};

        for(int i = 0; i < this.POSX.length; i ++){
            this.POSX[i] -= Settings.WIDTH * 0.75F / Settings.scale;
            this.POSY[i] -= AbstractDungeon.floorY / Settings.scale;
        }
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        if(!this.under){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
        AbstractDungeon.player.movePosition(Settings.WIDTH / 2.0F, AbstractDungeon.player.drawY);
        this.drawY = this.underY;
        this.intentHb.move(this.intentHb.cX, 300);
    }

    @Override
    protected void updateHitbox(float hb_x, float hb_y, float hb_w, float hb_h) {
        super.updateHitbox(hb_x, hb_y, hb_w, hb_h);
        if(this.under){
            this.intentHb.move(this.intentHb.cX, this.targetY);
            //LobotomyMod.logger.info("----------intent y: " + this.intentHb.cY);
        }
    }

    protected void getMove(int num) {
        int counter = 0;
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster instanceof BugNight && !monster.isDying && ((BugNight) monster).left == this.left){
                counter ++;
                break;
            }
        }
        switch (this.turns) {
            case 0:
                setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
                break;
            case 1:
                if(counter < 6) {
                    setMove((byte) 1, Intent.UNKNOWN);
                }
                else {
                    setMove((byte) 2, Intent.NONE);
                }
                break;
            case 2:
                setMove((byte) 2, Intent.NONE);
                break;
            case 3:
                setMove((byte) 3, Intent.UNKNOWN);
                break;
        }
        this.turns ++;
        if(this.turns > 3){
            this.turns = 0;
        }
    }

    public void takeTurn() {
        switch (this.nextMove){
            case 0:
                this.changeState("OUT");
                this.start = true;
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SPAWN"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    for(int i = 0; i < this.bugs.length; i ++) {
                        if (this.bugs[i] == null || this.bugs[i].isDeadOrEscaped()) {
                            this.bugs[i] = new BugNight(this.POSX[i], this.POSY[i], this.left);
                            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(this.bugs[i], false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.bugs[i], this.bugs[i], new ShiftingPower(this.bugs[i])));
                            break;
                        }
                    }
                }, 1.07F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    for(int i = 0; i < this.bugs.length; i ++) {
                        if (this.bugs[i] == null || this.bugs[i].isDeadOrEscaped()) {
                            this.bugs[i] = new BugNight(this.POSX[i], this.POSY[i], this.left);
                            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(this.bugs[i], false, AbstractDungeon.getCurrRoom().monsters.monsters.size() + 1));
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.bugs[i], this.bugs[i], new ShiftingPower(this.bugs[i])));
                            break;
                        }
                    }
                }, 0.46F));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "IN"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    this.start = false;
                }, 2.33F));
                int num = 0;
                for (BugNight bug : this.bugs) {
                    if (bug != null && !(bug.isDeadOrEscaped())) {
                        num += 80;
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, num));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void update() {
        super.update();
        if(this.start && this.under){
            if(this.drawY < this.targetY) {
                this.drawY += (this.targetY - this.underY) * Gdx.graphics.getDeltaTime();
            }
            else {
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0)));
                this.under = false;
                this.drawY = this.targetY;
            }
        }
        else if(!this.start && !this.under){
            if(this.drawY > this.underY) {
                this.drawY -= (this.targetY - this.underY) * Gdx.graphics.getDeltaTime();
            }
            else {
                this.under = true;
            }
        }
    }

    public void die() {
        super.die();
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(2, 4, true));
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "OUT":
                this.state.setAnimation(0, "Digout", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "IN":
                this.state.setAnimation(0, "DigIn", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default_Under", true, 0.0F);
                break;
            case "SPAWN":
                this.state.setAnimation(0, "Spawn", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.under){
            this.intentHb.move(this.intentHb.cX, this.targetY);
//            LobotomyMod.logger.info("----------intent y: " + this.intentHb.cY);
//            LobotomyMod.logger.info("----------intent x: " + this.intentHb.cX);
        }
        super.render(sb);
    }
}
