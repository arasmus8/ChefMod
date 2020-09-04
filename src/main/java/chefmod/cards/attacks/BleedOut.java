package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.WeakenWhenHitPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class BleedOut extends AbstractChefCard {
    public static String ID = makeID(BleedOut.class.getSimpleName());

    public BleedOut() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 5;
        upgradeDamageBy = 3;
        damages = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        applyToEnemy(m, new WeakenWhenHitPower(m));
    }
}
