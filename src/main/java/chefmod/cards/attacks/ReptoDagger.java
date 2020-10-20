package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.vfx.VfxBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class ReptoDagger extends AbstractChefCard {
    public static String ID = makeID(ReptoDagger.class.getSimpleName());
    private final TextureAtlas daggerAtlas = new TextureAtlas(Gdx.files.internal("images/monsters/theForest/mage_dagger/skeleton.atlas"));
    private final TextureAtlas.AtlasRegion dagger = daggerAtlas.findRegion("dagger copy");

    public ReptoDagger() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.SPECIAL,
                CardTarget.ENEMY,
                CardColor.COLORLESS,
                null
        );
        baseDamage = damage = 4;
        upgradeDamageBy = 2;
        damages = true;
        baseMagicNumber = magicNumber = 2;
        shuffleBackIntoDrawPile = true;
    }

    private void daggerVfx(AbstractPlayer p, AbstractMonster m) {
        float x = p.drawX;
        float y = MathUtils.random(p.hb.y, p.hb.y + p.hb.height);
        addToBot(new VFXAction(
                new VfxBuilder(dagger, x, y, Settings.ACTION_DUR_FAST)
                        .velocity(MathUtils.radiansToDegrees * MathUtils.atan2(m.hb.cY - y, m.hb.cX - p.hb.cX), 5500f * Settings.scale)
                        .setAngle(60f)
                        .setScale(0.6f)
                        .rotate(-1500f)
                        .build()
        ));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber).forEachOrdered(i -> {
            daggerVfx(p, m);
            dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        });
        baseMagicNumber = magicNumber = magicNumber + 1;
    }
}
