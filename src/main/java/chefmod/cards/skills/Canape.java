package chefmod.cards.skills;

import chefmod.actions.XCostAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Canape extends AbstractChefCard {
    public static String ID = makeID(Canape.class.getSimpleName());

    public Canape() {
        super(ID,
                -1,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.ENEMY
        );
        magicNumber = baseMagicNumber = 0;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new XCostAction(this, (x, params) -> {
            IntStream.rangeClosed(1, x + params[0])
                    .forEach(i -> applyToEnemy(m, new HungerPower(m)));
            return true;
        }, magicNumber));
    }
}
