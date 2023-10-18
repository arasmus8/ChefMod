package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.FriedLagavulinRecipe;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class FriedLagavulin extends AbstractOptionCard {
    public static String ID = makeID(FriedLagavulin.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FriedLagavulin() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new FriedLagavulinRecipe());
    }
}
