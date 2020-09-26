package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.cards.options.*;
import chefmod.powers.HungerPower;
import chefmod.recipe.*;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static chefmod.ChefMod.makeID;

public class TodaysSpecial extends AbstractChefCard implements StartupCard {
    public static String ID = makeID(TodaysSpecial.class.getSimpleName());
    private static final HashMap<String, Class<? extends AbstractCard>> recipeMap;

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
    public boolean atBattleStartPreDraw() {
        addToBot(new ChooseOneAction(getChoices()));
        return false;
    }

    private AbstractCard cardFromId(String id) {
        if (recipeMap.containsKey(id)) {
            Class<? extends AbstractCard> cls = recipeMap.get(id);
            try {
                return cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new NeowNuggets();
    }

    private ArrayList<AbstractCard> getChoices() {
        List<String> unlockedIdsCopy = new ArrayList<>(RecipeManager.unlockedRecipes);
        Collections.shuffle(unlockedIdsCopy, AbstractDungeon.cardRandomRng.random);
        return unlockedIdsCopy.stream()
                .limit(3)
                .map(this::cardFromId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static {
        recipeMap = new HashMap<>();
        recipeMap.put(NeowNuggetsRecipe.ID, NeowNuggets.class);
        recipeMap.put(NobStewRecipe.ID, NobStew.class);
        recipeMap.put(FriedLagavulinRecipe.ID, FriedLagavulin.class);
        recipeMap.put(SentryBrittleRecipe.ID, SentryBrittle.class);
        recipeMap.put(StabKabobRecipe.ID, StabKabob.class);
        recipeMap.put(SlaverSaladRecipe.ID, SlaverSalad.class);
        recipeMap.put(GremlinGoulashRecipe.ID, GremlinGoulash.class);
        recipeMap.put(NemesisSouffleRecipe.ID, NemesisSouffle.class);
    }
}