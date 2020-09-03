package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.RetainThisTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class BaoBuns extends AbstractChefCard {
    public static String ID = makeID(BaoBuns.class.getSimpleName());

    public BaoBuns() {
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
