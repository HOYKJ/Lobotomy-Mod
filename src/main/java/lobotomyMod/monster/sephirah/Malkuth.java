package lobotomyMod.monster.sephirah;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.sephirah.Binah.Binah;
import lobotomyMod.power.MalkuthConfusionPower;

/**
 * @author hoykj
 */
public class Malkuth extends AbstractOrdealMonster {
    public static final String ID = "Malkuth";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Malkuth");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns;

    public Malkuth(float x, float y) {
        super(NAME, "Malkuth", 600, 0.0F, -20.0F, 500.0F, 400.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Malkuth/malkuth.atlas", "lobotomyMod/images/monsters/Sephirah/Malkuth/malkuth.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "First", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("First", "Second", 0.2F);
        this.stateData.setMix("First", "Dead", 0.2F);
        this.stateData.setMix("Second", "Dead", 0.2F);
        this.damage.add(new DamageInfo(this, 28));
        this.turns = 1;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Malkuth.mp3");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new MalkuthConfusionPower(AbstractDungeon.player, this)));
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if(this.turns == 6){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CHANGE"));
        }
        this.turns ++;
        if(!AbstractDungeon.player.hasPower(MalkuthConfusionPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new MalkuthConfusionPower(AbstractDungeon.player, this)));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.deathTimer += 1.5F;
        this.changeState("DIE");
    }

    public int getTurns(){
        return this.turns;
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "CHANGE":
                this.state.setAnimation(0, "Second", true);
                this.state.setTimeScale(1F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg {
        @SpirePostfixPatch
        public static void postfix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(m instanceof Malkuth){
                    Texture img = LobotomyImageMaster.MALKUTH_BACKGROUND;
                    sb.setColor(Color.WHITE.cpy());
                    sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
                    return;
                }
            }
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0){
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(m instanceof Malkuth){
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
