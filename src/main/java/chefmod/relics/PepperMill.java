package chefmod.relics;

import chefmod.TheChef;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chefmod.ChefMod.makeID;

public class PepperMill extends AbstractChefRelic implements TriggerOnFrozenCardPlayedRelic {
    public static String ID = makeID(PepperMill.class.getSimpleName());

    public PepperMill() {
        super(ID, RelicTier.BOSS, LandingSound.SOLID, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void triggerOnFrozenCardPlayed(AbstractCard card) {
        flash();
        addToBot(new LoseEnergyAction(1));
    }
}
