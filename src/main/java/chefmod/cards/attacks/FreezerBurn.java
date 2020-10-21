package chefmod.cards.attacks;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static chefmod.ChefMod.makeID;

public class FreezerBurn extends AbstractChefCard {
    public static String ID = makeID(FreezerBurn.class.getSimpleName());

    public FreezerBurn() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseMagicNumber = magicNumber = 3;
        baseDamage = damage = 3;
        damages = true;
    }

    private void setDescription(boolean toLibraryDesc) {
        if (upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
        } else {
            rawDescription = DESCRIPTION;
        }
        if (!toLibraryDesc) {
            rawDescription = rawDescription + EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    private int calculatedDamage() {
        return magicNumber * (upgraded ? ChefMod.frozenThisCombat.size() : ChefMod.frozenPile.size());
    }

    @Override
    public void applyPowers() {
        baseDamage = calculatedDamage();
        super.applyPowers();
        setDescription(false);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        setDescription(false);
    }

    @Override
    public void onMoveToDiscard() {
        setDescription(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        baseDamage = calculatedDamage();
        calculateCardDamage(m);
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new BlizzardEffect(baseDamage, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            this.addToBot(new VFXAction(new BlizzardEffect(baseDamage, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }
        dealDamage(m, AbstractGameAction.AttackEffect.NONE);
    }
}
