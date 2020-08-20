package chefmod;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import chefmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class ChefMod implements PostInitializeSubscriber {
    public static String IMAGE_PATH = "chefmodResources/images";

    public ChefMod() {
        BaseMod.subscribe(this);
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        ChefMod chefMod = new ChefMod();
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeImage = TextureLoader.getTexture(IMAGE_PATH + "/Badge.png");
        BaseMod.registerModBadge(badgeImage, "ChefMod", "NotInTheFace", "A character who's a chef.", null);
    }
}
