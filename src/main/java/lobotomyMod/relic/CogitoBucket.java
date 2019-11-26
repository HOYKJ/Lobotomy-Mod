package lobotomyMod.relic;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.unique.RecallAbnormalityAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.reward.CogitoReward;

import java.io.IOException;

/**
 * @author hoykj
 */
public class CogitoBucket extends AbstractLobotomyRelic implements CustomSavable<int[]> {
    public static final String ID = "CogitoBucket";
    public static int[] level = new int[120];
    private int rightTime;
    public static boolean reExtract;
    private boolean canCount, changed;

    public CogitoBucket()
    {
        super("CogitoBucket",  RelicTier.SPECIAL, LandingSound.HEAVY);
        this.counter = LobotomyMod.PE;
    }

    @Override
    public void obtain() {
        super.obtain();
        this.counter = LobotomyMod.PE;
        level[0] = 1;
        this.rightTime = 0;
        reExtract = false;
        this.canCount = false;
        this.changed = false;
    }

    protected  void onRightClick(){
        this.rightTime ++;
//        if(this.rightTime > 9){
//            this.counter = 999;
//        }
        //this.counter += 9999;
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new RecallAbnormalityAction(this));
        if(this.rightTime >= 19){
            level = new int[120];
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        LobotomyMod.PE = this.counter;
        System.arraycopy(CogitoBucket.level, 0, LobotomyMod.levelSave, 0, CogitoBucket.level.length);
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.canCount = false;
        if(this.changed){
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            LobotomyMod.logger.info("level: ----------" + CogitoBucket.level[0] + "----------");
            if(CogitoBucket.level[0] == 6){
                if(!(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)){
                    return;
                }
                if((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) || (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)){
                    return;
                }
                if(LobotomyMod.activeFixer){
                    LobotomyUtils.OrdealStart(6, 1);
                }
                else {
                    LobotomyUtils.OrdealStart(AbstractDungeon.eventRng.random(3) + 1, 1);
                }
            }
            else if(CogitoBucket.level[0] == 12){
                if(!(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)){
                    return;
                }
                if((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) || (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)){
                    return;
                }
                if(LobotomyMod.activeFixer){
                    LobotomyUtils.OrdealStart(6, 2);
                }
                else {
                    int roll = AbstractDungeon.eventRng.random(2) + 1;
                    if (roll == 2) {
                        roll = 5;
                    }
                    LobotomyUtils.OrdealStart(roll, 2);
                }
            }
            else if(CogitoBucket.level[0] == 18){
                if(!(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)){
                    return;
                }
                if((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) || (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)){
                    return;
                }
                if(LobotomyMod.activeFixer){
                    LobotomyUtils.OrdealStart(6, 3);
                }
                else {
                    LobotomyUtils.OrdealStart(AbstractDungeon.eventRng.random(1) + 1, 3);
                }
            }
            else if(CogitoBucket.level[0] > 18 && AbstractDungeon.id.equals("TheBeyond")){
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
                    return;
                }
                if(LobotomyMod.activeFixer) {
                    AbstractDungeon.bossKey = "FixerMidnight";
                    DungeonMap.boss = ImageMaster.loadImage("lobotomyMod/images/ui/map/boss/claw.png");
                    DungeonMap.bossOutline = ImageMaster.loadImage("lobotomyMod/images/ui/map/bossOutline/claw.png");
                    if(AbstractDungeon.bossList.size() > 1) {
                        AbstractDungeon.bossList.remove(1);
                    }
                }
            }
            this.canCount = true;
//            this.changed = true;
            CogitoBucket.level[0] ++;
        }));
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);

    }

    @Override
    public void onVictory() {
        super.onVictory();
        reExtract = true;
        AbstractDungeon.getCurrRoom().rewards.add(new CogitoReward());
        for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
            if(card instanceof AbstractLobotomyCard){
                switch (((AbstractLobotomyCard) card).realRarity){
                    case COMMON:
                        this.counter += 12;
                        break;
                    case UNCOMMON:
                        this.counter += 18;
                        break;
                    case RARE:
                        this.counter += 30;
                        break;
                }
            }
        }
        this.rightTime = 0;
        LobotomyMod.PE = this.counter;
        if(this.canCount){
//            CogitoBucket.level[0] ++;
        }
        this.changed = false;
        System.arraycopy(CogitoBucket.level, 0, LobotomyMod.levelSave, 0, CogitoBucket.level.length);
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if(this.canCount){
//            CogitoBucket.level[0] ++;
//        }
//        this.changed = false;
    }

//    @Override
//    public void onLoseHp(int damageAmount) {
//        super.onLoseHp(damageAmount);
//        if(damageAmount >= AbstractDungeon.player.currentHealth){
//            LobotomyMod.PE = this.counter;
//            System.arraycopy(CogitoBucket.level, 0, LobotomyMod.levelSave, 0, CogitoBucket.level.length);
//            try {
//                LobotomyMod.saveData();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player == null){
            return;
        }
        if(DungeonMap.boss == null && AbstractDungeon.bossKey.equals("FixerMidnight")){
            DungeonMap.boss = ImageMaster.loadImage("lobotomyMod/images/ui/map/boss/claw.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("lobotomyMod/images/ui/map/bossOutline/claw.png");
        }
    }

    @Override
    public int[] onSave() {
        return level;
    }

    @Override
    public void onLoad(int[] arg0)
    {
        if (arg0 == null) {
            return;
        }
        System.arraycopy(arg0, 0, level, 0, arg0.length);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new CogitoBucket();
    }
}
