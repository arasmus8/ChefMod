package chefmod.actions;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
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
    private Predicate<AbstractCard> filterCriteria;
    private final Predicate<AbstractCard> noFreezeFilter = c -> !(c instanceof AbstractChefCard) || !(((AbstractChefCard) c).nofreeze);
    private AbstractCard card;

    public FreezeAction(int amount, Predicate<AbstractCard> filterFn) {
        super();
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        startDuration = duration = Settings.ACTION_DUR_FASTER;
        filterCriteria = filterFn;
    }

    public FreezeAction(int amount) {
        this(amount, null);
    }

    public FreezeAction() {
        this(1, null);
    }

    public FreezeAction(AbstractCard specificCard) {
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

    // TODO: add VFX to freezing
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
                    currentGroup.get().moveToBottomOfDeck(card);
                    return;
                } else {
                    AbstractCard newCard = card.makeSameInstanceOf();
                    if (newCard instanceof AbstractChefCard) {
                        ((AbstractChefCard) newCard).frozen = true;
                    }
                    ChefMod.frozenPile.addToBottom(newCard);
                }
            } else {
                List<AbstractCard> eligibleCards = drawPile.group.stream()
                        .filter(noFreezeFilter)
                        .filter(filterCriteria != null ? filterCriteria : c -> true)
                        .collect(Collectors.toList());
                Collections.reverse(eligibleCards);
                eligibleCards.stream()
                        .limit(amount)
                        .forEachOrdered(ChefMod.cardsToFreeze::add);
            }
            ChefMod.cardsToFreeze.forEach(c -> {
                if (c instanceof AbstractChefCard) {
                    ((AbstractChefCard) c).frozen = true;
                    ((AbstractChefCard) c).triggerWhenFrozen();
                }
                drawPile.moveToBottomOfDeck(c);
            });
            ChefMod.cardsToFreeze.clear();
        }
    }
}
