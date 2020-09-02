package chefmod.recipe;

import basemod.ClickableUIElement;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static chefmod.ChefMod.makeImagePath;

public class RecipeRenderer extends ClickableUIElement {
    private static final Texture recipeCardImage = TextureHelper.getTexture(makeImagePath("vfx/recipe.png"));

    private static final float COUNT_X = 48.0F * Settings.scale;
    private static final float COUNT_Y = -16.0F * Settings.scale;
    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;
    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;
    private static final float DECK_TIP_X = -64f * Settings.scale;
    private static final float DECK_TIP_Y = -96 * Settings.scale;
    private static final Random rng = new Random(Settings.seed);

    public RecipeRenderer(Vector2 offset) {
        super(recipeCardImage, offset.x, offset.y, recipeCardImage.getWidth(), recipeCardImage.getHeight());
        angle = rng.random(-0.1f, 0.1f);
    }

    public void render (SpriteBatch sb, AbstractRecipe recipe) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        TextureHelper.draw(sb, ImageMaster.DECK_COUNT_CIRCLE, hitbox.cX + COUNT_OFFSET_X, hitbox.cY + COUNT_OFFSET_Y);
        String msg = Integer.toString(recipe.ingredientCount);
        FontHelper.renderFontCentered(sb, FontHelper.speech_font, msg, hitbox.cX + COUNT_X, hitbox.cY + COUNT_Y);

        hitbox.render(sb);
        if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(hitbox.cX + DECK_TIP_X, hitbox.cY + DECK_TIP_Y, recipe.tipHeader, recipe.tipBody);
        }
    }

    @Override
    protected void onHover() { }

    @Override
    protected void onUnhover() { }

    @Override
    protected void onClick() { }
}
