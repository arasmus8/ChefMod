package chefmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import java.util.function.Predicate;

public class FunctionalAction extends AbstractGameAction {
    private final Predicate<Boolean> actionFunction;
    private boolean firstUpdate;

    public FunctionalAction(float duration, Predicate<Boolean> actionFunction) {
        this.duration = startDuration = duration;
        this.actionFunction = actionFunction;
        firstUpdate = true;
    }

    public FunctionalAction(Predicate<Boolean> actionFunction) {
        this(Settings.ACTION_DUR_XFAST, actionFunction);
    }

    @Override
    public void update() {
        if (firstUpdate) {
            firstUpdate = false;
            isDone = actionFunction.test(true);
        } else {
            isDone = actionFunction.test(false);
        }
    }
}
