package chefmod;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import chefmod.recipe.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class RecipeConsoleCommand extends ConsoleCommand {

    public RecipeConsoleCommand() {
        maxExtraTokens = 1;
        minExtraTokens = 0;
        requiresPlayer = true;
        simpleCheck = true;
    }

    private static final ArrayList<String> recipeList;

    static {
        recipeList = new ArrayList<>();
        recipeList.add(NeowNuggetsRecipe.ID);
        recipeList.add(NobStewRecipe.ID);
        recipeList.add(FriedLagavulinRecipe.ID);
        recipeList.add(SentryBrittleRecipe.ID);
        recipeList.add(StabKabobRecipe.ID);
        recipeList.add(SlaverSaladRecipe.ID);
        recipeList.add(GremlinGoulashRecipe.ID);
        recipeList.add(NemesisSouffleRecipe.ID);
        recipeList.add(GiantMarbleCakeRecipe.ID);
        recipeList.add(ReptoRavioliRecipe.ID);
    }

    @Override
    protected ArrayList<String> extraOptions(String[] tokens, int depth) {
        if (depth > 0 && depth < 5) {
            return recipeList;
        }
        return new ArrayList<>();
    }

    private AbstractRecipe recipeById(String id) {
        if (id.equals(NeowNuggetsRecipe.ID)) return new NeowNuggetsRecipe();
        if (id.equals(NobStewRecipe.ID)) return new NobStewRecipe();
        if (id.equals(FriedLagavulinRecipe.ID)) return new FriedLagavulinRecipe();
        if (id.equals(SentryBrittleRecipe.ID)) return new SentryBrittleRecipe();
        if (id.equals(StabKabobRecipe.ID)) return new StabKabobRecipe();
        if (id.equals(SlaverSaladRecipe.ID)) return new SlaverSaladRecipe();
        if (id.equals(GremlinGoulashRecipe.ID)) return new GremlinGoulashRecipe();
        if (id.equals(NemesisSouffleRecipe.ID)) return new NemesisSouffleRecipe();
        if (id.equals(GiantMarbleCakeRecipe.ID)) return new GiantMarbleCakeRecipe();
        if (id.equals(ReptoRavioliRecipe.ID)) return new ReptoRavioliRecipe();
        return new NeowNuggetsRecipe();
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (AbstractDungeon.player == null) {
            DevConsole.log("Cannot start recipe - player is null");
        } else {
            if (tokens.length == 1) {
                if (ChefMod.recipeManager.recipes.size() > 0) {
                    int ingredientCount = ChefMod.recipeManager.recipes.get(0).ingredientCount();
                    IntStream.rangeClosed(1, ingredientCount)
                            .forEachOrdered(i -> ChefMod.recipeManager.useIngredient(null));
                }
            } else if (tokens.length == 2) {
                try {
                    ChefMod.recipeManager.startRecipe(recipeById(tokens[1]));
                } catch (Exception e) {
                    DevConsole.log("Something went wrong!");
                }
            } else {
                DevConsole.log("Command not formatted correctly!");
            }
        }

    }
}
