package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class SatiatedPower extends AbstractChefPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(SatiatedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int DAMAGE_REDUCTION = 5;

    public SatiatedPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = -1;
        isTurnBased = true;
        type = PowerType.DEBUFF;
        updateDescription();
        loadRegion(this.getClass().getSimpleName());
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2f : damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2f : damage;
    }

    @Override
    public void stackPower(int stackAmount) {
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SatiatedPower(owner);
    }
}
