package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.FreshProducePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class FreshProduce extends AbstractChefCard {
    public static String ID = makeID(FreshProduce.class.getSimpleName());

    public FreshProduce() {
        super(ID,
                1,
                CardType.POWER,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FreshProducePower(p, magicNumber));
    }
}
