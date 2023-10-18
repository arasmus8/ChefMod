package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.cards.powers.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class OldCookbook extends AbstractChefCard {
    public static String ID = makeID(OldCookbook.class.getSimpleName());

    public OldCookbook() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        CardGroup recipes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        recipes.addToBottom(new BananaSplit());
        recipes.addToBottom(new Escargot());
        recipes.addToBottom(new FiveAlarmChili());
        recipes.addToBottom(new FullCourseMeal());
        recipes.addToBottom(new LocoMoco());
        recipes.addToBottom(new SushiPlatter());
        AbstractCard card = recipes.getRandomCard(true);
        if (upgraded) {
            card.setCostForTurn(0);
        }
        makeInHand(card);
    }
}
