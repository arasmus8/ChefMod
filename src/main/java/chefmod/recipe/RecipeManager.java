package chefmod.recipe;

import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.cardmods.IngredientCardmod;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeManager {
    public ArrayList<AbstractRecipe> recipes;
    private static final ArrayList<RecipeRenderer> renderers = new ArrayList<>();
    private static final long RENDER_COUNT = 5;
    private final Vector2 additionalPos;

    public RecipeManager() {
        float offsetH = 128f * 1.618f * Settings.scale;
        float x = 256f * Settings.scale;
        float y = Settings.HEIGHT * 0.75f;
        for (int i = 0; i < RENDER_COUNT; i++) {
            Vector2 vec = new Vector2(x + offsetH * i, y);
            renderers.add(new RecipeRenderer(vec));
        }
        additionalPos = new Vector2(x + offsetH * RENDER_COUNT, y);
        recipes = new ArrayList<>();
    }

    public void useIngredient(AbstractCard card) {
        if (recipes.size() > 0) {
            recipes.get(0).useIngredient();
        }
    }

    private int addTo(CardGroup group, int count) {
        List<AbstractCard> cards = group.group.stream()
                .filter(c -> !IngredientCardmod.getForCard(c, IngredientCardmod.CARDMOD_ID).isPresent())
                .collect(Collectors.toList());
        Collections.shuffle(cards, AbstractDungeon.cardRandomRng.random);
        cards.stream()
                .limit(count)
                .forEachOrdered(c -> CardModifierManager.addModifier(c, new IngredientCardmod()));
        return cards.size();
    }

    private void addIngredients(int count) {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup cg = p.drawPile;
        count -= addTo(cg, count);
        if (count > 0) {
            cg = p.discardPile;
            count -= addTo(cg, count);
        }
        if (count > 0) {
            cg = ChefMod.frozenPile;
            count -= addTo(cg, count);
        }
        if (count > 0) {
            // TODO: make a custom status instead of slimes
            AbstractCard newCard = CardLibrary.getCard(Slimed.ID).makeCopy();
            CardModifierManager.addModifier(newCard, new IngredientCardmod());
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(newCard, count, true, true));
        }
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

    public void update() {
        renderers.forEach(RecipeRenderer::update);
    }

    public void render(SpriteBatch sb) {
        if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
            for (int i = 0; i < recipes.size() && i < renderers.size(); i++) {
                renderers.get(i).render(sb, recipes.get(i));
            }
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