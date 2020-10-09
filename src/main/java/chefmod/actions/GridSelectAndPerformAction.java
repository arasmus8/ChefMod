package chefmod.actions;

import chefmod.ChefMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class GridSelectAndPerformAction extends AbstractGameAction {
    public static ArrayList<AbstractCard> selectedCards = new ArrayList<>();
    private Predicate<AbstractCard> filterCriteria;
    private CardGroup group;
    private final AbstractGameAction followUpAction;
    private boolean clearSelectedCards;
    private final boolean isRandom;
    private static AbstractPlayer p;
    private static final float DURATION;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static final String ID = makeID(GridSelectAndPerformAction.class.getSimpleName());

    public GridSelectAndPerformAction(
            int amount,
            boolean random,
            CardGroup group,
            Predicate<AbstractCard> filterCriteria,
            AbstractGameAction followUpAction
    ) {
        p = AbstractDungeon.player;
        setValues(p, p, amount);
        actionType = ActionType.CARD_MANIPULATION;
        duration = DURATION;
        this.followUpAction = followUpAction;
        isRandom = random;
        clearSelectedCards = true;
        this.group = group;
        this.filterCriteria = filterCriteria;
    }

    public GridSelectAndPerformAction(int amount, AbstractGameAction action) {
        this(amount, false, ChefMod.frozenPile, null, action);
    }

    @Override
    public void update() {
        if (duration == DURATION) {
            if (clearSelectedCards) {
                clearSelectedCards = false;
                selectedCards.clear();
            }

            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                isDone = true;
                return;
            }

            if (group == null) {
                group = ChefMod.frozenPile;
            }

            List<AbstractCard> filteredList = group.group.stream()
                    .filter(Optional.ofNullable(filterCriteria).orElseGet(() -> abstractCard -> true))
                    .collect(Collectors.toList());

            if (filteredList.size() <= amount) {
                selectedCards.addAll(filteredList);
                p.hand.applyPowers();
                endActionWithFollowUp();
                return;
            }

            if (!isRandom) {
                if (group == p.hand) {
                    if (amount < 0) {
                        AbstractDungeon.handCardSelectScreen.open("", 99, true, true);
                        p.hand.applyPowers();
                        tickDuration();
                        return;
                    }

                    if (p.hand.size() > amount) {
                        AbstractDungeon.handCardSelectScreen.open("", amount, false);
                        p.hand.applyPowers();
                        tickDuration();
                        return;
                    }
                } else {
                    CardGroup filtered = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    filtered.group.addAll(filteredList);
                    if (amount < 0) {
                        AbstractDungeon.gridSelectScreen.open(filtered, 99, true, TEXT[1] + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(filtered, amount, true, TEXT[0] + amount + TEXT[2]);
                    }
                    tickDuration();
                    return;
                }
            } else {
                CardGroup filtered = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                filtered.group.addAll(filteredList);
                IntStream.rangeClosed(1, amount)
                        .forEach(i -> {
                            AbstractCard card = filtered.getRandomCard(AbstractDungeon.cardRandomRng);
                            filtered.removeCard(card);
                            selectedCards.add(card);
                        });
                endActionWithFollowUp();
            }
        }

        if (group == p.hand) {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                selectedCards.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
                group.group.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                p.hand.applyPowers();
                endActionWithFollowUp();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                selectedCards.addAll(AbstractDungeon.gridSelectScreen.selectedCards);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                p.hand.refreshHandLayout();
                p.hand.applyPowers();
                endActionWithFollowUp();
            }
        }

        tickDuration();
    }

    private void endActionWithFollowUp() {
        isDone = true;
        if (followUpAction != null) {
            addToTop(followUpAction);
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
