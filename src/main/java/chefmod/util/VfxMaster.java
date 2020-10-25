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

    public static void initialize() {
        vfxAtlas = ChefMod.assets.loadAtlas(assetPath("images/vfx/vfx.atlas"));
        CHOCOLATE = vfxAtlas.findRegion("chocolate");
        FROZEN_CARD_VFX = vfxAtlas.findRegion("frozenCard");
        FROZEN_TUNA = vfxAtlas.findRegion("frozenTuna");
        INGREDIENT_BADGE = vfxAtlas.findRegion("ingredientBadge");
        RECIPE = vfxAtlas.findRegion("recipe");
    }
}
