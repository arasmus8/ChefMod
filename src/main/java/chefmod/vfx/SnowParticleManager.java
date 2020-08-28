package chefmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

import static chefmod.ChefMod.makeImagePath;

public class SnowParticleManager {
    private static ArrayList<ParticleEffect> particleEffects;
    private float nextParticleDelay;
    private float x;
    private float y;
    private static final int TARGET_COUNT = 30;

    public SnowParticleManager(float x, float y) {
        particleEffects = new ArrayList<>();
        nextParticleDelay = MathUtils.random(0.1f, 0.5f);
        this.x = x;
        this.y = y;
    }

    private ParticleEffect createParticle() {
        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal(makeImagePath("snowParticle.p")), Gdx.files.internal(makeImagePath("")));
        effect.setPosition(x, y);
        effect.start();
        effect.scaleEffect(Settings.scale);
        return effect;
    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;
        float delta = Gdx.graphics.getDeltaTime();
        if (particleEffects.size() < TARGET_COUNT) {
            nextParticleDelay -= delta;
            if (nextParticleDelay <= 0f) {
                particleEffects.add(createParticle());
                nextParticleDelay = MathUtils.random(0.2f, 0.9f);
            }
        }
        particleEffects.forEach(pe -> pe.update(delta));
    }

    public void render(SpriteBatch sb, float x, float y) {
        particleEffects.forEach(pe -> {
            pe.draw(sb, Gdx.graphics.getDeltaTime());
            if (pe.isComplete()) {
                pe.dispose();
            }
        });
        particleEffects.removeIf(ParticleEffect::isComplete);
    }
}
