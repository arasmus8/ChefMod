package chefmod.cards.skills;

import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.SatiatedPower;
import chefmod.powers.SupercooledPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class IceCreamCone extends AbstractChefCard {
    public static String ID = makeID(IceCreamCone.class.getSimpleName());

    public IceCreamCone() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseMagicNumber = magicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new SatiatedPower(m));
        applyToEnemy(m, new SupercooledPower(m, 1));
        IntStream.rangeClosed(1, magicNumber)
                .forEachOrdered(i -> addToBot(new PlayOldestFrozenCardAction(c -> c.type == CardType.ATTACK)));
    }
}
