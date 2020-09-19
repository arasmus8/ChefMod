package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.ChefMod;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class TastyDistractionPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(TastyDistractionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    private int blockedThisTurn = 99;

    public TastyDistractionPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("buffer");
    }

    public void updateDescription() {
        if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0];
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && blockedThisTurn < amount && ChefMod.frozenPile.size() > 0) {
            this.flashWithoutSound();
            blockedThisTurn += 1;
            AbstractCard oldestFrozen = ChefMod.frozenPile.getBottomCard();
            addToTop(new ExhaustSpecificCardAction(oldestFrozen, ChefMod.frozenPile));
            return 0;
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        blockedThisTurn = 0;
    }

    @Override
    public AbstractPower makeCopy() {
        return new TastyDistractionPower(owner, amount);
    }
}