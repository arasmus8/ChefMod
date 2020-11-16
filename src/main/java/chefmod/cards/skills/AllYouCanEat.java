package chefmod.cards.skills;

import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.SatiatedPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class AllYouCanEat extends AbstractChefCard {
    public static String ID = makeID(AllYouCanEat.class.getSimpleName());

    public AllYouCanEat() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new DrawCardAction(p, 1));
        }
        addToBot(new FunctionalAction(firstUpdate -> {
            int attackCount = p.hand.getCardsOfType(CardType.ATTACK).size();
            if (attackCount > 0) {
                IntStream.rangeClosed(1, attackCount).forEachOrdered(i ->
                        applyToEnemy(m, new SatiatedPower(m))
                );
            }
            return true;
        }));
    }
}
