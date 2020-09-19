package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.NobStewRecipe;

import static chefmod.ChefMod.makeID;

public class NobStew extends AbstractChefCard {
    public static String ID = makeID(NobStew.class.getSimpleName());

    public NobStew() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new NobStewRecipe());
    }
}
