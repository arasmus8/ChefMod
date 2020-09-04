package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.actions.FunctionalAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;

import static chefmod.ChefMod.makeID;

public class RetainThisTurnPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(RetainThisTurnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    public RetainThisTurnPower(AbstractCreature owner, int numCards) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = numCards;
        priority = 3;
        isTurnBased = true;
        updateDescription();
        loadRegion("retain");
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
                AbstractDungeon.player.hand.group.stream().anyMatch(c -> !c.selfRetain && !c.retain)) {
            addToBot(new FunctionalAction(firstUpdate -> {
                if (firstUpdate) {
                    if (AbstractDungeon.player.hand.group.stream().anyMatch(c -> !c.selfRetain && !c.retain)) {
                        AbstractDungeon.handCardSelectScreen.open(DESCRIPTIONS[3], this.amount, false, true, false, false, true);
                        this.addToBot(new WaitAction(0.25F));
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                        AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(c -> {
                            AbstractDungeon.player.hand.addToTop(c);
                            if (!c.isEthereal) {
                                c.retain = true;
                            }
                        });
                        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                        return true;
                    }
                }
                return false;
            }));
        }

        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new RetainThisTurnPower(owner, amount);
    }
}
