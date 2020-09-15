package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.OrderUpPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class OrderUp extends AbstractChefCard {
    public static String ID = makeID(OrderUp.class.getSimpleName());

    public OrderUp() {
        super(ID,
                3,
                CardType.POWER,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
        upgradeCostTo = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new OrderUpPower(p, magicNumber));
    }
}
