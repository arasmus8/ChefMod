package chefmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import java.util.function.Function;

public class FunctionalAction extends AbstractGameAction {
    private final Function<Boolean, Boolean> actionFunction;

    public FunctionalAction(float duration, Function<Boolean, Boolean> actionFunction) {
        this.duration = startDuration = duration;
        this.actionFunction = actionFunction;
    }

    public FunctionalAction(Function<Boolean, Boolean> actionFunction) {
        this(Settings.ACTION_DUR_XFAST, actionFunction);
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            isDone = actionFunction.apply(true);
        } else {
            isDone = actionFunction.apply(false);
        }
        tickDuration();
    }
}
