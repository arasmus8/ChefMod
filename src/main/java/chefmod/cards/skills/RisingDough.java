package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import static chefmod.ChefMod.makeID;

public class RisingDough extends AbstractChefCard {
    public static String ID = makeID(RisingDough.class.getSimpleName());

    public RisingDough() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));
        applyToSelf(new EquilibriumPower(p, 1));
    }
}
