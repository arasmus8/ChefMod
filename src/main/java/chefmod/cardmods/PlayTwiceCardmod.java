package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class PlayTwiceCardmod extends AbstractChefCardmod {
    public static String ID = makeID(PlayTwiceCardmod.class.getSimpleName());
    public final boolean permanent;

    public PlayTwiceCardmod(boolean permanent) {
        this.permanent = permanent;
    }

    public static void addToCard(AbstractCard card, boolean permanent) {
        Optional<AbstractChefCardmod> abstractChefCardmod = PlayTwiceCardmod.getForCard(card, PlayTwiceCardmod.ID);
        if (abstractChefCardmod.isPresent()) {
            PlayTwiceCardmod current = (PlayTwiceCardmod) (abstractChefCardmod.get());
            if (permanent && !current.permanent) {
                CardModifierManager.removeModifiersById(card, PlayTwiceCardmod.ID, true);
                CardModifierManager.addModifier(card, new PlayTwiceCardmod(true));
            }
            return;
        }
        CardModifierManager.addModifier(card, new PlayTwiceCardmod(permanent));
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return !permanent;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        // checking dontTriggerOnUseCard prevents a co-modification crash on time eater when playing cards twice from the frozen pile
        if (!card.purgeOnUse && !card.dontTriggerOnUseCard) {
            AbstractCard tmp = card.makeSameInstanceOf();
            GameActionManager.queueExtraCard(tmp, (AbstractMonster) action.target);
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL x2";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PlayTwiceCardmod(permanent);
    }
}
