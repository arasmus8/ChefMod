package chefmod.cards.skills;

import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class NewMenu extends AbstractChefCard {
    public static String ID = makeID(NewMenu.class.getSimpleName());

    public NewMenu() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        FunctionalAction followupAction = new FunctionalAction(firstUpdate -> {
            long nonAttacks = DrawCardAction.drawnCards.stream()
                    .filter(c -> c.type != CardType.ATTACK)
                    .count();
            if (upgraded && nonAttacks > 0) {
                IntStream.rangeClosed(1, (int) nonAttacks)
                        .forEachOrdered(i -> monsterList().forEach(monster -> applyToEnemy(monster, new HungerPower(monster))));
            } else if (nonAttacks == DrawCardAction.drawnCards.size()) {
                monsterList().forEach(monster -> applyToEnemy(monster, new HungerPower(monster)));
            }
            return true;
        });
        addToBot(new DrawCardAction(magicNumber, followupAction));
    }
}
