package chefmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HandSelectFunctionalAction extends AbstractGameAction {
    private final Consumer<List<AbstractCard>> actionFunction;
    private final Predicate<AbstractCard> filter;
    private final String message;

    private final ArrayList<AbstractCard> originalHand;

    public HandSelectFunctionalAction(float duration,
                                      int amount,
                                      Consumer<List<AbstractCard>> actionFunction,
                                      Predicate<AbstractCard> filter,
                                      String message) {
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.duration = startDuration = duration;
        this.actionFunction = actionFunction;
        this.filter = filter;
        this.message = message;
        actionType = ActionType.CARD_MANIPULATION;
        originalHand = new ArrayList<>();
    }

    public HandSelectFunctionalAction(int amount, Consumer<List<AbstractCard>> actionFunction, String message) {
        this(Settings.ACTION_DUR_XFAST, amount, actionFunction, Objects::nonNull, message);
    }

    public HandSelectFunctionalAction(Consumer<List<AbstractCard>> actionFunction, String message) {
        this(Settings.ACTION_DUR_XFAST, 1, actionFunction, Objects::nonNull, message);
    }

    @Override
    public void update() {
        CardGroup hand = AbstractDungeon.player.hand;
        List<AbstractCard> eligible = hand.group.stream().filter(filter).collect(Collectors.toList());
        if (duration == startDuration) {
            if (eligible.size() <= amount) {
                isDone = true;
                if (eligible.size() > 0) {
                    actionFunction.accept(eligible);
                }
            }
            originalHand.addAll(hand.group);

            hand.group.clear();
            hand.group.addAll(eligible);
            AbstractDungeon.handCardSelectScreen.open(message, amount, false);
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                // Restore original hand positions
                hand.clear();
                hand.group.addAll(originalHand);
                hand.refreshHandLayout();
                actionFunction.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group);
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                isDone = true;
            }
        }
        tickDuration();
    }
}
