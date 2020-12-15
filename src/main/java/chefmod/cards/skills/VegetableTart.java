package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class VegetableTart extends AbstractChefCard {
    public static String ID = makeID(VegetableTart.class.getSimpleName());

    public VegetableTart() {
        super(ID,
                8,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseBlock = block = 16;
        upgradeBlockBy = 4;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        costForTurn = MathUtils.clamp(cost - ChefMod.frozenPile.size(), 0, costForTurn);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }
}
