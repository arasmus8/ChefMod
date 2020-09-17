package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.SatiatedPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class PotatoGratin extends AbstractChefCard {
    public static String ID = makeID(PotatoGratin.class.getSimpleName());

    public PotatoGratin() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseBlock = block = 10;
        upgradeBlockBy = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        monsterList().forEach(monster -> applyToEnemy(monster, new SatiatedPower(monster)));
    }
}
