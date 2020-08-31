package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FreezeAction;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.recipe.NeowNuggets;
import chefmod.recipe.RecipeManager;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class TodaysSpecial extends AbstractChefCard implements StartupCard {
    public static String ID = makeID(TodaysSpecial.class.getSimpleName());

    public TodaysSpecial() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.BASIC,
                CardTarget.ENEMY
        );
        upgradeCostTo = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new HungerPower(m));
    }

    @Override
    public boolean atBattleStartPreDraw() {
        ChefMod.recipeManager.startRecipe(new NeowNuggets());
        return true;
    }
}
