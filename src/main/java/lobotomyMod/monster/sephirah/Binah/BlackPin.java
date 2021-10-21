package lobotomyMod.monster.sephirah.Binah;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;

/**
 * @author hoykj
 */
public class BlackPin extends AbstractMonster {
    public static final String ID = "BlackPin";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BlackPin");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private Binah binah;

    public BlackPin(float x, float y, Binah binah) {
        super("", "BlackPin", 150, 0.0F, -20.0F, 320.0F, 80.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Binah/blackPin.atlas", "lobotomyMod/images/monsters/Sephirah/Binah/blackPin.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Create", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        //this.stateData.setMix("Create", "Attack", 0.9F);
        this.damage.add(new DamageInfo(this, 40));
        this.binah = binah;
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void takeTurn() {
    }

    public void act(){
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new LatterAction(this::die, 0.5F));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0){
            this.binah.loseResistance();
            this.binah.changeState("REST");
            this.binah.setMove((byte)8, Intent.UNKNOWN);
            this.binah.createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this.binah, (byte)8, Intent.UNKNOWN));
        }
    }

    public void die() {
        super.die(false);
        this.currentHealth = 0;
        this.hideHealthBar();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            AbstractDungeon.getCurrRoom().monsters.monsters.remove(this);
        }));
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
