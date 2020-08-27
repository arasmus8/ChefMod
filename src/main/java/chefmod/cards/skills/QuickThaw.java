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
    private static final List<CardTags> tagsList = Collections.singletonList(CardTags.STARTER_DEFEND);

    public QuickThaw() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.BASIC,
                CardTarget.SELF,
                tagsList
        );
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new PlayOldestFrozenCardAction(false));
        }
        addToBot(new FreezeAction(magicNumber));
    }
}
