package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.character.Angela;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class SpecialBullet extends AbstractBulletCard {
    public static final String ID = "SpecialBullet";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private Angela.AimType aimType;
    private AbstractBulletCard tb;
    private ArrayList<AbstractBulletCard> bulletList = new ArrayList<>();

    public SpecialBullet() {
        super("SpecialBullet", SpecialBullet.NAME, 1, SpecialBullet.DESCRIPTION, CardType.ATTACK, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.bulletList.clear();
        this.bulletList.add(new NormalBullet());
        this.bulletList.add(new HpAim());
        this.bulletList.add(new SpAim());
        this.bulletList.add(new RedAim());
        this.bulletList.add(new WhiteAim());
        this.bulletList.add(new BlackAim());
        this.bulletList.add(new PaleAim());
        this.bulletList.add(new SlowAim());
        this.bulletList.add(new ExecuteAim());
        this.tb = this.bulletList.get(0);
        this.initAim();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.tb == null){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType)));
        }
        else {
            this.tb.use(p, m);
            if (this.tb.removeOnUse){
                AbstractCard r = null;
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card.uuid.equals(this.uuid)){
                        r = card;
                        break;
                    }
                }
                if(r != null){
                    AbstractDungeon.player.masterDeck.removeCard(r);
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player instanceof Angela && this.aimType != ((Angela) AbstractDungeon.player).aimType){
            this.changeAim(((Angela) AbstractDungeon.player).aimType);
        }
        this.feedBack();
    }

    public void changeAim(Angela.AimType type){
        if(this.tb != null){
            this.feedBack();
        }
        switch (type){
            case NORMAL:
                this.tb = this.bulletList.get(0);
                break;
            case HP:
                this.tb = this.bulletList.get(1);
                break;
            case SP:
                this.tb = this.bulletList.get(2);
                break;
            case RED:
                this.tb = this.bulletList.get(3);
                break;
            case WHITE:
                this.tb = this.bulletList.get(4);
                break;
            case BLACK:
                this.tb = this.bulletList.get(5);
                break;
            case PALE:
                this.tb = this.bulletList.get(6);
                break;
            case SLOW:
                this.tb = this.bulletList.get(7);
                break;
            case EXECUTE:
                this.tb = this.bulletList.get(8);
                break;
        }
        if(this.tb != null){
            this.name = this.tb.name;
            this.initializeTitle();
            this.rawDescription = this.tb.rawDescription;
            this.portrait = this.tb.portrait;
            this.type = this.tb.type;
            this.target = this.tb.target;
            this.initAim();
        }
        this.aimType = type;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(this.tb != null){
            this.tb.applyPowers();
            this.initAim2();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(this.tb != null){
            this.tb.calculateCardDamage(mo);
            this.initAim2();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(this.tb == null) {
            return super.canUse(p, m);
        }
        return this.tb.canUse(p, m);
    }

    @Override
    public void modifyCostForCombat(int amt) {
        super.modifyCostForCombat(amt);

    }

    private void initAim(){
        this.cost = this.tb.cost;
        this.costForTurn = this.tb.costForTurn;
        this.baseDamage = this.tb.baseDamage;
        this.baseBlock = this.tb.baseBlock;
        this.baseMagicNumber = this.tb.baseMagicNumber;
        this.magicNumber = this.baseMagicNumber;
        this.isCostModified = this.tb.isCostModified;
        this.isCostModifiedForTurn = this.tb.isCostModifiedForTurn;
        this.freeToPlayOnce = this.tb.freeToPlayOnce;
        this.exhaust = this.tb.exhaust;
        this.isEthereal = this.tb.isEthereal;
        this.purgeOnUse = this.tb.purgeOnUse;
        this.initializeDescription();
    }

    private void initAim2(){
        this.cost = this.tb.cost;
        this.costForTurn = this.tb.costForTurn;
        this.damage = this.tb.damage;
        this.block = this.tb.block;
        this.magicNumber = this.tb.magicNumber;
        this.isCostModified = this.tb.isCostModified;
        this.isCostModifiedForTurn = this.tb.isCostModifiedForTurn;
        this.freeToPlayOnce = this.tb.freeToPlayOnce;
        this.exhaust = this.tb.exhaust;
        this.isEthereal = this.tb.isEthereal;
        this.purgeOnUse = this.tb.purgeOnUse;
        this.initializeDescription();
    }

    private void feedBack(){
        this.tb.cost = this.cost;
        this.tb.costForTurn = this.costForTurn;
        this.tb.baseDamage = this.baseDamage;
        this.tb.baseBlock = this.baseBlock;
        this.tb.baseMagicNumber = this.baseMagicNumber;
        this.tb.magicNumber = this.tb.baseMagicNumber;
        this.tb.isCostModified = this.isCostModified;
        this.tb.isCostModifiedForTurn = this.isCostModifiedForTurn;
        this.tb.freeToPlayOnce = this.freeToPlayOnce;
        this.tb.exhaust = this.exhaust;
        this.tb.isEthereal = this.isEthereal;
        this.tb.purgeOnUse = this.purgeOnUse;
    }

    public AbstractCard makeCopy() {
        return new SpecialBullet();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SpecialBullet");
        NAME = SpecialBullet.cardStrings.NAME;
        DESCRIPTION = SpecialBullet.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SpecialBullet.cardStrings.EXTENDED_DESCRIPTION;
    }
}
