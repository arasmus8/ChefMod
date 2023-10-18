package chefmod.cards.powers;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.LocoMocoRecipe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class LocoMoco extends AbstractChefCard {
    public static String ID = makeID(LocoMoco.class.getSimpleName());

    public LocoMoco() {
        super(ID,
                0,
                CardType.POWER,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChefMod.recipeManager.startRecipe(new LocoMocoRecipe(magicNumber, upgraded, this));
    }
}
