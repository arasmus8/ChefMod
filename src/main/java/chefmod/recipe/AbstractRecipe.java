package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.util.ActionUnit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public abstract class AbstractRecipe implements ActionUnit {
    public String TipHeader;
    public String TipBody;

    protected int ingredientCount;

    public abstract void onActivate();

    public void useIngredient() {
        ingredientCount -= 1;
        if (ingredientCount <= 0) {
            onActivate();
            ChefMod.recipeManager.remove(this);
        }
    }

    @Override
    public DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return null;
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx, DamageInfo.DamageType damageType) { }

    @Override
    public void gainBlock() { }
}
