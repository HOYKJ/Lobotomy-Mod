package lobotomyMod.monster.Ordeal.OutterGod.OutterGodMidnight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;

/**
 * @author hoykj
 */
public class PaleAttacker extends AbstractOrdealMonster {
    public static final String ID = "PaleAttacker";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns;
    private boolean back;
    private float timer;
    private PaleAltar parent;
    private float vX, vY;

    public PaleAttacker(float x, float y) {
        super(NAME, "PaleAttacker", 1, 0.0F, 0.0F, 0.0F, 512.0F, "lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/Peye_Resize.png", x - Settings.WIDTH * 0.75F / Settings.scale, y - 90);
        this.back = false;
        this.vX = MathUtils.random(-200, 200);
        this.vY = MathUtils.random(-200, 200);
        this.drawX = MathUtils.random(120 * Settings.scale ,Settings.WIDTH - 120 * Settings.scale);
        this.drawY = MathUtils.random(80 * Settings.scale ,Settings.HEIGHT - 80 * Settings.scale);
    }

    public void addParent(PaleAltar parent){
        this.parent = parent;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.hideHealthBar();
        this.halfDead = true;
    }

    protected void getMove(int num) {
        switch (this.turns){
            case 0:
                setMove((byte) 1, Intent.BUFF);
                break;
            case 1:
                setMove((byte) 2, Intent.DEFEND);
                break;
        }
        this.turns ++;
        if(this.turns > 1){
            this.turns = 0;
        }
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if(!(m.isDeadOrEscaped()) && (m instanceof RedAttacker || m instanceof WhiteAttacker || m instanceof BlackAttacker)){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 3), 3));
                }
            }
        }
        else if(this.nextMove == 2) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if(!(m.isDeadOrEscaped()) && (m instanceof RedAltar || m instanceof WhiteAltar || m instanceof BlackAltar || m instanceof PaleAltar)){
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, 50));
                }
            }
        }
    }

    @Override
    public void damage(DamageInfo info) {
    }

    public void die() {
        super.die();
    }

    public void active(){
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!(m.isDeadOrEscaped()) && (m instanceof RedAttacker || m instanceof WhiteAttacker || m instanceof BlackAttacker)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 3), 3));
            }
        }
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!(m.isDeadOrEscaped()) && (m instanceof RedAltar || m instanceof WhiteAltar || m instanceof BlackAltar || m instanceof PaleAltar)){
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, 50));
            }
        }
        this.back = true;
        this.timer = 6;
    }

    @Override
    public void update() {
        super.update();
        if(this.back){
            this.drawX += (this.parent.hb.cX - this.drawX) * 0.5F * Gdx.graphics.getDeltaTime();
            this.drawY += (this.parent.hb.cY - this.drawY) * 0.5F * Gdx.graphics.getDeltaTime();
            if(this.drawX > this.parent.drawX && this.drawX < this.parent.drawX + this.parent.hb.width
                    &&this.drawX > this.parent.drawX && this.drawX < this.parent.drawX + this.parent.hb.width){
                this.timer -= Gdx.graphics.getDeltaTime();
                if(this.timer <= 0){
                    this.back = false;
                }
            }
        }
        else {
            this.drawX += this.vX * Gdx.graphics.getDeltaTime();
            this.drawY += this.vY * Gdx.graphics.getDeltaTime();

            if(this.drawX < 40 * Settings.scale ){
                this.vX += 60 * Gdx.graphics.getDeltaTime();
            }
            else if(this.drawX > Settings.WIDTH - 110 * Settings.scale){
                this.vX -= 60 * Gdx.graphics.getDeltaTime();
            }
            else {
                this.vX = (this.vX > 0? (this.drawX < Settings.WIDTH / 2.0F? (this.drawX + 180) / 6: (Settings.WIDTH - this.drawX + 180) / 6): (this.drawX > Settings.WIDTH / 2.0F? -(Settings.WIDTH - this.drawX + 180) / 6: -(this.drawX + 180) / 6)) * Settings.scale;
            }

            if(this.drawY < 20 * Settings.scale){
                this.vY += 60 * Gdx.graphics.getDeltaTime();
            }
            else if(this.drawY > Settings.HEIGHT - 482 * Settings.scale){
                this.vY -= 60 * Gdx.graphics.getDeltaTime();
            }
            else {
                this.vY = (this.vY > 0? (this.drawY < Settings.HEIGHT / 2.0F? (this.drawY + 180) / 6: (Settings.HEIGHT - this.drawY + 180) / 6): (this.drawY > Settings.HEIGHT / 2.0F? -(Settings.HEIGHT - this.drawY + 180) / 6: -(this.drawY + 180) / 6)) * Settings.scale;
            }
        }
    }
}
