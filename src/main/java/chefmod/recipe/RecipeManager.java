package chefmod.recipe;

import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;

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
    }

    public void useIngredient(AbstractCard card) {
        if (recipes.size() > 0) {
            recipes.get(0).useIngredient();
        }
    }

    public void startRecipe(AbstractRecipe recipe) {
        recipes.add(recipe);
    }

    public void remove(AbstractRecipe which) {
        recipes.remove(which);
    }

    public void render(SpriteBatch sb) {
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