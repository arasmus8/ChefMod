package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;
import java.util.List;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Defend extends AbstractChefCard {
    public static String ID = makeID(Defend.class.getSimpleName());
    private static final List<CardTags> tagsList = Collections.singletonList(CardTags.STARTER_DEFEND);

    public Defend() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.BASIC,
                CardTarget.SELF,
                tagsList
        );
        baseBlock = block = 5;
        upgradeBlockBy = 3;
        blocks = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }
}
