package chefmod.relics;

import chefmod.TheChef;
import chefmod.cardmods.IngredientCardmod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chefmod.ChefMod.makeID;

public class WoodenSpoon extends AbstractChefRelic {
    public static String ID = makeID(WoodenSpoon.class.getSimpleName());

    public WoodenSpoon() {
        super(ID, RelicTier.UNCOMMON, LandingSound.HEAVY, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractDungeon.player.hand.group.stream()
                .filter(c -> IngredientCardmod.getForCard(c, IngredientCardmod.ID).isPresent())
                .forEach(c -> c.retain = true);
    }
}
