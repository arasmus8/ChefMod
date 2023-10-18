package chefmod.cards.attacks;

import basemod.helpers.VfxBuilder;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.powers.SatiatedPower;
import chefmod.util.VfxMaster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class DeathByChocolate extends AbstractChefCard {
    public static String ID = makeID(DeathByChocolate.class.getSimpleName());

    public DeathByChocolate() {
        super(ID,
                0,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ENEMY
        );
        baseDamage = damage = 6;
        upgradeDamageBy = 3;
        magicNumber = baseMagicNumber = 50;
        damages = true;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = baseDamage;
        baseDamage = baseMagicNumber;
        super.calculateCardDamage(mo);
        magicNumber = damage;
        isMagicNumberModified = magicNumber != baseMagicNumber;
        baseDamage = realBaseDamage;
        super.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = baseMagicNumber;
        isMagicNumberModified = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(SatiatedPower.POWER_ID) && m.hasPower(HungerPower.POWER_ID)) {
            AbstractGameEffect vfx = new VfxBuilder(VfxMaster.CHOCOLATE, m.hb.cX, Settings.HEIGHT + 256f, 1.5f)
                    .moveY(Settings.HEIGHT, m.hb.y + m.hb.height / 6, VfxBuilder.Interpolations.BOUNCEOUT)
                    .fadeIn(0.25f)
                    .setScale(1.25f * m.hb.width / VfxMaster.CHOCOLATE.packedWidth)
                    .fadeOut(0.25f)
                    .playSoundAt(0.35f, "BLUNT_HEAVY")
                    .triggerVfxAt(0.4f, 20, (x, y) -> new StarBounceEffect(
                            x + MathUtils.random(-m.hb.width / 2, m.hb.width / 2),
                            y + MathUtils.random(-1 * VfxMaster.CHOCOLATE.packedHeight / 2f, VfxMaster.CHOCOLATE.packedHeight / 2f)))
                    .build();
            addToBot(new VFXAction(vfx));
            addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        } else {
            dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }
}
