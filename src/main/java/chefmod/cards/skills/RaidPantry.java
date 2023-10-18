package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class RaidPantry extends AbstractChefCard {
    public static String ID = makeID(RaidPantry.class.getSimpleName());

    public RaidPantry() {
        super(ID,
                2,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        upgradeCostTo = 1;
        nofreeze = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        List<AbstractCard> attacks = ChefMod.frozenPile.group.stream()
                .filter(c -> c.type == CardType.ATTACK)
                .collect(Collectors.toList());
        ChefMod.frozenPile.group.removeIf(attacks::contains);
        Collections.shuffle(attacks, AbstractDungeon.cardRandomRng.random);
        attacks.forEach(c -> {
            ChefMod.frozenPile.addToBottom(c);
            addToBot(new PlayOldestFrozenCardAction());
        });
    }
}
