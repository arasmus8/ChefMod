package chefmod.cards.skills;

import chefmod.actions.HandSelectFunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class SaveForLater extends AbstractChefCard {
    public static String ID = makeID(SaveForLater.class.getSimpleName());

    public SaveForLater() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectFunctionalAction(selectedCards ->
                selectedCards.forEach(card -> {
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                    shuffleIn(card.makeStatEquivalentCopy(), magicNumber);
                }), EXTENDED_DESCRIPTION[0]));
    }
}
