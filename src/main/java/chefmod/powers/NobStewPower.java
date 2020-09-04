package chefmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import chefmod.cardmods.PermanentDamageBonusCardmod;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chefmod.ChefMod.makeID;

public class NobStewPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(NobStewPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    private boolean prioritizePrepped;

    public NobStewPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = 1;
        updateDescription();
        loadRegion("anger");
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            AbstractCard atk = AbstractDungeon.player.hand.getRandomCard(AbstractCard.CardType.ATTACK, true);
            if (atk != null) {
                PermanentDamageBonusCardmod.add(atk, amount);
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new NobStewPower(owner);
    }
}
