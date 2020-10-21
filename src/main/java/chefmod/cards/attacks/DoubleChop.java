package chefmod.cards.attacks;

import chefmod.ChefMod;
import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class DoubleChop extends AbstractChefCard {
    public static String ID = makeID(DoubleChop.class.getSimpleName());

    public DoubleChop() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 2;
        upgradeDamageBy = 2;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber)
                .forEach(i -> dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (isEthereal) {
            addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        } else {
            ChefMod.cardsToFreeze.add(this);
            if (AbstractDungeon.player.hasRelic(RunicPyramid.ID) || AbstractDungeon.player.hasPower(EquilibriumPower.POWER_ID) || retain || selfRetain) {
                addToTop(new DiscardSpecificCardAction(this, AbstractDungeon.player.hand));
            }
        }
    }

    @Override
    public void onRetained() {
        addToBot(new FreezeAction(this));
    }
}
