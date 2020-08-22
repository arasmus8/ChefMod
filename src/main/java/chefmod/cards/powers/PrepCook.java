package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import static chefmod.ChefMod.makeID;

public class PrepCook extends AbstractChefCard {
    public static String ID = makeID(PrepCook.class.getSimpleName());

    public PrepCook() {
        super(ID,
                1,
                CardType.POWER,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
        upgradeCostTo = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RetainCardPower(p, magicNumber));
    }
}
