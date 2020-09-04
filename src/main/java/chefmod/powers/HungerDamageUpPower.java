package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class HungerDamageUpPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(HungerDamageUpPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    public HungerDamageUpPower(AbstractCreature owner, int damageBonus) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damageBonus;
        this.updateDescription();
        this.loadRegion("fasting");
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new HungerDamageUpPower(owner, amount);
    }
}