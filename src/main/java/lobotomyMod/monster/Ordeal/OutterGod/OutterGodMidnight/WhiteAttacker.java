package lobotomyMod.monster.Ordeal.OutterGod.OutterGodMidnight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;

/**
 * @author hoykj
 */
public class WhiteAttacker extends AbstractOrdealMonster {
    public static final String ID = "WhiteAttacker";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private boolean attack, nor;
    private int code;

    public WhiteAttacker(float x, float y, int i) {
        super(NAME, "WhiteAttacker", 1, 0.0F, 0.0F, 0.0F, 120.0F, null, x - (float) Settings.WIDTH * 0.75F / Settings.scale, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/tentacleW.atlas", "lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/tentacleW.json", 1.0F);
        this.attack = AbstractDungeon.aiRng.randomBoolean();
        this.stateData.setMix("Ready", "Attack", 0.1F);
        this.stateData.setMix("Ready1", "Attack1", 0.1F);
        this.damage.add(new DamageInfo(this, 20, DamageInfo.DamageType.THORNS));
        this.nor = false;
        this.code = i;
        switch (i){
            case 0:
                if(this.attack) {
                    AnimationState.TrackEntry e = this.state.setAnimation(0, "Ready1", true);
                    e.setTime(e.getEndTime() * MathUtils.random());
                }
                else {
                    AnimationState.TrackEntry e = this.state.setAnimation(0, "Attack1", false);
                    e.setTime(4.33F);
                }
                this.nor = true;
                break;
            case 2:
                this.flipHorizontal = true;
            default:
                AnimationState.TrackEntry e = this.state.setAnimation(0, "Attack", false);
                e.setTime(4.33F);
                break;
        }
        this.hideHealthBar();
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.hideHealthBar();
    }

    protected void getMove(int num) {
        if(!this.nor){
            setMove((byte) 0, Intent.NONE);
            return;
        }
        if(this.attack) {
            setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        }
        else {
            setMove((byte) 2, Intent.UNKNOWN);
        }
        this.attack = !this.attack;
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }, 2.33F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
        }
        else if(this.nextMove == 2) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
    }

    @Override
    public void showHealthBar() {
        //super.showHealthBar();
    }

    public void die() {
        super.die();
    }

    @Override
    public void update() {
        super.update();
        switch (this.code){
            case 0:
                this.drawX = AbstractDungeon.player.hb.cX;
                this.drawY = AbstractDungeon.player.hb.cY + 450;
                break;
            case 1:
                this.drawX = AbstractDungeon.player.hb.cX - 450;
                this.drawY = AbstractDungeon.player.hb.cY;
                break;
            case 2:
                this.drawX = AbstractDungeon.player.hb.cX + 450;
                this.drawY = AbstractDungeon.player.hb.cY;
                break;
        }
    }

    public void active(){
        if(this.nextMove == 1){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
        setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        createIntent();
        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base));
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Attack" + (this.nor? "1": ""), false);
                this.state.setTimeScale(1F);
                break;
            case "OPEN":
                this.state.setAnimation(0, "Open" + (this.nor? "1": ""), false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Ready" + (this.nor? "1": ""), true, 0.0F);
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.nor){
            this.intentHb.move(this.intentHb.cX, this.intentHb.cY - 160);
        }
        super.render(sb);
    }
}
