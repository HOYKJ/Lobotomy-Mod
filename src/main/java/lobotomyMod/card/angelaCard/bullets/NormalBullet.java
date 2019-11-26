package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author hoykj
 */
public class NormalBullet extends AbstractBulletCard {

    public NormalBullet() {
        super("NormalBullet", SpecialBullet.NAME, 1, SpecialBullet.DESCRIPTION, CardType.ATTACK, CardTarget.ENEMY);
        this.baseDamage = 8;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType)));
    }

    public AbstractCard makeCopy() {
        return new NormalBullet();
    }
}
