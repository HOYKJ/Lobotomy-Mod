package lobotomyMod.monster.sephirah.Binah;

import basemod.helpers.SuperclassFinder;
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
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.common.RemoveRandomBuffAction;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.Ordeal.Machine.MachineNight;
import lobotomyMod.monster.Ordeal.Machine.MachineNoon;
import lobotomyMod.power.ResistantPower;
import lobotomyMod.vfx.FairiesEffect;
import lobotomyMod.vfx.PillarsMeltdownEffect;
import lobotomyMod.vfx.TombStoneEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class Binah extends AbstractMonster {
    public static final String ID = "Binah";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Binah");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int stage, turns;
    public float resistance;
    private BlackPin pin;
    private PillarsMeltdownEffect pillars;
    private BlackWave wave;
    private boolean stop;

    public Binah(float x, float y) {
        super(NAME, "Binah", 600, 40.0F, 0.0F, 280.0F, 400.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Binah/bna.atlas", "lobotomyMod/images/monsters/Sephirah/Binah/bna.json", 1.6F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_01", 0.1F);
        this.stateData.setMix("Default", "Attack_02", 0.1F);
        this.stateData.setMix("Default", "Attack_03", 0.1F);
        this.stateData.setMix("Default", "Attack_04_Cast", 0.1F);
        this.stateData.setMix("Attack_04_Cast", "Attack_04_End", 0.1F);
        this.stateData.setMix("Attack_04_End", "Default", 0.1F);
        this.stateData.setMix("Default", "Dead", 0.1F);
        this.stateData.setMix("Default", "Rest", 0.1F);
        this.stateData.setMix("Rest", "Default", 0.1F);
        this.damage.add(new DamageInfo(this, 30));
        this.damage.add(new DamageInfo(this, 80));
        this.damage.add(new DamageInfo(this, 21));
        this.stage = 1;
        this.turns = 0;
        this.wave = null;
        this.resistance = 0.1F;
        this.dialogX = -70;
        this.dialogY = 90;
        this.stop = true;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Binah.mp3");
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(0)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ResistantPower(this)));
    }

    protected void getMove(int num) {
        switch (this.stage){
            case 1:
                switch (this.turns){
                    case 0:
                        setMove(MOVES[0], (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                        break;
                    case 1:
                        setMove(MOVES[1], (byte) 2, Intent.ATTACK, this.damage.get(1).base);
                        break;
                    case 2:
                        this.pin = new BlackPin(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.y - 20, this);
                        this.pin.drawX = AbstractDungeon.player.hb.cX;
                        this.pin.drawY = AbstractDungeon.player.hb.y - 20;
                        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.pin, false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this.pin));
                        setMove(MOVES[2], (byte) 3, Intent.NONE);
                        break;
                }
                break;
            case 2:
                switch (this.turns){
                    case 0:
                        setMove(MOVES[0], (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                        break;
                    case 1:
                        setMove(MOVES[1], (byte) 2, Intent.ATTACK, this.damage.get(1).base);
                        break;
                    case 2:
                        this.pin = new BlackPin(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.y - 20, this);
                        this.pin.drawX = AbstractDungeon.player.hb.cX;
                        this.pin.drawY = AbstractDungeon.player.hb.y - 20;
                        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.pin, false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this.pin));
                        setMove(MOVES[2], (byte) 4, Intent.NONE);
                        break;
                }
                break;
            case 3:
                switch (this.turns){
                    case 0:
                        setMove(MOVES[0], (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                        break;
                    case 1:
                        setMove(MOVES[1], (byte) 2, Intent.ATTACK, this.damage.get(1).base);
                        break;
                    case 2:
                        this.pin = new BlackPin(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.y - 20, this);
                        this.pin.drawX = AbstractDungeon.player.hb.cX;
                        this.pin.drawY = AbstractDungeon.player.hb.y - 20;
                        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.pin, false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this.pin));
                        setMove(MOVES[2], (byte) 4, Intent.NONE);
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK4CAST"));
                        this.pillars = new PillarsMeltdownEffect(this.hb.cX, this.hb.cY, this.damage.get(2));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.pillars));
                        setMove(MOVES[3], (byte) 5, Intent.ATTACK_DEBUFF, this.damage.get(2).base, 8, true);
                        break;
                }
                break;
        }
        this.turns ++;
        if(this.stage == 3){
            if(this.turns > 3){
                this.turns = 0;
            }
        }
        else {
            if(this.turns > 2){
                this.turns = 0;
            }
        }

        if(this.stage >= 2){
            if(this.wave == null || this.wave.isDead){
                if (this.stop){
                    this.stop = false;
                }
                else {
                    this.wave = new BlackWave(0, 0);
                    this.wave.drawX = Settings.WIDTH;
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.wave, false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
                    this.stop = true;
                }
            }
        }
        //AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.wave, false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
    }

    public void takeTurn() {
        if(this.resistance > 0.8){
            this.resistance = 0.8F;
        }
        else if(this.resistance > 0.1 && this.stage < 3){
            this.resistance = 0.1F;
        }

        switch (this.nextMove){
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK3"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new TombStoneEffect(this.hb.cX, this.hb.cY, this.damage.get(0))));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK1"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.effectList.add(new FairiesEffect(this.hb.cX));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }, 3.3F));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    this.pin.act();
                }, 2.6F));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    this.pin.act();
                    AbstractDungeon.actionManager.addToBottom(new RemoveRandomBuffAction(AbstractDungeon.player));
                }, 2.6F));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK4END"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    this.pillars.active();
                }, 0.1F));
                break;
            case 8:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DEFAULT"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(3)));
                break;
            case 9:
                this.halfDead = false;
                if (this.maxHealth < 1){
                    this.maxHealth = 1;
                    this.showHealthBar();
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(1)));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DEFAULT"));
                break;
            case 99:
                this.halfDead = false;
                if (this.maxHealth < 1){
                    this.maxHealth = 1;
                    this.showHealthBar();
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(2)));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK4END"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    this.pillars.active();
                }, 0.1F));
                break;
        }
        if(!this.hasPower(ResistantPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ResistantPower(this)));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void loseResistance(){
        this.resistance = 2;
    }

    public void die() {
        switch (this.stage){
            case 1:
                this.halfDead = true;
                this.stage ++;
                this.changeState("REST");
                this.turns = 0;
                setMove((byte)9, Intent.UNKNOWN);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)9, Intent.UNKNOWN));
                break;
            case 2:
                this.halfDead = true;
                this.stage ++;
//                this.changeState("ATTACK4CAST");
                this.turns = 0;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK4CAST"));
                this.pillars = new PillarsMeltdownEffect(this.hb.cX, this.hb.cY, this.damage.get(2));
//                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.pillars));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.effectList.add(this.pillars);
                }));
                setMove(MOVES[3] ,(byte) 99, Intent.ATTACK_DEBUFF, this.damage.get(2).base, 8, true);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[3] ,(byte) 99, Intent.ATTACK_DEBUFF, this.damage.get(2).base, 8, true));
                this.resistance = 0.8F;
                break;
            case 3:
                super.die();
                this.changeState("DIE");
                this.deathTimer += 3.8F;
                break;
        }
        for (Object s = this.powers.iterator(); ((Iterator)s).hasNext();)
        {
            AbstractPower p = (AbstractPower)((Iterator)s).next();
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                ((Iterator)s).remove();
            }
        }
        if(this.pin != null && !this.pin.isDeadOrEscaped()){
            this.pin.die();
        }
        if(this.pillars != null){
            this.pillars.end();
        }
    }

    @Override
    public void update() {
        super.update();
        if(this.pin == null){
            return;
        }
        if(this.pin.isDead){
            return;
        }
        if(AbstractDungeon.getCurrRoom().monsters.monsters.get(AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1) instanceof BlackPin){
            return;
        }
        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(this.pin, false, AbstractDungeon.getCurrRoom().monsters.monsters.size()));
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this.pin));
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK1":
                this.state.setAnimation(0, "Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK3":
                this.state.setAnimation(0, "Attack_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK4CAST":
                this.state.setAnimation(0, "Attack_04_Cast", true);
                this.state.setTimeScale(1F);
                break;
            case "ATTACK4END":
                this.state.setAnimation(0, "Attack_04_End", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "REST":
                this.state.setAnimation(0, "Rest", true);
                this.state.setTimeScale(1F);
                break;
            case "DEFAULT":
                this.state.setAnimation(0, "Default", true);
                this.state.setTimeScale(1F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }

    private String getWord(int i){
        ArrayList<String> derp = new ArrayList<>();
        switch (i){
            case 0:
                derp.add(DIALOG[1]);
                derp.add(DIALOG[2]);
                derp.add(DIALOG[3]);
                derp.add(DIALOG[4]);
                break;
            case 1:
                derp.add(DIALOG[5]);
                derp.add(DIALOG[6]);
                derp.add(DIALOG[7]);
                break;
            case 2:
                derp.add(DIALOG[8]);
                derp.add(DIALOG[9]);
                derp.add(DIALOG[10]);
                derp.add(DIALOG[11]);
                break;
            case 3:
                derp.add(DIALOG[12]);
                break;
            case 4:
                derp.add(DIALOG[13]);
                derp.add(DIALOG[14]);
                break;
        }
        return derp.get(MathUtils.random(derp.size() - 1));
    }

    public boolean unableButton(){
        if(this.stage == 3){
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(4)));
            return true;
        }
        return false;
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg {
        @SpirePostfixPatch
        public static void postfix(TheBottomScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            if(!(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Binah)){
                return;
            }
            Texture img = LobotomyImageMaster.BINAH_BACKGROUND;
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheCityScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg2 {
        @SpirePostfixPatch
        public static void postfix(TheCityScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            if(!(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Binah)){
                return;
            }
            Texture img = LobotomyImageMaster.BINAH_BACKGROUND;
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg3 {
        @SpirePostfixPatch
        public static void postfix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(m instanceof Binah){
                    Texture img = LobotomyImageMaster.BINAH_BACKGROUND;
                    sb.setColor(Color.WHITE.cpy());
                    sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
                    return;
                }
            }
        }
    }

    @SpirePatch(
            clz= TheEndingScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg4 {
        @SpirePostfixPatch
        public static void postfix(TheEndingScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            if(!(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Binah)){
                return;
            }
            Texture img = LobotomyImageMaster.BINAH_BACKGROUND;
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBottomScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Binah){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TheCityScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg2 {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheCityScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Binah){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg3 {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0){
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(m instanceof Binah){
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TheEndingScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg4 {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheEndingScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Binah){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
