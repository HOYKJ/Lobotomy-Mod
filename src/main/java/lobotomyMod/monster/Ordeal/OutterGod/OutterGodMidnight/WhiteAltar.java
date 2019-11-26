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
public class WhiteAltar extends AbstractOrdealMonster {
    public static final String ID = "WhiteAltar";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int counter;
    private WhiteAttacker tentacles[] = new WhiteAttacker[2];

    public WhiteAltar(float x, float y, WhiteAttacker tentacle, WhiteAttacker tentacle2) {
        super(NAME, "WhiteAltar", 600, 0.0F, 90.0F, 540.0F, 360.0F, "lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/White.png", x - Settings.WIDTH * 0.75F / Settings.scale + 270, y - 90);
        this.tentacles[0] = tentacle;
        this.tentacles[1] = tentacle2;
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
            this.tentacles[0].active();
            this.tentacles[1].active();
            this.counter -= 180;
        }
        if ((this.currentHealth <= 0) && (!this.halfDead)) {
            this.img = ImageMaster.loadImage("lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/White_dead.png");
            this.halfDead = true;
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }
            this.powers.clear();
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((!m.halfDead) && !(m instanceof WhiteAttacker)) {
                    allDead = false;
                    break;
                }
                else if(m instanceof WhiteAttacker){
                    m.die();
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
