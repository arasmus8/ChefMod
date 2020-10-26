package chefmod.relics;

import chefmod.TheChef;
import chefmod.actions.FreezeAction;

import static chefmod.ChefMod.makeID;

public class RollingPin extends AbstractChefRelic {
    public static String ID = makeID(RollingPin.class.getSimpleName());

    public RollingPin() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void atBattleStart() {
        addToBot(new FreezeAction(3));
    }
}
