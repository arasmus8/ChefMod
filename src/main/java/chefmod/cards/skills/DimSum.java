package chefmod.cards.skills;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerDamageUpPower;
import chefmod.powers.HungerPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class DimSum extends AbstractChefCard implements StartupCard {
    public static String ID = makeID(DimSum.class.getSimpleName());

    public DimSum() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        hasPreppedActions = !upgraded;
        baseMagicNumber = magicNumber = 5;
        isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new HungerPower(m));
        shuffleBackIntoDrawPile = !upgraded && isPrepped();
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (upgraded) {
            applyToSelf(new HungerDamageUpPower(AbstractDungeon.player, magicNumber));
            return true;
        }
        return false;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        hasPreppedActions = false;
    }
}
