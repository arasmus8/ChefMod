package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.util.ActionUnit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractRecipe implements ActionUnit {
    public String tipHeader;
    public String tipBody;
    public AbstractCard card;

    protected int ingredientCount;

    public abstract void onActivate();

    public void useIngredient() {
        ingredientCount -= 1;
        if (ingredientCount <= 0) {
            onActivate();
            ChefMod.recipeManager.remove(this);
            card.current_x = card.target_x = ChefMod.recipeManager.activeXPos;
            card.current_y = card.target_y = ChefMod.recipeManager.activeYPos - 256f * card.drawScale * Settings.scale;
            qAction(new ShowCardAndPoofAction(card));
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
