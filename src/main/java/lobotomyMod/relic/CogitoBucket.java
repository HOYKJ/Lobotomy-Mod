package lobotomyMod.relic;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.unique.RecallAbnormalityAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.department.Binah;
import lobotomyMod.card.angelaCard.department.Geburah;
import lobotomyMod.character.Angela;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.npc.AbstractNPC;
import lobotomyMod.reward.CogitoReward;
import lobotomyMod.room.NewVictoryRoom;
import lobotomyMod.ui.LobotomyFtue;
import lobotomyMod.vfx.action.LatterEffect;

import java.io.IOException;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.saveFile;

/**
 * @author hoykj
 */
public class CogitoBucket extends AbstractLobotomyRelic implements CustomSavable<int[]> {
    public static ArrayList<AbstractNPC> npcs = new ArrayList<>();
    public static ArrayList<AbstractNPC> npcs_t = new ArrayList<>();
    public static final String ID = "CogitoBucket";
    public static int[] level = new int[120];
    private int rightTime;
    public static boolean reExtract, reEnter = false;
    public boolean clicked, changed;

    public CogitoBucket()
    {
        super("CogitoBucket",  RelicTier.SPECIAL, LandingSound.HEAVY);
        this.counter = LobotomyMod.PE;
    }

    @Override
    public void obtain() {
        super.obtain();
        if (LobotomyMod.deleteSave){
            LobotomyMod.deleteSave = false;
            LobotomyMod.PE = 0;
            LobotomyMod.levelSave = new int[120];
            level = new int[120];
            try {
                LobotomyMod.saveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.counter = LobotomyMod.PE;
        level[0] = 1;
        this.rightTime = 0;
        reExtract = false;
        this.changed = false;
    }

    protected  void onRightClick(){
        this.rightTime ++;
//        if(this.rightTime > 9){
//            this.counter = 999;
//        }
        //this.counter += 9999;
//        if(this.rightTime >= 19){
//            level = new int[120];
//        }
//        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
//            return;
//        }

        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null){
            if (AbstractDungeon.getCurrRoom() instanceof NewVictoryRoom){
                return;
            }
            if (AbstractDungeon.getCurrRoom().monsters != null) {
                if (AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.getCurrRoom().isBattleOver && !AbstractDungeon.getCurrRoom().cannotLose) {
                    AbstractDungeon.getCurrRoom().endBattle();
                } else {
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDeadOrEscaped()) {
                            LobotomyMod.logger.info(m.id);
                        }
                    }
                }
            }
        }
        if (!this.clicked && !AbstractDungeon.isScreenUp) {
            this.clicked = true;
            AbstractDungeon.actionManager.addToBottom(new RecallAbnormalityAction(this));
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        super.justEnteredRoom(room);
        if(room instanceof VictoryRoom){
            LobotomyMod.deadTime = 0;
        }
        if(!(room instanceof RestRoom)) {
            Angela.tmpD = null;
        }

        npcs.clear();
        npcs.addAll(npcs_t);
        for (AbstractNPC npc: npcs){
            npc.justEnteredRoom(room);
        }

        reEnter = false;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.rightTime = 0;
        LobotomyMod.PE = this.counter;
        System.arraycopy(CogitoBucket.level, 0, LobotomyMod.levelSave, 0, CogitoBucket.level.length);
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//            this.changed = true;
            CogitoBucket.level[0] ++;
        }));

        if(reEnter && AbstractDungeon.getCurrRoom() != null){
            this.justEnteredRoom(AbstractDungeon.getCurrRoom());
        }

        for (AbstractNPC npc: npcs){
            npc.atBattleStart();
        }

        this.initRecallTip();
    }

    @Override
    public void onMasterDeckChange() {
        super.onMasterDeckChange();
        if(Angela.departments[Binah.departmentCode[0]] == 5) {
            AbstractDungeon.player.gainGold(25);
        }
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
        this.changed = false;
        System.arraycopy(CogitoBucket.level, 0, LobotomyMod.levelSave, 0, CogitoBucket.level.length);
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        npcs_t.clear();
        for (AbstractNPC npc: npcs){
            npc.onVictory();
            if(!npc.needRemove){
                npcs_t.add(npc);
            }
        }
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (Angela.departments[Binah.departmentCode[0]] > 3 && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
        }
        for(String id : RecallAbnormalityAction.recallMap.keySet()){
            if (id.equals(c.cardID)){
                if(LobotomyMod.activeTutorials[5]) {
                    AbstractDungeon.ftue = new LobotomyFtue(5);
                    LobotomyMod.activeTutorials[5] = false;
                    initRecallTip();
                }
            }
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        for (AbstractNPC npc: npcs){
            npc.atTurnStartPostDraw();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        for (AbstractNPC npc: npcs){
            npc.onPlayerEndTurn();
        }
    }

    @Override
    public void onLoseHp(int damageAmount) {
        super.onLoseHp(damageAmount);
        for (AbstractNPC npc: npcs){
            npc.onLoseHp(damageAmount);
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        super.onMonsterDeath(m);
        for (AbstractNPC npc: npcs){
            npc.onMonsterDeath(m);
        }
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player == null){
            return;
        }
        if(AbstractDungeon.bossKey != null && DungeonMap.boss == null && AbstractDungeon.bossKey.equals("FixerMidnight")){
            DungeonMap.boss = ImageMaster.loadImage("lobotomyMod/images/ui/map/boss/claw.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("lobotomyMod/images/ui/map/bossOutline/claw.png");
        }
        if (AbstractDungeon.currMapNode != null) {
            if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof VictoryRoom && !(AbstractDungeon.getCurrRoom() instanceof NewVictoryRoom) && LobotomyMod.meltdownCode != -1) {
                if (LobotomyMod.activeAngela && LobotomyMod.challengeEvent) {
                    MapRoomNode node = new MapRoomNode(-1, 15);
                    node.room = new NewVictoryRoom(VictoryRoom.EventType.HEART);
                    AbstractDungeon.nextRoom = node;
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.nextRoomTransitionStart();
                }
            }
        }

        for (AbstractNPC npc: npcs){
            //LobotomyMod.logger.info(npc.ID);
            if(npc.needRemove){
                LobotomyMod.logger.info("remove");
                AbstractDungeon.effectList.add(new LatterEffect(()->{
                    npcs.remove(npc);
                    npc.dispose();
                }));
            }
            else {
                //LobotomyMod.logger.info("update");
                npc.update();
            }
        }
    }

//    @Override
//    protected void initializeTips() {
//
//        super.initializeTips();
//        for (PowerTip tip : this.tips){
//            if (tip.header.equals(this.name)){
//                tip.body = this.description;
//            }
//        }
//    }

    private void initRecallTip(){
        for (int i = 0; i < this.tips.size(); i ++){
            if (this.tips.get(i).header.equals(this.DESCRIPTIONS[1])){
                this.tips.remove(i);
                break;
            }
        }
        if(!LobotomyMod.activeTutorials[5]){
            StringBuilder str = new StringBuilder();
            int no = 1;
            for(String id : RecallAbnormalityAction.recallMap.keySet()){
                AbstractCard card = CardLibrary.getCard(id).makeCopy();
                if(card != null){
                    str.append(no).append(". ").append(card.name).append(" NL ");
                    no ++;
                }
            }
            this.tips.add(new PowerTip(this.DESCRIPTIONS[1], str.toString()));
        }
    }

    //    @Override
//    public void renderInTopPanel(SpriteBatch sb) {
//        super.renderInTopPanel(sb);
//        for (AbstractNPC npc: npcs){
//            npc.render(sb);
//        }
//    }

    public static boolean hasNPC(String ID){
        for(AbstractNPC npc : npcs){
            if(npc.ID.equals(ID)){
                return true;
            }
        }
        return false;
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
