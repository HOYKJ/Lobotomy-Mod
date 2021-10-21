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
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.Ordeal.Machine.MachineNight;
import lobotomyMod.monster.sephirah.Binah.Binah;
import lobotomyMod.power.ChesedVulnerablePower;
import lobotomyMod.power.ResistantPower;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class Chesed extends AbstractOrdealMonster {
    public static final String ID = "Chesed";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Chesed");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns, vulnerable;
    public float resistance;

    public Chesed(float x, float y) {
        super(NAME, "Chesed", 1200, 0.0F, -12.0F, 520.0F, 500.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Chesed/chesedBoss.atlas", "lobotomyMod/images/monsters/Sephirah/Chesed/chesedBoss.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Phase1", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Phase1", "Phase2", 0.2F);
        this.damage.add(new DamageInfo(this, 36));
        this.turns = 1;
        this.resistance = 1.25F;
        this.vulnerable = 50;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Chesed.mp3");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ChesedVulnerablePower(AbstractDungeon.player, this.vulnerable)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ResistantPower(this)));
    }

    protected void getMove(int num) {
        if(this.turns > 6){
            setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        }
        else {
            setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true)));
        }
        if(this.turns == 2){
            this.resistance = 1.5F;
            this.vulnerable = 100;
        }
        else if(this.turns == 4){
            this.resistance = 1.75F;
            this.vulnerable = 150;
        }
        else if(this.turns == 6){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CHANGE"));
            this.resistance = 2.0F;
            this.vulnerable = 200;
        }
        this.turns ++;
        if(!this.hasPower(ResistantPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ResistantPower(this)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ChesedVulnerablePower(AbstractDungeon.player, this.vulnerable),
                this.vulnerable - (AbstractDungeon.player.hasPower(ChesedVulnerablePower.POWER_ID)? AbstractDungeon.player.getPower(ChesedVulnerablePower.POWER_ID).amount: 0)));
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.deathTimer += 1.5F;
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "CHANGE":
                this.state.setAnimation(0, "Phase2", true);
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
                if(m instanceof Chesed){
                    Texture img = LobotomyImageMaster.CHESED_BACKGROUND;
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
                    if(m instanceof Chesed){
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
