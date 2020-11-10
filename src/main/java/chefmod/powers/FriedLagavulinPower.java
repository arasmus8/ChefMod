package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class FriedLagavulinPower extends AbstractChefPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FriedLagavulinPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int attackUsedThisTurn = 0;

    public FriedLagavulinPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = 1;
        updateDescription();
        loadRegion(this.getClass().getSimpleName());
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        attackUsedThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            attackUsedThisTurn += 1;
            flash();
        }
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return card.type != AbstractCard.CardType.ATTACK || attackUsedThisTurn < amount;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 2;
        }
        return damage;
    }

    @Override
    public AbstractPower makeCopy() {
        return new FriedLagavulinPower(owner);
    }
}
