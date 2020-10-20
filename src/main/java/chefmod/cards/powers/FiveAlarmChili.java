package chefmod.cards.powers;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.ChiliRecipe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class FiveAlarmChili extends AbstractChefCard {
    public static String ID = makeID(FiveAlarmChili.class.getSimpleName());

    public FiveAlarmChili() {
        super(ID,
                1,
                CardType.POWER,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 10;
        upgradeMagicNumberBy = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChefMod.recipeManager.startRecipe(new ChiliRecipe(magicNumber, this));
    }
}
