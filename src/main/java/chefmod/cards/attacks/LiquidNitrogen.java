package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.SupercooledPower;
import chefmod.util.TextureHelper;
import chefmod.vfx.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.function.BiFunction;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class LiquidNitrogen extends AbstractChefCard {
    public static String ID = makeID(LiquidNitrogen.class.getSimpleName());
    private final Texture img = TextureHelper.getTexture(makeImagePath("snowParticle.png"));

    public LiquidNitrogen() {
        super(ID,
                0,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 7;
        upgradeDamageBy = 2;
        baseMagicNumber = magicNumber = 1;
        damages = true;
    }

    private AbstractGameEffect buildVfx(float startX, Hitbox hb) {
        BiFunction<Float, Float, AbstractGameEffect> particle = (x, y) -> new VfxBuilder(img, x, y, 0.9f)
                .setColor(new Color(0xC7EDEBFF))
                .scale(0.8f, MathUtils.random(1.1f, 2f))
                .velocity(MathUtils.random(45f, 135f), 400f)
                .gravity(MathUtils.random(15f, 40f))
                .fadeOut(0.1f)
                .build();

        return new VfxBuilder(ImageMaster.FLAME_2, Settings.ACTION_DUR_MED)
                .setColor(new Color(0xC7EDEBFF))
                .setAngle(-90f)
                .setAlpha(0.8f)
                .scale(0.25f, 3f, VfxBuilder.Interpolations.LINEAR)
                .setY(hb.y + hb.height * 0.5f)
                .moveX(startX, hb.cX, VfxBuilder.Interpolations.LINEAR)
                .emitEvery(particle, 0.05f)
                .playSoundAt(0.2f, "ORB_FROST_EVOKE")
                .andThen(Settings.ACTION_DUR_FAST)
                .playSoundAt(0f, "MONSTER_SLIME_ATTACK")
                .scale(3f, 5f)
                .fadeOut(Settings.ACTION_DUR_FAST)
                .velocity(270f, 1250f)
                .emitEvery(particle, 0.07f)
                .build();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.NONE);
        applyToEnemy(m, new SupercooledPower(m, magicNumber));
        addToBot(new VFXAction(buildVfx(p.hb.cX, m.hb)));
    }
}
