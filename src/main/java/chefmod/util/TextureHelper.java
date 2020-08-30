package chefmod.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class TextureHelper {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    public static final Logger logger = LogManager.getLogger(TextureHelper.class.getName());

    private static final Texture defaultTexture;

    public static Texture getTexture(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                logger.error("Could not find texture: " + textureString);
                return defaultTexture;
            }
        }
        return textures.get(textureString);
    }

    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        logger.info("ChefMod | Loading Texture: " + textureString);
        Texture texture = new Texture(textureString);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textures.put(textureString, texture);
    }

    private static Pixmap redPixel() {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pm.setColor(0xff0000);
        pm.drawPixel(0, 0);
        return pm;
    }

    public static void draw(SpriteBatch sb, Texture texture, float cX, float cY) {
        float w = texture.getWidth() * Settings.scale;
        float h = texture.getHeight() * Settings.scale;
        float halfW = w / 2f;
        float halfH = h / 2f;
        sb.draw(ImageMaster.DECK_COUNT_CIRCLE,
                cX - halfW,
                cY - halfH,
                halfW,
                halfH,
                w,
                h,
                Settings.scale,
                Settings.scale,
                0f,
                0,
                0,
                (int)w,
                (int)h,
                false,
                false);
    }

    public static void drawScaled(SpriteBatch sb, Texture texture, float cX, float cY, float scale) {
        float w = texture.getWidth() * Settings.scale;
        float h = texture.getHeight() * Settings.scale;
        float halfW = w / 2f;
        float halfH = h / 2f;
        sb.draw(ImageMaster.DECK_COUNT_CIRCLE,
                cX - halfW,
                cY - halfH,
                halfW,
                halfH,
                w,
                h,
                scale * Settings.scale,
                scale * Settings.scale,
                0f,
                0,
                0,
                (int)w,
                (int)h,
                false,
                false);
    }

    static {
        defaultTexture = new Texture(redPixel());
    }
}