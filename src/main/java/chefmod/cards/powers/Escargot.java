package chefmod.cards.powers;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.EscargotRecipe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Escargot extends AbstractChefCard {
    public static String ID = makeID(Escargot.class.getSimpleName());

    public Escargot() {
        super(ID,
                0,
                CardType.POWER,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChefMod.recipeManager.startRecipe(new EscargotRecipe(upgraded, this));
    }
}
