package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerDamageUpPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class CandyBowl extends AbstractChefCard {
    public static String ID = makeID(CandyBowl.class.getSimpleName());

    public CandyBowl() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 8;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HungerDamageUpPower(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            exhaust = false;
        }
        super.upgrade();
    }
}
