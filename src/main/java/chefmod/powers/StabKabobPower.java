package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EntanglePower;

import static chefmod.ChefMod.makeID;

public class StabKabobPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(StabKabobPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    public StabKabobPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = 1;
        updateDescription();
        this.loadRegion("tools");
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new DrawPileToHandAction(1, AbstractCard.CardType.ATTACK));
    }

    @Override
    public AbstractPower makeCopy() {
        return new StabKabobPower(owner);
    }
}
