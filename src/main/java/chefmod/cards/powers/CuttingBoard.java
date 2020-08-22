package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class CuttingBoard extends AbstractChefCard {
    public static String ID = makeID(CuttingBoard.class.getSimpleName());

    public CuttingBoard() {
        super(ID,
                1,
                CardType.POWER,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, magicNumber));
    }
}
