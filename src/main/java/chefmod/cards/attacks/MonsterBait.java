package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.vfx.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
        baseDamage = damage = 1; // 9;
        upgradeDamageBy = 3;
        baseMagicNumber = magicNumber = 5;
        damages = true;
        ImageMaster.loadRelicImg("Meat on the Bone", "meat.png");
        img = ImageMaster.getRelicImg("Meat on the Bone");
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int origBaseDamage = baseDamage;
        if (mo.hasPower(HungerPower.POWER_ID)) {
            baseDamage += magicNumber;
        }
        super.calculateCardDamage(mo);
        baseDamage = origBaseDamage;
        if (damage != baseDamage) {
            isDamageModified = true;
        }
    }

    private BiFunction<Float, Float, AbstractGameEffect> particleGenerator() {
        return (x, y) -> new VfxBuilder(ImageMaster.TINY_STAR, x, y, 0.45f)
                .setScale(0.6f)
                .setColor(Color.YELLOW)
                .gravity(20f)
                .fadeOut(0.1f)
                .build();
    }

    private AbstractGameEffect buildVfx(Hitbox hb) {
        float w = hb.width;
        float h = hb.height;
        return new VfxBuilder(img, 0.25f)
                .setX(hb.x - w * 0.25f)
                .setY(hb.y + h * 1.5f)
                .scale(0f, 1f)
                .andThen(0.5f)
                .arc(hb.x - w * 0.25f, hb.y + h * 1.5f, hb.cX, hb.y + h * 1.5f, hb.y + h)
                .emitEvery(particleGenerator(), 0.2f)
                .andThen(0.1f)
                .wobble(0f, 45f, 100f)
                .andThen(0.5f)
                .arc(hb.cX, hb.y + h * 1.5f, hb.x - w * 0.25f, hb.y + h * 1.25f, hb.y + h)
                .emitEvery(particleGenerator(), 0.2f)
                .andThen(1.5f)
                .velocity(135f, 180f)
                .gravity(50f)
                .build();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new VFXAction(buildVfx(m.hb)));
    }
}
