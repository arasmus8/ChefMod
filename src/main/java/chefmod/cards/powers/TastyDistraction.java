package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.TastyDistractionPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class TastyDistraction extends AbstractChefCard {
    public static String ID = makeID(TastyDistraction.class.getSimpleName());

    public TastyDistraction() {
        super(ID,
                3,
                CardType.POWER,
                CardRarity.RARE,
                CardTarget.SELF
        );
        upgradeCostTo = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new TastyDistractionPower(p, 1));
    }
}
