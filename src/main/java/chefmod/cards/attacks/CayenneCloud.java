package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class CayenneCloud extends AbstractChefCard {
    public static String ID = makeID(CayenneCloud.class.getSimpleName());

    public CayenneCloud() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 9;
        magicNumber = baseMagicNumber = 1;
        upgradeDamageBy = -5;
        upgradeMagicNumberBy = 2;
        damages = true;
        isMultiDamage = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber)
                .forEach(i -> dealAoeDamage(AbstractGameAction.AttackEffect.FIRE));
    }
}
