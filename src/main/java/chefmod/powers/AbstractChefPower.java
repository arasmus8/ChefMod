package chefmod.powers;

import chefmod.ChefMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractChefPower extends AbstractPower {
    private final TextureAtlas powerAtlas = ChefMod.assets.loadAtlas(ChefMod.assetPath("images/powers/powers.atlas"));

    @Override
    protected void loadRegion(String fileName) {
        region48 = powerAtlas.findRegion("32/" + fileName);
        region128 = powerAtlas.findRegion("128/" + fileName);

        if (region48 == null && region128 == null) {
            super.loadRegion(fileName);
        }
    }
}
