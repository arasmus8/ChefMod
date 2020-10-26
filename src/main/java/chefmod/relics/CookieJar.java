package chefmod.relics;

import chefmod.TheChef;
import chefmod.powers.HungerDamageUpPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static chefmod.ChefMod.makeID;

public class CookieJar extends AbstractChefRelic {
    public static String ID = makeID(CookieJar.class.getSimpleName());

    public CookieJar() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        setCounter(counter + 1);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        applyToSelf(new HungerDamageUpPower(AbstractDungeon.player, counter));
    }
}
