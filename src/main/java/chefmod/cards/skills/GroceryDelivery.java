package chefmod.cards.skills;

import chefmod.actions.XCostAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.StrengthNextTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class GroceryDelivery extends AbstractChefCard {
    public static String ID = makeID(GroceryDelivery.class.getSimpleName());

    public GroceryDelivery() {
        super(ID,
                -1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        addToBot(new XCostAction(this, (amount, params) -> {
            IntStream.rangeClosed(1, amount)
                    .forEachOrdered(i -> {
                        applyToSelf(new EnergizedPower(p, 2));
                        if (upgraded) {
                            applyToSelf(new StrengthNextTurnPower(p, 1));
                        }
                    });
            return true;
        }));
    }
}
