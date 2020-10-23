package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class FreezeSolidPower extends AbstractChefPower implements CloneablePowerInterface, TriggerOnFrozenPower {
    public static final String POWER_ID = makeID(FreezeSolidPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    public FreezeSolidPower(AbstractCreature owner, int blockAmount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = blockAmount;
        updateDescription();
        loadRegion(this.getClass().getSimpleName());
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FreezeSolidPower(owner, amount);
    }

    @Override
    public void triggerOnFrozen(AbstractCard card) {
        addToBot(new GainBlockAction(owner, amount));
    }
}
