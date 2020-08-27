package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.powers.SatiatedPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import static chefmod.ChefMod.makeID;

public class AllYouCanEat extends AbstractChefCard {
    public static String ID = makeID(AllYouCanEat.class.getSimpleName());

    public AllYouCanEat() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        upgradeCostTo = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int attackCount = p.hand.getCardsOfType(CardType.ATTACK).size();
        for (; attackCount > 0; attackCount -= 1) {
            applyToEnemy(m, new SatiatedPower(m));
        }
    }
}
