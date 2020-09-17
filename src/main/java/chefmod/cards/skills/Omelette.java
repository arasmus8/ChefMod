package chefmod.cards.skills;

import chefmod.actions.FunctionalAction;
import chefmod.actions.GridSelectAndPerformAction;
import chefmod.cardmods.OmeletteCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.stream.Collectors;

import static chefmod.ChefMod.makeID;

public class Omelette extends AbstractChefCard {
    public static String ID = makeID(Omelette.class.getSimpleName());

    public Omelette() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 5;
        exhaust = true;
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

            if (attacks.size() == 1) {
                try {
                    AbstractCard card = findOriginal(attacks.get(0));
                    OmeletteCardmod.addToCard(card, magicNumber, upgraded);
                    card.superFlash();
                } catch (Exception e) {
                    System.out.println("Couldn't find original card for some reason.");
                    e.printStackTrace();
                }
            } else if (attacks.size() > 0) {
                CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                cg.group.addAll(attacks);
                FunctionalAction followupAction = new FunctionalAction(f -> {
                    GridSelectAndPerformAction.selectedCards.forEach(selected -> {
                        try {
                            AbstractCard c = findOriginal(selected);
                            OmeletteCardmod.addToCard(c, magicNumber, upgraded);
                            c.superFlash();
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
