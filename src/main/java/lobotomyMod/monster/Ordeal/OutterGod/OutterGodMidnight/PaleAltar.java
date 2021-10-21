package lobotomyMod.monster.Ordeal.OutterGod.OutterGodMidnight;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class PaleAltar extends AbstractOrdealMonster {
    public static final String ID = "PaleAltar";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int counter;
    private PaleAttacker tentacle;

    public PaleAltar(float x, float y, PaleAttacker tentacle) {
        super(NAME, "PaleAltar", 400, 0.0F, 90.0F, 540.0F, 360.0F, "lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/Pale.png", x - Settings.WIDTH * 0.75F / Settings.scale + 270, y - 90);
        AbstractDungeon.getCurrRoom().cannotLose = true;
        this.tentacle = tentacle;
        this.type = EnemyType.BOSS;
    }

    protected void getMove(int num) {
        setMove((byte) 0, Intent.NONE);
    }

    public void takeTurn() {
    }

    @Override
    public void damage(DamageInfo info) {
        int tmp = this.currentHealth;
        super.damage(info);
        this.counter += tmp - this.currentHealth;
        if(this.counter >= 180){
            this.tentacle.active();
            this.counter -= 180;
        }
        if ((this.currentHealth <= 0) && (!this.halfDead)) {
            this.img = ImageMaster.loadImage("lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/Pale_dead.png");
            this.halfDead = true;
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }
            this.powers.clear();
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if(m instanceof PaleAttacker){
                    m.die();
                }
            }
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m instanceof BlackAltar || m instanceof RedAltar || m instanceof WhiteAltar){
                    if (!m.halfDead) {
                        allDead = false;
                        break;
                    }
                }
            }
            if (allDead) {
                AbstractDungeon.getCurrRoom().cannotLose = false;
                this.halfDead = false;
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    m.die();
                }
            }
        }
    }

    public void die() {
        super.die();
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(3, 4, true));
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
        }
    }
}
