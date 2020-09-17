package chefmod.cards.skills;

import chefmod.actions.FunctionalAction;
import chefmod.actions.GridSelectAndPerformAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.stream.Collectors;

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
        addToBot(new FunctionalAction(firstUpdate -> {
            List<AbstractCard> attacks = AbstractDungeon.player.hand.getAttacks().group.stream()
                    .map(AbstractCard::makeSameInstanceOf)
                    .collect(Collectors.toList());

            CardGroup hand = AbstractDungeon.player.hand;

            if (hand.size() == 1) {
                AbstractCard card = hand.getTopCard();
                addToBot(new ExhaustSpecificCardAction(card, hand));
                shuffleIn(card.makeStatEquivalentCopy(), magicNumber);
            } else if (attacks.size() > 0) {
                CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                cg.group.addAll(hand.group.stream().map(AbstractCard::makeSameInstanceOf).collect(Collectors.toList()));
                FunctionalAction followupAction = new FunctionalAction(f -> {
                    GridSelectAndPerformAction.selectedCards.forEach(selected -> {
                        try {
                            AbstractCard c = findOriginal(selected);
                            addToBot(new ExhaustSpecificCardAction(c, hand));
                            shuffleIn(c.makeStatEquivalentCopy(), magicNumber);
                        } catch (Exception e) {
                            System.out.println("Couldn't find original card for some reason.");
                            e.printStackTrace();
                        }
                    });
                    return true;
                });
                addToBot(new GridSelectAndPerformAction(1, false, cg, null, followupAction));
            }
            return true;
        }));
    }
}
