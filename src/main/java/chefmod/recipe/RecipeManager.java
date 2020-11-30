package chefmod.recipe;

import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.cardmods.IngredientCardmod;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.GiantHead;
import com.megacrit.cardcrawl.monsters.beyond.Nemesis;
import com.megacrit.cardcrawl.monsters.beyond.Reptomancer;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.exordium.*;

import java.util.*;
import java.util.stream.IntStream;

public class RecipeManager {
    public static final ArrayList<String> unlockedRecipes = new ArrayList<>();
    public ArrayList<AbstractRecipe> recipes;
    public float activeXPos;
    public float activeYPos;
    private static final ArrayList<RecipeRenderer> renderers = new ArrayList<>();
    private static final int RENDER_COUNT = 5;
    private final Vector2 additionalPos;

    private static final List<String> ACT1_RECIPES = Arrays.asList(NobStewRecipe.ID, FriedLagavulinRecipe.ID, SentryBrittleRecipe.ID);
    private static final List<String> ACT2_RECIPES = Arrays.asList(StabKabobRecipe.ID, SlaverSaladRecipe.ID, GremlinGoulashRecipe.ID);
    private static final List<String> ACT3_RECIPES = Arrays.asList(NemesisSouffleRecipe.ID, GiantMarbleCakeRecipe.ID, ReptoRavioliRecipe.ID);

    public RecipeManager() {
        float offsetH = 128f * 1.618f * Settings.scale;
        float x = 256f * Settings.scale;
        // float y = AbstractDungeon.player.relics.get(0);
        float y = (float) Settings.HEIGHT - 256f * Settings.scale;
        activeXPos = x;
        activeYPos = y;
        IntStream.range(0, RENDER_COUNT)
                .forEachOrdered(i -> {
                    Vector2 vec = new Vector2(x + offsetH * i, y);
                    renderers.add(new RecipeRenderer(vec));
                });
        additionalPos = new Vector2(x + offsetH * RENDER_COUNT, y + 64f * Settings.scale);
        recipes = new ArrayList<>();
    }

    public void useIngredient(AbstractCard card) {
        if (recipes.size() > 0) {
            recipes.get(0).useIngredient();
        }
    }

    public void checkIngredientCount() {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cg.group.addAll(p.hand.group);
        cg.group.addAll(p.discardPile.group);
        cg.group.addAll(p.drawPile.group);
        cg.group.add(p.cardInUse);
        cg.group.addAll(ChefMod.frozenPile.group);
        cg.group.addAll(ChefMod.cardsToFreeze);
        int ingredientCount = (int) cg.group.stream()
                .distinct()
                .filter(Objects::nonNull)
                .filter(c -> IngredientCardmod.getForCard(c, IngredientCardmod.ID).isPresent())
                .count();
        int wantedIngredientCount = recipes.stream()
                .mapToInt(r -> r.ingredientCount)
                .sum();
        if (wantedIngredientCount > ingredientCount) {
            addIngredients(wantedIngredientCount - ingredientCount);
        }
    }

    private void addIngredients(int count) {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cg.group.addAll(p.hand.group);
        cg.group.addAll(p.discardPile.group);
        cg.group.addAll(p.drawPile.group);
        cg.group.addAll(ChefMod.frozenPile.group);

        cg.group.removeIf(c -> c.cost < -1 ||
                c.type == AbstractCard.CardType.CURSE ||
                IngredientCardmod.getForCard(c, IngredientCardmod.ID).isPresent() ||
                c.purgeOnUse);

        while (cg.size() < count) {
            // TODO: make a custom status instead of slimes
            cg.addToBottom(new Slimed());
        }

        cg.shuffle(AbstractDungeon.cardRandomRng);

        cg.group.stream()
                .limit(count)
                .forEach(c -> CardModifierManager.addModifier(c, new IngredientCardmod()));
    }

    public void startRecipe(AbstractRecipe recipe) {
        recipes.add(recipe);
        addIngredients(recipe.ingredientCount);
    }

    public void remove(AbstractRecipe which) {
        recipes.remove(which);
    }

    public void clear() {
        recipes.clear();
    }

    public String recipeIdForMonster(AbstractMonster m) {
        switch (m.id) {
            case GremlinNob.ID:
                return NobStewRecipe.ID;
            case Lagavulin.ID:
                return FriedLagavulinRecipe.ID;
            case Sentry.ID:
                return SentryBrittleRecipe.ID;
            case BookOfStabbing.ID:
                return StabKabobRecipe.ID;
            case SlaverBlue.ID: //fallthrough
            case SlaverRed.ID:
                return SlaverSaladRecipe.ID;
            case GremlinLeader.ID:
                return GremlinGoulashRecipe.ID;
            case Nemesis.ID:
                return NemesisSouffleRecipe.ID;
            case GiantHead.ID:
                return GiantMarbleCakeRecipe.ID;
            case Reptomancer.ID:
                return ReptoRavioliRecipe.ID;
            default:
                return null;
        }
    }

    public boolean notYetUnlocked(String id) {
        return unlockedRecipes.stream().noneMatch(r -> r.equals(id));
    }

    private String randomRecipeForAct(List<String> possibleIds) {
        return possibleIds.stream()
                .filter(s -> !unlockedRecipes.contains(s))
                .findFirst()
                .orElse(null);
    }

    public String unlockForCombat(MonsterGroup eliteMonsters, int actNum) {
        Optional<String> unlockId = eliteMonsters.monsters.stream()
                .map(this::recipeIdForMonster)
                .filter(Objects::nonNull)
                .findFirst();
        if (unlockId.isPresent()) {
            if (notYetUnlocked(unlockId.get())) {
                return unlockId.get();
            }
        } else {
            String recipeToAdd = null;
            switch (actNum) {
                case 1:
                    recipeToAdd = randomRecipeForAct(ACT1_RECIPES);
                    break;
                case 2:
                    recipeToAdd = randomRecipeForAct(ACT2_RECIPES);
                    break;
                case 3:
                    recipeToAdd = randomRecipeForAct(ACT3_RECIPES);
                    break;
                default:
                    break;
            }
            if (recipeToAdd != null) {
                if (notYetUnlocked(recipeToAdd)) {
                    return recipeToAdd;
                }
            }
        }
        return null;
    }

    public void unlock(String recipeIdToUnlock) {
        unlockedRecipes.add(recipeIdToUnlock);
    }

    public void update() {
        renderers.forEach(RecipeRenderer::update);
    }

    public void render(SpriteBatch sb) {
        if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
            IntStream.range(0, recipes.size())
                    .limit(renderers.size())
                    .forEachOrdered(i -> renderers.get(i).render(sb, recipes.get(i)));
            int additionalRecipes = recipes.size() - renderers.size();
            if (additionalRecipes > 0) {
                String msg = "+" + additionalRecipes;
                GlyphLayout gl = new GlyphLayout();
                gl.setText(FontHelper.turnNumFont, msg);
                sb.setColor(Color.WHITE);
                TextureHelper.drawScaled(sb, ImageMaster.DECK_COUNT_CIRCLE, additionalPos.x, additionalPos.y, 1.618f);
                FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, additionalPos.x, additionalPos.y);
            }
        }
    }
}