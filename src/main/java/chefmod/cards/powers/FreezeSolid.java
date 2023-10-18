package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.FreezeSolidPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class FreezeSolid extends AbstractChefCard {
    public static String ID = makeID(FreezeSolid.class.getSimpleName());

    public FreezeSolid() {
        super(ID,
                1,
                CardType.POWER,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FreezeSolidPower(p, magicNumber));
    }
}
