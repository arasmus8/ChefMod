package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.actions.HandSelectFunctionalAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;

import static chefmod.ChefMod.makeID;

public class RetainThisTurnPower extends AbstractChefPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(RetainThisTurnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RetainThisTurnPower(AbstractCreature owner, int numCards) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = numCards;
        priority = 3;
        isTurnBased = true;
        updateDescription();
        loadRegion(this.getClass().getSimpleName());
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer &&
                !AbstractDungeon.player.hand.isEmpty() &&
                !AbstractDungeon.player.hasRelic(RunicPyramid.ID) &&
                !AbstractDungeon.player.hasPower(EquilibriumPower.POWER_ID) &&
                AbstractDungeon.player.hand.group.stream().anyMatch(c -> !c.selfRetain && !c.retain && !c.isEthereal)) {
            addToBot(new HandSelectFunctionalAction(Settings.ACTION_DUR_XFAST,
                    amount,
                    selectedCards -> selectedCards.forEach(c -> c.retain = true),
                    c -> !c.selfRetain && !c.retain,
                    DESCRIPTIONS[3]
            ));
        }

        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new RetainThisTurnPower(owner, amount);
    }
}
