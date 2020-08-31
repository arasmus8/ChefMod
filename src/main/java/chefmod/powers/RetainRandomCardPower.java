package chefmod.powers;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static chefmod.ChefMod.makeID;

public class RetainRandomCardPower extends AbstractPower {
    public static final String POWER_ID = makeID(RetainRandomCardPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // private static final Texture texture = TextureLoader.getTexture(makePowerPath("prep-cook.png"));

    private boolean prioritizePrepped;

    public RetainRandomCardPower(AbstractCreature owner, int numCards, boolean prioritizePrepped) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.prioritizePrepped = prioritizePrepped;
        amount = numCards;
        updateDescription();
        loadRegion("retain");
    }

    public void upgrade() {
        prioritizePrepped = true;
        updateDescription();
    }

    public static boolean stackPower(int numCards, boolean prioritizePrepped) {
        AbstractPower current = AbstractDungeon.player.getPower(POWER_ID);
        if (current == null) {
            return false;
        }

        current.amount += numCards;
        if (prioritizePrepped) {
            ((RetainRandomCardPower) current).upgrade();
        }
        return true;
    }

    public void updateDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(DESCRIPTIONS[0]);
        desc.append(amount);
        if (amount == 1) {
            desc.append(DESCRIPTIONS[1]);
        } else {
            desc.append(DESCRIPTIONS[2]);
        }
        if (prioritizePrepped) {
            desc.append(DESCRIPTIONS[3]);
        } else {
            desc.append(".");
        }
        description = desc.toString();
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup hand = p.hand;
        if (isPlayer &&
                !hand.isEmpty() &&
                !p.hasRelic(RunicPyramid.ID) &&
                !p.hasPower(EquilibriumPower.POWER_ID)) {
            Predicate<AbstractCard> preppedFilter = c -> (c instanceof AbstractChefCard) && ((AbstractChefCard) c).hasPreppedActions;
            Predicate<AbstractCard> notAlreadyRetained = c -> !c.retain && !c.selfRetain && !c.isEthereal;
            Predicate<AbstractCard> notStatusOrCurse = c -> c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE;
            if (hand.group.stream().anyMatch(notAlreadyRetained)) {
                int cardsToRetain = amount;
                if (prioritizePrepped) {
                    List<AbstractCard> preppedCards = hand.group.stream()
                            .filter(preppedFilter)
                            .collect(Collectors.toList());
                    Collections.shuffle(preppedCards, AbstractDungeon.cardRandomRng.random);
                    preppedCards.stream()
                            .limit(cardsToRetain)
                            .forEachOrdered(c -> c.retain = true);
                    cardsToRetain -= preppedCards.size();
                }

                if (cardsToRetain > 0) {
                    List<AbstractCard> cardsInHand = hand.group.stream()
                            .filter(notAlreadyRetained)
                            .collect(Collectors.toList());
                    Collections.shuffle(cardsInHand, AbstractDungeon.cardRandomRng.random);
                    cardsInHand.stream()
                            .limit(cardsToRetain)
                            .forEachOrdered(c -> c.retain = true);
                }
            }
        }
    }
}
