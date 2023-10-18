package chefmod.cards.skills;

import chefmod.actions.HandSelectFunctionalAction;
import chefmod.cardmods.OmeletteCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Omelette extends AbstractChefCard {
    public static String ID = makeID(Omelette.class.getSimpleName());

    public Omelette() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 5;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectFunctionalAction(Settings.ACTION_DUR_XFAST,
                1,
                selectedCards -> selectedCards.forEach(card -> {
                    OmeletteCardmod.addToCard(card, magicNumber, upgraded);
                    card.superFlash();
                }),
                c -> c.type == CardType.ATTACK,
                EXTENDED_DESCRIPTION[0]));
    }
}
