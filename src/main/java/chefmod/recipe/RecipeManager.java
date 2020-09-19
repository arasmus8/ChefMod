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
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;

import java.util.*;
import java.util.stream.IntStream;

public class RecipeManager {
    public static final ArrayList<String> unlockedRecipes = new ArrayList<>();
    public ArrayList<AbstractRecipe> recipes;
    private static final ArrayList<RecipeRenderer> renderers = new ArrayList<>();
    private static final int RENDER_COUNT = 5;
    private final Vector2 additionalPos;

    private static final List<String> ACT1_RECIPES = Arrays.asList(NobStew.ID, FriedLagavulin.ID, SentryBrittle.ID);

    public RecipeManager() {
        float offsetH = 128f * 1.618f * Settings.scale;
        float x = 256f * Settings.scale;
        // float y = AbstractDungeon.player.relics.get(0);
        float y = (float) Settings.HEIGHT - 256f * Settings.scale;
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
        cg.group.addAll(ChefMod.frozenPile.group);
        int ingredientCount = (int) cg.group.stream()
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
                return NobStew.ID;
            case Lagavulin.ID:
                return FriedLagavulin.ID;
            case Sentry.ID:
                return SentryBrittle.ID;
            default:
                return null;
        }
    }

    public boolean notYetUnlocked(String id) {
        return unlockedRecipes.stream().noneMatch(r -> r.equals(id));
    }

    public void unlock(MonsterGroup eliteMonsters, int actNum) {
        Optional<String> unlockId = eliteMonsters.monsters.stream()
                .map(this::recipeIdForMonster)
                .filter(Objects::nonNull)
                .findFirst();
        if (unlockId.isPresent()) {
            if (notYetUnlocked(unlockId.get())) {
                unlockedRecipes.add(unlockId.get());
            }
        } else {
            String recipeToAdd = null;
            switch (actNum) {
                case 1:
                    recipeToAdd = ACT1_RECIPES.stream()
                            .filter(s -> !unlockedRecipes.contains(s))
                            .findFirst()
                            .orElse(null);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            if (recipeToAdd != null) {
                if (notYetUnlocked(recipeToAdd)) {
                    unlockedRecipes.add(recipeToAdd);
                }
            }
        }
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
                gl.setText(FontHelper.eventBodyText, msg);
                sb.setColor(Color.WHITE);
                TextureHelper.drawScaled(sb, ImageMaster.DECK_COUNT_CIRCLE, additionalPos.x, additionalPos.y, 1.618f);
                FontHelper.renderFontCentered(sb, FontHelper.speech_font, msg, additionalPos.x, additionalPos.y);
            }
        }
    }
}