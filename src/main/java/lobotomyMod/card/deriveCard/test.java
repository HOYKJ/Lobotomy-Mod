package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.angelaCard.department.*;
import lobotomyMod.character.Angela;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.monster.friendlyMonster.RabbitTeam;
import lobotomyMod.reward.CogitoReward;
import lobotomyMod.vfx.HellTrainEffect;
import lobotomyMod.vfx.action.ChooseEffect;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;
import lobotomyMod.vfx.rabbitTeam.alert.RabbitAlertBack;
import lobotomyMod.vfx.rabbitTeam.alert.RabbitAlertHelper;
import lobotomyMod.vfx.rabbitTeam.order.RabbitOrderScreen;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class test extends AbstractCard {
    public static final String ID = "test";
    private static final int COST = 0;

    public test() {
        super("test", "test", "red/attack/reaper",  0, "test", CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, 150), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        //AbstractDungeon.topLevelEffects.add(new HellTrainEffect());

//        AbstractMonster monster2 = new YinMonster(Settings.WIDTH * 0.2F, 0.0F);
//        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(monster2, false));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster2, monster2, new YinYangPower(monster2)));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster2, monster2, new ThornsPower(monster2, 4), 4));
//        AbstractMonster monster = new YangMonster(-Settings.WIDTH * 0.8F, 0.0F);
//        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(monster, false));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new YinYangPower(monster)));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new YangHealPower(monster, 3), 3));

//        CogitoReward tmp = new CogitoReward();
//        tmp.getAbnormalityTool();
        AbstractDungeon.effectList.add(new LatterEffect(()->{
            LobotomyUtils.OrdealStart(3, 4);
        }, 0.2F));

//        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
//            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("FixerMidnight");
//            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
//            AbstractDungeon.getCurrRoom().monsters.init();
//            AbstractRoom.waitTimer = 0.1F;
//            AbstractDungeon.player.preBattlePrep();
//            CardCrawlGame.fadeIn(1.5F);
//            AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
//        }, 0.2F));

        //LobotomyMod.rabbitOrderScreen = new RabbitOrderScreen();
        //AbstractDungeon.getCurrRoom().monsters.addMonster(0, new RabbitTeam(AbstractDungeon.player.drawX + 280, 0, new ArrayList<>(AbstractDungeon.getCurrRoom().monsters.monsters)));
//        AbstractMonster monster = new RabbitTeam(-720, 0, new ArrayList<>(AbstractDungeon.getCurrRoom().monsters.monsters));
//        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(monster, false, 0));
//        AbstractDungeon.actionManager.addToBottom(new LatterAction(monster::createIntent));

//        Angela.departments[18] += 6;
//        LobotomyUtils.hireDepartment();
    }

    public AbstractCard makeCopy() {
        return new test();
    }

    public void upgrade() {
    }

    static {
    }
}
