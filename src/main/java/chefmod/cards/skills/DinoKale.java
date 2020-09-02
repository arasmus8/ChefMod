package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.RetainThisTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static chefmod.ChefMod.makeID;

public class DinoKale extends AbstractChefCard {
    public static String ID = makeID(DinoKale.class.getSimpleName());

    public DinoKale() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        baseBlock = block = 5;
        upgradeBlockBy = 3;
        blocks = true;
        hasPreppedActions = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        if (isPrepped()) {
            applyToSelf(new NextTurnBlockPower(p, block));
        }
    }
}
