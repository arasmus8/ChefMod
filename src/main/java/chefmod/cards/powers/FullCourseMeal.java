package chefmod.cards.powers;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.FullCourseMealRecipe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class FullCourseMeal extends AbstractChefCard {
    public static String ID = makeID(FullCourseMeal.class.getSimpleName());

    public FullCourseMeal() {
        super(ID,
                0,
                CardType.POWER,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 25;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChefMod.recipeManager.startRecipe(new FullCourseMealRecipe(magicNumber, upgraded));
    }
}
