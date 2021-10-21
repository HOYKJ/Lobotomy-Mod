package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.EventRoom;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.Angela;
import lobotomyMod.event.BossRushEvent;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.monster.Ordeal.Bug.BugDawn;
import lobotomyMod.monster.Ordeal.Bug.BugMidnight;
import lobotomyMod.monster.Ordeal.Bug.BugNight;
import lobotomyMod.monster.Ordeal.Circus.CircusDawn;
import lobotomyMod.monster.Ordeal.Cleaner;
import lobotomyMod.monster.Ordeal.Machine.MachineDawn;
import lobotomyMod.monster.Ordeal.Machine.MachineMidnight;
import lobotomyMod.monster.Ordeal.Machine.MachineNight;
import lobotomyMod.monster.Ordeal.Machine.MachineNoon;
import lobotomyMod.monster.Ordeal.OutterGod.OutterGodDawn;
import lobotomyMod.monster.Ordeal.OutterGod.OutterGodMidnight.BlackAltar;
import lobotomyMod.monster.Ordeal.OutterGod.OutterGodNoon;
import lobotomyMod.reward.CogitoReward;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class Ordeal extends AbstractCard {
    public static final String ID = "Ordeal";
    private static final int COST = 0;

    public Ordeal() {
        super("Ordeal", "Ordeal", "green/skill/alchemize",  0, "test", CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
//        CogitoReward tmp = new CogitoReward();
//        tmp.getAbnormalityTool();
        final ChooseAction choice = new ChooseAction(this, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true, 1, true);
        choice.add("Ordeal", MachineDawn.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(1, 1);
            }, 0.2F));
        });
        choice.add("Ordeal", MachineNoon.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(1, 2);
            }, 0.2F));
        });
        choice.add("Ordeal", MachineNight.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(1, 3);
            }, 0.2F));
        });
        choice.add("Ordeal", MachineMidnight.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(1, 4);
            }, 0.2F));
        });

        choice.add("Ordeal", BugDawn.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(2, 1);
            }, 0.2F));
        });
        choice.add("Ordeal", BugNight.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(2, 3);
            }, 0.2F));
        });
        choice.add("Ordeal", BugMidnight.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(2, 4);
            }, 0.2F));
        });

        choice.add("Ordeal", OutterGodDawn.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(3, 1);
            }, 0.2F));
        });
        choice.add("Ordeal", OutterGodNoon.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(3, 2);
            }, 0.2F));
        });
        choice.add("Ordeal", BlackAltar.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(3, 4);
            }, 0.2F));
        });

        choice.add("Ordeal", CircusDawn.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(4, 1);
            }, 0.2F));
        });
        choice.add("Ordeal", Cleaner.NAME, ()->{
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                LobotomyUtils.OrdealStart(5, 2);
            }, 0.2F));
        });
        this.addToBot(choice);
    }

    public AbstractCard makeCopy() {
        return new Ordeal();
    }

    public void upgrade() {
    }

    static {
    }
}
