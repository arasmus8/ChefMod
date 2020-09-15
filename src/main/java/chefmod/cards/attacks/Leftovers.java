package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class Leftovers extends AbstractChefCard {
    public static String ID = makeID(Leftovers.class.getSimpleName());

    public Leftovers() {
        super(ID,
                2,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 10;
        upgradeDamageBy = 2;
        damages = true;
    }

    @Override
    public void triggerWhenFrozen() {
        baseDamage *= 2;
        applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.POISON);
    }
}
