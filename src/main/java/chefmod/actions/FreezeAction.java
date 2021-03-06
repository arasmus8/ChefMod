package chefmod.actions;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.TriggerOnFrozenPower;
import chefmod.vfx.FrozenCardVfx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FreezeAction extends AbstractGameAction {
    private final Predicate<AbstractCard> filterCriteria;
    private final AbstractGameAction followUpAction;
    public static final ArrayList<AbstractCard> frozenCards = new ArrayList<>();
    private final Predicate<AbstractCard> noFreezeFilter = c -> !(c instanceof AbstractChefCard) || !(((AbstractChefCard) c).nofreeze);
    private AbstractCard card;

    public FreezeAction(int amount, Predicate<AbstractCard> filterFn, AbstractGameAction followUpAction) {
        super();
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        startDuration = duration = Settings.ACTION_DUR_FASTER;
        filterCriteria = filterFn;
        this.followUpAction = followUpAction;
    }

    public FreezeAction(int amount, Predicate<AbstractCard> filterFn) {
        this(amount, filterFn, null);
    }

    public FreezeAction(int amount) {
        this(amount, null, null);
    }

    public FreezeAction(AbstractCard specificCard) {
        this(-1, null, null);
        card = specificCard;
    }

    private static Optional<CardGroup> findCurrentGroup(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup[] groups = {
                ChefMod.frozenPile,
                p.drawPile,
                p.hand,
                p.discardPile,
                p.limbo
        };
        return Arrays.stream(groups).filter(cg -> cg.contains(card)).findFirst();
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            isDone = true;

            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                return;
            }

            CardGroup drawPile = AbstractDungeon.player.drawPile;
            if (card != null) {
                Optional<CardGroup> currentGroup = findCurrentGroup(card);
                if (currentGroup.isPresent()) {
                    ChefMod.cardsToFreeze.add(card);
                    currentGroup.get().moveToDeck(card, false);
                    ChefMod.cardsToFreeze.remove(card);
                } else {
                    AbstractCard newCard = card.makeSameInstanceOf();
                    if (newCard instanceof AbstractChefCard) {
                        ((AbstractChefCard) newCard).frozen = true;
                    }
                    ChefMod.frozenPile.addToTop(newCard);
                    ChefMod.frozenThisCombat.add(newCard.makeStatEquivalentCopy());
                    AbstractDungeon.effectList.add(new FrozenCardVfx(newCard));
                    AbstractDungeon.player.powers.stream()
                            .filter(p -> p instanceof TriggerOnFrozenPower)
                            .map(p -> (TriggerOnFrozenPower) p)
                            .forEach(p -> p.triggerOnFrozen(newCard));
                }
            } else {
                frozenCards.clear();
                List<AbstractCard> eligibleCards = drawPile.group.stream()
                        .filter(noFreezeFilter)
                        .filter(Optional.ofNullable(filterCriteria).orElseGet(() -> c -> true))
                        .collect(Collectors.toList());
                Collections.reverse(eligibleCards);
                eligibleCards.stream()
                        .limit(amount)
                        .forEachOrdered(c -> {
                            ChefMod.cardsToFreeze.add(c);
                            frozenCards.add(c);
                        });
                frozenCards.forEach(c -> drawPile.moveToDeck(c, false));
                ChefMod.cardsToFreeze.removeIf(frozenCards::contains);
                endActionWithFollowUp();
            }
        }
    }

    private void endActionWithFollowUp() {
        isDone = true;
        if (followUpAction != null) {
            addToTop(followUpAction);
        }
    }

}
