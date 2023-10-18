package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.actions.FunctionalAction;
import chefmod.cardmods.OneTimeBlockBonusCardmod;
import chefmod.cardmods.OneTimeDamageBonusCardmod;
import chefmod.cardmods.PermanentBlockBonusCardmod;
import chefmod.cardmods.PermanentDamageBonusCardmod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class PrepCookPower extends AbstractChefTwoAmountPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(PrepCookPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PrepCookPower(AbstractCreature owner, int powerBonus, boolean permanent) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        if (permanent) {
            amount2 = powerBonus;
            amount = 0;
        } else {
            amount = powerBonus;
            amount2 = 0;
        }
        this.updateDescription();
        loadRegion(this.getClass().getSimpleName());
    }

    public void updateDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(DESCRIPTIONS[0]);
        if (amount > 0 && amount2 > 0) {
            desc.append(amount);
            desc.append(DESCRIPTIONS[1]);
            desc.append(DESCRIPTIONS[2]);
            desc.append(DESCRIPTIONS[4]);
            desc.append(amount2);
            desc.append(DESCRIPTIONS[1]);
            desc.append(DESCRIPTIONS[3]);
        } else if (amount2 > 0) {
            desc.append(amount2);
            desc.append(DESCRIPTIONS[1]);
            desc.append(DESCRIPTIONS[3]);
        } else if (amount > 0) {
            desc.append(amount);
            desc.append(DESCRIPTIONS[1]);
            desc.append(DESCRIPTIONS[2]);
        }
        desc.append(".");
        description = desc.toString();
    }

    @Override
    public void stackPower(int stackAmount) {
        if (stackAmount > 0) {
            amount += stackAmount;
        } else if (stackAmount < 0) {
            amount2 -= stackAmount;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        final int oneTimeAmount = amount;
        final int permanentAmount = amount2;

        addToBot(new FunctionalAction(first -> {
            AbstractDungeon.player.hand.group.stream()
                    .filter(c -> c.selfRetain || c.retain)
                    .forEach(c -> {
                        flash();
                        if (oneTimeAmount > 0) {
                            OneTimeDamageBonusCardmod.add(c, oneTimeAmount);
                            OneTimeBlockBonusCardmod.add(c, oneTimeAmount);
                        }

                        if (permanentAmount > 0) {
                            PermanentDamageBonusCardmod.add(c, permanentAmount);
                            PermanentBlockBonusCardmod.add(c, permanentAmount);
                        }
                    });
            return true;
        }));
    }

    @Override
    public AbstractPower makeCopy() {
        PrepCookPower copy = new PrepCookPower(owner, amount, false);
        if (amount2 > 0) {
            copy.stackPower(-1 * amount2);
        }
        return copy;
    }
}