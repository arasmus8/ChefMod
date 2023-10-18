package chefmod.cards.powers;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.PrepCookPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class PrepCook extends AbstractChefCard {
    public static String ID = makeID(PrepCook.class.getSimpleName());

    public PrepCook() {
        super(ID,
                2,
                CardType.POWER,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PrepCookPower(p, magicNumber, upgraded), upgraded ? -1 * magicNumber : magicNumber));
    }
}
