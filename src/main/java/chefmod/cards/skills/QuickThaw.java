package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;
import java.util.List;

import static chefmod.ChefMod.makeID;

public class QuickThaw extends AbstractChefCard {
    public static String ID = makeID(QuickThaw.class.getSimpleName());

    public QuickThaw() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.BASIC,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
        nofreeze = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new PlayOldestFrozenCardAction(false));
        }
        addToBot(new FreezeAction(magicNumber));
    }
}
