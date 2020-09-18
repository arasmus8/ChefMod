package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.actions.FreezeAction;
import chefmod.actions.PlayOldestFrozenCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class OrderUpPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(OrderUpPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    public OrderUpPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("mayhem");
    }

    public void updateDescription() {
        if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + amount;
        } else {
            description = DESCRIPTIONS[0] + amount;
        }
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        IntStream.rangeClosed(1, amount)
                .forEachOrdered(i -> addToBot(new PlayOldestFrozenCardAction()));
        addToBot(new FreezeAction(amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new OrderUpPower(owner, amount);
    }
}