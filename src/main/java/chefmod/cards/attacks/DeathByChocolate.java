package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.powers.SatiatedPower;
import chefmod.util.TextureHelper;
import chefmod.vfx.VfxBuilder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class DeathByChocolate extends AbstractChefCard {
    public static String ID = makeID(DeathByChocolate.class.getSimpleName());
    private static Texture t = TextureHelper.getTexture(makeImagePath("vfx/chocolate.png"));

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
            VfxBuilder vfxBuilder = new VfxBuilder(t, m.hb.cX, Settings.HEIGHT + 256f, 1.5f)
                    .moveY(Settings.HEIGHT, m.hb.y + m.hb.height / 6, VfxBuilder.Interpolations.BOUNCE)
                    .fadeIn(0.25f)
                    .setScale(1.25f * m.hb.width / t.getWidth())
                    .fadeOut(0.25f)
                    .playSoundAt("BLUNT_HEAVY", 0.35f);
            IntStream.rangeClosed(1, 20)
                    .forEachOrdered(i -> vfxBuilder.triggerVfxAt((x, y) -> new StarBounceEffect(
                                    x + MathUtils.random(-m.hb.width / 2, m.hb.width / 2),
                                    y + MathUtils.random(-1 * t.getHeight() / 2f, t.getHeight() / 2f)),
                            0.4f));
            AbstractGameEffect vfx = vfxBuilder.build();
            addToBot(new VFXAction(vfx));
            //addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            makeInHand(this);
        } else {
            dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }
}
