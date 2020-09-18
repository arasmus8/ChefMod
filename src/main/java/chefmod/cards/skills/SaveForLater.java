package chefmod.cards.skills;

import chefmod.actions.HandSelectFunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

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

    private AbstractCard findOriginal(AbstractCard selected) throws Exception {
        return AbstractDungeon.player.hand.group.stream()
                .filter(c -> c.uuid.equals(selected.uuid))
                .findFirst()
                .orElseThrow(Exception::new);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectFunctionalAction(selectedCards -> {
            AbstractCard card = selectedCards.get(0);
            addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            shuffleIn(card.makeStatEquivalentCopy(), magicNumber);
        }, EXTENDED_DESCRIPTION[0]));
    }
}
