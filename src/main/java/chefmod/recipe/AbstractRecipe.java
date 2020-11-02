package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.util.ActionUnit;
import chefmod.util.VfxMaster;
import chefmod.vfx.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.function.BiFunction;

public abstract class AbstractRecipe implements ActionUnit {
    public String tipHeader;
    public String tipBody;
    public AbstractCard card;
    private static final float foodX = Settings.WIDTH / 2f;
    private static final float foodY = Settings.HEIGHT / 2f;

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

    public int ingredientCount() {
        return ingredientCount;
    }

    public void renderFood(TextureAtlas.AtlasRegion image) {
        if (!ChefMod.cheesyRecipeAnimations) {
            return;
        }

        BiFunction<Float, Float, AbstractGameEffect> foodEffect = (x, y) -> new VfxBuilder(image, x, y, 1.25f)
                .scale(.8f, 1f, VfxBuilder.Interpolations.SWING)
                .fadeIn(0.25f)
                .fadeOut(0.1f)
                .build();

        BiFunction<Float, Float, AbstractGameEffect> sparkle = (x, y) -> new VfxBuilder(ImageMaster.ROOM_SHINE_1, 0.3f)
                .setX(MathUtils.random(-120f, 120f) * Settings.scale + x)
                .setY(MathUtils.random(-90f, 120f) * Settings.scale + y)
                .setColor(new Color(0xFAFAD0FF))
                .scale(0.8f, 0.9f, VfxBuilder.Interpolations.ELASTIC)
                .setAlpha(0.5f)
                .build();

        BiFunction<Float, Float, AbstractGameEffect> tasty = (x, y) -> new VfxBuilder(VfxMaster.TASTY, 1f)
                .setX(x - 75f * Settings.scale)
                .setY(y + 75f * Settings.scale)
                .scale(0f, 0.8f, VfxBuilder.Interpolations.SWING)
                .fadeIn(0.1f)
                .fadeOut(0.1f)
                .build();

        qAction(new VFXAction(
                new VfxBuilder((TextureAtlas.AtlasRegion) null, foodX, foodY, 1.0f)
                        .triggerVfxAt(0f, 1, foodEffect)
                        .triggerVfxAt(0.25f, 1, tasty)
                        .emitEvery(sparkle, 0.08f)
                        .build()
        ));
    }

    @Override
    public DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return null;
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) { }

    @Override
    public void gainBlock() { }
}
