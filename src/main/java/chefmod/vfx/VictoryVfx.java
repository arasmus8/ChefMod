package chefmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static chefmod.ChefMod.makeImagePath;

public class VictoryVfx extends AbstractGameEffect {
    private final ParticleEffect snowflakes;
    private float timer1 = 0f;

    public VictoryVfx() {
        renderBehind = true;
        snowflakes = new ParticleEffect();
        snowflakes.load(Gdx.files.internal(makeImagePath("victorySnowParticle.p")), Gdx.files.internal(makeImagePath("")));
        snowflakes.setPosition(0f, Settings.HEIGHT);
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        timer1 -= dt;
        if (timer1 < 0f) {
            snowflakes.start();
        }
        snowflakes.update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        snowflakes.draw(sb);
    }

    public void dispose() {
        snowflakes.dispose();
    }
}
