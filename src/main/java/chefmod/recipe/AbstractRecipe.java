package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.util.ActionUnit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractRecipe implements ActionUnit {
    public String tipHeader;
    public String tipBody;

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
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) { }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) { }

    @Override
    public void gainBlock() { }
}
