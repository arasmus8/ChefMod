package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class MonsterBait extends AbstractChefCard {
    public static String ID = makeID(MonsterBait.class.getSimpleName());

    public MonsterBait() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 9;
        upgradeDamageBy = 3;
        baseMagicNumber = magicNumber = 5;
        damages = true;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int origBaseDamage = baseDamage;
        if (mo.hasPower(HungerPower.POWER_ID)) {
            baseDamage += magicNumber;
        }
        super.calculateCardDamage(mo);
        baseDamage = origBaseDamage;
        if (damage != baseDamage) {
            isDamageModified = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }
}
