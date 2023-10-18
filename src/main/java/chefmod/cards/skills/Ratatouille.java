package chefmod.cards.skills;

import chefmod.cardmods.PermanentBlockBonusCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Ratatouille extends AbstractChefCard {
    public static String ID = makeID(Ratatouille.class.getSimpleName());

    public Ratatouille() {
        super(ID,
                2,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseBlock = block = 12;
        upgradeBlockBy = 3;
        baseMagicNumber = magicNumber = 4;
        upgradeMagicNumberBy = 2;
        hasPreppedActions = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        if (isPrepped()) {
            PermanentBlockBonusCardmod.add(this, magicNumber);
        }
    }
}
