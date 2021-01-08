package chefmod.recipe;

import basemod.BaseMod;
import basemod.ClickableUIElement;
import basemod.helpers.CardPowerTip;
import chefmod.util.TextureHelper;
import chefmod.util.VfxMaster;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;

import java.util.ArrayList;

public class RecipeRenderer extends ClickableUIElement {
    private static final float COUNT_X = 48.0F * Settings.scale;
    private static final float COUNT_Y = -16.0F * Settings.scale;
    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;
    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;
    private static final float TIP_X = -64f * Settings.scale;
    private static final float SPECIFIC_TIP_Y = -96 * Settings.scale;
    private static final Random rng = new Random(Settings.seed);
    private final BobEffect bob;

    public RecipeRenderer(Vector2 offset) {
        super(VfxMaster.RECIPE,
                offset.x / Settings.scale,
                offset.y / Settings.scale,
                VfxMaster.RECIPE.packedWidth,
                VfxMaster.RECIPE.packedHeight);
        angle = rng.random(-5f, 5f);
        bob = new BobEffect(rng.random(2f, 6f), rng.random(0.6f, 1.4f));
    }

    @Override
    public void update() {
        super.update();
        bob.update();
    }

    public void render(SpriteBatch sb, AbstractRecipe recipe) {
        sb.setColor(Color.WHITE);
        TextureHelper.drawScaledAndRotated(sb, region, hitbox.cX, hitbox.cY + bob.y, 1f, angle);
        TextureHelper.draw(sb, ImageMaster.DECK_COUNT_CIRCLE, hitbox.cX + COUNT_OFFSET_X, hitbox.cY + COUNT_OFFSET_Y);
        String msg = Integer.toString(recipe.ingredientCount);
        FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, hitbox.cX + COUNT_X, hitbox.cY + COUNT_Y);

        hitbox.render(sb);
        if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            ArrayList<PowerTip> tips = new ArrayList<>();
            recipe.card.drawScale = 0.75f;
            String key;
            if (Settings.language == Settings.GameLanguage.ZHS) {
                key = "chefmod:配方";
            } else {
                key = "chefmod:recipe";
            }
            String title = BaseMod.getKeywordTitle(key);
            String body = BaseMod.getKeywordDescription(key);
            tips.add(new CardPowerTip(recipe.card, title, body));
            TipHelper.queuePowerTips(hitbox.cX + TIP_X, hitbox.cY + SPECIFIC_TIP_Y, tips);
        }
    }

    @Override
    protected void onHover() {
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
    }
}
