package chefmod.cards.powers;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.BananaSplitRecipe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class BananaSplit extends AbstractChefCard {
    public static String ID = makeID(BananaSplit.class.getSimpleName());

    public BananaSplit() {
        super(ID,
                1,
                CardType.POWER,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChefMod.recipeManager.startRecipe(new BananaSplitRecipe(magicNumber, upgraded, this));
    }
}
