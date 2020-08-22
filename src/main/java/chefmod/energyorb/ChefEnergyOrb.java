package chefmod.energyorb;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ChefEnergyOrb extends CustomEnergyOrb {
    private static final float ORB_SCALE = 0.85F;
    private final Texture background;
    private static final Color whiteAlpha80 = new Color(1f, 1f, 1f, 0.8f);

    public ChefEnergyOrb() {
        super(null, null, null);
        background = ImageMaster.loadImage("chefmodResources/images/character/EnergyOrb.png");
        orbVfx = ImageMaster.loadImage("chefmodResources/images/character/vfx.png");
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float x, float y) {
        if (enabled) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(whiteAlpha80);
        }
        sb.draw(this.background,
                x - 64.0F,
                y - 64.0F,
                64.0F,
                64.0F,
                128.0F,
                128.0F,
                ORB_SCALE,
                ORB_SCALE,
                0,
                0,
                0,
                128,
                128,
                false,
                false);
    }

    @Override
    public void updateOrb(int energyCount) {
    }
}
