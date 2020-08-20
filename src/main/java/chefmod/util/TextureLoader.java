package chefmod.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    public static final Logger logger = LogManager.getLogger(TextureLoader.class.getName());

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

    static {
        defaultTexture = new Texture(redPixel());
    }
}
