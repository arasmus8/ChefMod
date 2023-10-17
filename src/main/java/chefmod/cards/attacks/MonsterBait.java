package chefmod.cards.attacks;

import basemod.helpers.VfxBuilder;
import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.function.BiFunction;

import static chefmod.ChefMod.makeID;

public class MonsterBait extends AbstractChefCard {
    public static String ID = makeID(MonsterBait.class.getSimpleName());
    private final Texture img;

    public MonsterBait() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 12;
        upgradeDamageBy = 6;
        damages = true;
        ImageMaster.loadRelicImg("Meat on the Bone", "meat.png");
        img = ImageMaster.getRelicImg("Meat on the Bone");
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean b = super.canUse(p, m);
        if (!b) {
            return false;
        }

        if (m != null && m.hasPower(HungerPower.POWER_ID)) {
            return true;
        }

        cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    private BiFunction<Float, Float, AbstractGameEffect> particleGenerator() {
        return (x, y) -> new VfxBuilder(ImageMaster.WATER_DROP_VFX[2], x, y, 0.45f)
                .setX(x + MathUtils.random(-6, 6))
                .setY(y + MathUtils.random(-5, 5))
                .setScale(0.6f)
                .setColor(Color.CHARTREUSE)
                .gravity(20f)
                .fadeOut(0.1f)
                .build();
    }

    private AbstractGameEffect buildVfx(Hitbox hb) {
        float w = hb.width;
        float h = hb.height;
        return new VfxBuilder(img, 0.3f)
                .setColor(Color.CHARTREUSE)
                //.setX(hb.x - w * 0.25f)
                //.setY(hb.y + h * 1.5f)
                .scale(0.4f, 2.5f)
                .rotate(70f)
                .arc(hb.x - w * 0.25f, hb.y + h * 1.5f, hb.x - w * 0.1f, hb.y + h * 0.1f, hb.y - h * 0.25f)
                .andThen(0.2f)
                .rotate(40f)
                .emitEvery(particleGenerator(), 0.2f)
                .andThen(0.15f)
                .rotate(-50f)
                .andThen(0.1f)
                .fadeOut(0.1f)
                .triggerVfxAt(
                        0f,
                        1,
                        (x, y) -> new VfxBuilder(ImageMaster.ATK_POISON, x, y, 0.3f)
                            .setScale(0.8f)
                            .fadeOut(0.3f)
                            .build()
                )
                .build();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FunctionalAction(firstUpdate -> {
            if (m == null || p != null && p.isDying || m.isDeadOrEscaped()) {
                return true;
            }
            m.damage(new DamageInfo(p, damage, damageTypeForTurn));
            if (m.lastDamageTaken > 0) {
                int poisonAmount = m.lastDamageTaken;
                addToTop(new ApplyPowerAction(m, p, new PoisonPower(m, p, poisonAmount), poisonAmount));
            }
            return true;
        }));
        addToBot(new VFXAction(buildVfx(m.hb)));
    }
}
