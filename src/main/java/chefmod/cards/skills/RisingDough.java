package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class RisingDough extends AbstractChefCard {
    public static String ID = makeID(RisingDough.class.getSimpleName());

    public RisingDough() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new PutOnDeckAction(p, p, 1, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            exhaust = false;
        }
        super.upgrade();
    }
}
