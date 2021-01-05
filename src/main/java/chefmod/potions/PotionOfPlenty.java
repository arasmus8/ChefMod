package chefmod.potions;

import basemod.BaseMod;
import chefmod.actions.FunctionalAction;
import chefmod.cardmods.IngredientCardmod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class PotionOfPlenty extends AbstractPotion {

    public static final String POTION_ID = makeID(PotionOfPlenty.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final Color LIQUID_COLOR = new Color(0xDD9900FF);
    public static final Color HYBRID_COLOR = new Color(0xAAB000FF);
    public static final Color SPOTS_COLOR = new Color(0xCCCFC0FF);

    public PotionOfPlenty() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.NONE);
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        if (Settings.language == Settings.GameLanguage.ZHS) {
            tips.add(new PowerTip(
                    BaseMod.getKeywordTitle("chefmod:食材"),
                    BaseMod.getKeywordDescription("chefmod:食材")
            ));
        } else {
            tips.add(new PowerTip(
                    BaseMod.getKeywordTitle("chefmod:ingredient"),
                    BaseMod.getKeywordDescription("chefmod:ingredient")
            ));
        }
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new FunctionalAction(firstUpdate -> {
            CardGroup ingredients = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            ingredients.group.addAll(
                    p.drawPile.group.stream()
                            .filter(c -> IngredientCardmod.getForCard(c, IngredientCardmod.ID).isPresent())
                            .collect(Collectors.toList())
            );
            IntStream.rangeClosed(1, potency)
                    .forEachOrdered(i -> {
                        if (ingredients.size() > 0) {
                            AbstractCard ingredient = ingredients.getRandomCard(true);
                            ingredients.removeCard(ingredient);
                            p.drawPile.moveToHand(ingredient);
                            ingredient.freeToPlayOnce = true;
                        }
                    });
            return true;
        }));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PotionOfPlenty();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
}
