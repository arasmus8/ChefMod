package chefmod.cards.attacks;

import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import chefmod.util.TextureHelper;
import chefmod.vfx.VfxBuilder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class FrozenTuna extends AbstractChefCard implements StartupCard {
    public static String ID = makeID(FrozenTuna.class.getSimpleName());
    private final Texture img = TextureHelper.getTexture(makeImagePath("vfx/FrozenTuna.png"));

    public FrozenTuna() {
        super(ID,
                3,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 18;
        baseMagicNumber = magicNumber = 0;
        upgradeMagicNumberBy = 6;
        damages = true;
    }

    private AbstractGameEffect particle(float x, float y) {
        Texture t = ImageMaster.FROST_ORB_MIDDLE;
        if (MathUtils.randomBoolean(0.2f)) {
            t = ImageMaster.FROST_ORB_LEFT;
        } else if (MathUtils.randomBoolean(0.2f)) {
            t = ImageMaster.FROST_ORB_RIGHT;
        }
        float pitch = 0.8F + MathUtils.random(-0.2f, 0.2f);
        return new VfxBuilder(t, 0.8f)
                .setX(x + MathUtils.random(-200f * Settings.scale, 200f * Settings.scale))
                .setY(y)
                .setScale(MathUtils.random(2f, 2.8f))
                .setAngle(MathUtils.random(0f, 360f))
                .velocity(MathUtils.random(45f, 135f), 800f)
                .gravity(20f)
                .fadeOut(0.6f)
                .playSoundAt(MathUtils.random(0f, 0.6f), pitch, "ORB_FROST_EVOKE")
                .build();
    }

    private AbstractGameEffect vfx() {
        VfxBuilder builder = new VfxBuilder(img, .8f)
                .playSoundAt(0f, "ORB_FROST_CHANNEL")
                .setScale(1.5f)
                .arc(0f, Settings.HEIGHT / 2f, Settings.WIDTH * 0.75f, AbstractDungeon.floorY, Settings.HEIGHT)
                .playSoundAt(0.5f, -0.5f, "ATTACK_IRON_2")
                .andThen(1f)
                .velocity(90f, 120f)
                .fadeOut(0.4f)
                .rotate(MathUtils.random(-600f, 600f));
        IntStream.rangeClosed(1, 12)
                .forEachOrdered(i -> builder.triggerVfxAt(this::particle, 0f));
        return builder.build();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(vfx()));
        addToBot(new WaitAction(1.5f));
        dealAoeDamage(AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void triggerWhenFrozen() {
        if (upgraded) {
            baseDamage += magicNumber;
        }
    }

    @Override
    public boolean atBattleStartPreDraw() {
        addToBot(new FreezeAction(this));
        return true;
    }
}
