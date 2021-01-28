package chefmod.cards.attacks;

import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class Mince extends AbstractChefCard {
    public static String ID = makeID(Mince.class.getSimpleName());

    public Mince() {
        super(ID,
                3,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 7;
        magicNumber = baseMagicNumber = 4;
        upgradeMagicNumberBy = 1;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber).forEachOrdered(i ->
                addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_VERTICAL))
        );
    }

    @Override
    public void triggerWhenDrawn() {
        //addToBot(new DrawPileToHandAction(1, CardType.SKILL));
        addToBot(new FunctionalAction(firstUpdate -> {
            AbstractPlayer p = AbstractDungeon.player;
            Predicate<AbstractCard> isSkill = c -> c.type == CardType.SKILL;
            Optional<AbstractCard> skillInDraw = p.drawPile.group.stream()
                    .filter(isSkill)
                    .findFirst();
            skillInDraw.ifPresent(c -> {
                p.drawPile.removeCard(c);
                p.drawPile.addToTop(c);
                addToBot(new DrawCardAction(p, 1));
            });
            return true;
        }));
    }
}
