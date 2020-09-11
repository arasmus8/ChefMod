package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class FreezeNextCardPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FreezeNextCardPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    public FreezeNextCardPower(AbstractCreature owner, int numCards) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = numCards;
        isTurnBased = true;
        updateDescription();
        loadRegion("burst");
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && !card.exhaust && !AbstractChefCard.isNoFreeze(card) && amount > 0) {
            ChefMod.cardsToFreeze.add(card);
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new FreezeNextCardPower(owner, amount);
    }
}