package chefmod.cards.skills;

import chefmod.actions.FunctionalAction;
import chefmod.cardmods.IngredientCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class FarmersMarket extends AbstractChefCard {
    public static String ID = makeID(FarmersMarket.class.getSimpleName());

    public FarmersMarket() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 1;
        upgradeMagicNumberBy = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FunctionalAction(firstUpdate -> {
            CardGroup ingredients = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            ingredients.group.addAll(
                    p.drawPile.group.stream()
                            .filter(c -> IngredientCardmod.getForCard(c, IngredientCardmod.ID).isPresent())
                            .collect(Collectors.toList())
            );
            IntStream.rangeClosed(1, magicNumber)
                    .forEachOrdered(i -> {
                        if (ingredients.size() > 0) {
                            AbstractCard ingredient = ingredients.getRandomCard(true);
                            ingredients.removeCard(ingredient);
                            p.drawPile.moveToHand(ingredient);
                            if (upgraded) {
                                ingredient.freeToPlayOnce = true;
                            }
                        }
                    });
            return true;
        }));
    }
}
