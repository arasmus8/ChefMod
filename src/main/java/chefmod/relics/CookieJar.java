package chefmod.relics;

import chefmod.TheChef;
import chefmod.powers.HungerDamageUpPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chefmod.ChefMod.makeID;

public class CookieJar extends AbstractChefRelic {
    public static String ID = makeID(CookieJar.class.getSimpleName());

    public CookieJar() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        applyToSelf(new HungerDamageUpPower(AbstractDungeon.player, 10));
    }
}
