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
public class RedAltar extends AbstractOrdealMonster {
    public static final String ID = "RedAltar";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodMidnight");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int counter;
    private RedAttacker tentacles[] = new RedAttacker[2];

    public RedAltar(float x, float y, RedAttacker tentacle, RedAttacker tentacle2) {
        super(NAME, "RedAltar", 400, 0.0F, 90.0F, 540.0F, 360.0F, "lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/Red.png", x - Settings.WIDTH * 0.75F / Settings.scale + 270, y - 90);
        //this.flipHorizontal = true;
        AbstractDungeon.getCurrRoom().cannotLose = true;
        this.tentacles[0] = tentacle;
        this.tentacles[1] = tentacle2;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.player.movePosition(Settings.WIDTH / 2.0F, AbstractDungeon.player.drawY);
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
            this.img = ImageMaster.loadImage("lobotomyMod/images/monsters/Ordeal/OutterGod/Midnight/Red_dead.png");
            this.halfDead = true;
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }
            this.powers.clear();
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if(m instanceof RedAttacker){
                    m.die();
                }
            }
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m instanceof PaleAltar || m instanceof BlackAltar || m instanceof WhiteAltar){
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
