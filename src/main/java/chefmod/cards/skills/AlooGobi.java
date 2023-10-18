package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class AlooGobi extends AbstractChefCard {
    public static String ID = makeID(AlooGobi.class.getSimpleName());

    public AlooGobi() {
        super(ID,
                2,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseBlock = block = 10;
        upgradeBlockBy = 3;
        baseMagicNumber = magicNumber = 4;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void triggerWhenFrozen() {
        baseBlock += magicNumber;
        applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }
}
