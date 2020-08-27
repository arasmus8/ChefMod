package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static chefmod.ChefMod.makeID;

public class SearedScallops extends AbstractChefCard {
    public static String ID = makeID(SearedScallops.class.getSimpleName());

    public SearedScallops() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.ALL_ENEMY
        );
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        monsterList().forEach(m -> applyToEnemy(m, new HungerPower(m)));
        if (upgraded) {
            applyToSelf(new DexterityPower(p, magicNumber));
            applyToSelf(new LoseDexterityPower(p, magicNumber));
        }
    }
}
