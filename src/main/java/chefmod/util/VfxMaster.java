package chefmod.util;

import chefmod.ChefMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static chefmod.ChefMod.assetPath;

public class VfxMaster {
    public static TextureAtlas vfxAtlas;
    public static TextureAtlas.AtlasRegion CHOCOLATE;
    public static TextureAtlas.AtlasRegion FROZEN_CARD_VFX;
    public static TextureAtlas.AtlasRegion FROZEN_TUNA;
    public static TextureAtlas.AtlasRegion INGREDIENT_BADGE;
    public static TextureAtlas.AtlasRegion RECIPE;
    public static TextureAtlas.AtlasRegion TASTY;
    public static TextureAtlas.AtlasRegion NUGGETS;
    public static TextureAtlas.AtlasRegion BANANA_SPLIT;
    public static TextureAtlas.AtlasRegion CHILI;
    public static TextureAtlas.AtlasRegion ESCARGOT;
    public static TextureAtlas.AtlasRegion FRIED_LAGAVULIN;
    public static TextureAtlas.AtlasRegion FULL_COURSE_MEAL;
    public static TextureAtlas.AtlasRegion GIANT_MARBLE_CAKE;
    public static TextureAtlas.AtlasRegion GREMLIN_GOULASH;

    public static void initialize() {
        vfxAtlas = ChefMod.assets.loadAtlas(assetPath("images/vfx/vfx.atlas"));
        CHOCOLATE = vfxAtlas.findRegion("chocolate");
        FROZEN_CARD_VFX = vfxAtlas.findRegion("frozenCard");
        FROZEN_TUNA = vfxAtlas.findRegion("frozenTuna");
        INGREDIENT_BADGE = vfxAtlas.findRegion("ingredientBadge");
        RECIPE = vfxAtlas.findRegion("recipe");
        TASTY = vfxAtlas.findRegion("tasty");
        NUGGETS = vfxAtlas.findRegion("nuggets");
        BANANA_SPLIT = vfxAtlas.findRegion("bananaSplit");
        CHILI = vfxAtlas.findRegion("chili");
        ESCARGOT = vfxAtlas.findRegion("escargot");
        FRIED_LAGAVULIN = vfxAtlas.findRegion("friedLagavulin");
        FULL_COURSE_MEAL = vfxAtlas.findRegion("fullCourseMeal");
        GIANT_MARBLE_CAKE = vfxAtlas.findRegion("giantMarbleCake");
        GREMLIN_GOULASH = vfxAtlas.findRegion("gremlinGoulash");
    }
}
