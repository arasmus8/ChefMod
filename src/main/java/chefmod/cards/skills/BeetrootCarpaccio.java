package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.RetainThisTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import static chefmod.ChefMod.makeID;

public class BeetrootCarpaccio extends AbstractChefCard {
    public static String ID = makeID(BeetrootCarpaccio.class.getSimpleName());

    public BeetrootCarpaccio() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        baseBlock = block = 6;
        magicNumber = baseMagicNumber = 1;
        upgradeBlockBy = 3;
        blocks = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        applyToSelf(new RetainThisTurnPower(p, magicNumber));
    }
}
