package chefmod.actions;

import chefmod.ChefMod;
import chefmod.powers.SupercooledPower;
import chefmod.relics.TriggerOnFrozenCardPlayedRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;
import java.util.function.Predicate;

public class PlayOldestFrozenCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private Predicate<AbstractCard> filterFn = null;

    public PlayOldestFrozenCardAction() {
        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.WAIT;
        source = AbstractDungeon.player;
        exhaustCards = false;
    }

    public PlayOldestFrozenCardAction(Predicate<AbstractCard> filterFn) {
        this();
        this.filterFn = filterFn;
    }

    public PlayOldestFrozenCardAction(boolean exhaustCards) {
        this();
        this.exhaustCards = exhaustCards;
    }

    private AbstractCreature getFrozenTarget(AbstractCard card) {
        if (card.target == AbstractCard.CardTarget.ENEMY) {
            Optional<AbstractMonster> supercooled = AbstractDungeon.getMonsters().monsters.stream()
                    .filter(m -> m.hasPower(SupercooledPower.POWER_ID) && !m.isDeadOrEscaped())
                    .findFirst();
            return supercooled.orElseGet(AbstractDungeon::getRandomMonster);
        }
        return null;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            isDone = true;

            Predicate<AbstractCard> f = Optional.ofNullable(filterFn).orElse(c -> true);
            Optional<AbstractCard> c = ChefMod.frozenPile.group.stream()
                    .filter(f)
                    .findFirst();
            c.ifPresent(card -> {
                ChefMod.frozenPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.exhaustOnUseOnce = exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                target = getFrozenTarget(card);
                addToTop(new NewQueueCardAction(card, target, false, true));
                addToTop(new UnlimboAction(card));
                AbstractDungeon.player.relics.stream()
                        .filter(r -> r instanceof TriggerOnFrozenCardPlayedRelic)
                        .map(r -> (TriggerOnFrozenCardPlayedRelic) r)
                        .forEach(r -> r.triggerOnFrozenCardPlayed(card));
                if (!Settings.FAST_MODE) {
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            });
        }

    }
}