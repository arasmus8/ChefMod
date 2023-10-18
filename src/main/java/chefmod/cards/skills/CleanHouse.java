package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class CleanHouse extends AbstractChefCard {
    public static String ID = makeID(CleanHouse.class.getSimpleName());

    public CleanHouse() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseBlock = block = 8;
        upgradeBlockBy = 3;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        addToBot(new FreezeAction(magicNumber, c -> c.type == CardType.CURSE || c.type == CardType.STATUS));
    }
}
