package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.recipe.RecipeManager;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static chefmod.ChefMod.makeID;

public class TodaysSpecial extends AbstractChefCard {
    public static String ID = makeID(TodaysSpecial.class.getSimpleName());
    private boolean effectTriggered = false;

    public TodaysSpecial() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.BASIC,
                CardTarget.ENEMY
        );
        upgradeCostTo = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new HungerPower(m));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if (!effectTriggered) {
            effectTriggered = true;
            this.superFlash();
            addToBot(new ChooseOneAction(getChoices()));
        }
    }

    private ArrayList<AbstractCard> getChoices() {
        List<String> unlockedIdsCopy = new ArrayList<>(RecipeManager.unlockedRecipes);
        Collections.shuffle(unlockedIdsCopy, AbstractDungeon.cardRandomRng.random);
        return unlockedIdsCopy.stream()
                .limit(3)
                .map(RecipeManager::cardFromId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}