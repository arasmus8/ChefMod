package chefmod.vfx;

import chefmod.util.TextureHelper;
import chefmod.util.VfxMaster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FrozenCardVfx extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.0F;
    private static final float ANIM_DUR = 0.25F;
    private static final float ANIM_START_AT = 0.75F;
    private static final float SOUND_AT = 0.25F;
    private final AbstractCard c;
    private static final float PADDING = 50.0f * Settings.scale;
    private boolean soundPlayed;
    private float alpha;

    private static int count = 0;

    public FrozenCardVfx(AbstractCard card) {
        c = card.makeStatEquivalentCopy();
        startingDuration = duration = EFFECT_DUR;
        c.drawScale = 0.01f;
        c.targetDrawScale = 1.0f;
        c.current_x = Settings.WIDTH / 4.0f - (PADDING * count);
        c.current_y = Settings.HEIGHT / 1.8f;
        c.target_x = c.current_x;
        c.target_y = c.current_y;
        c.targetAngle = MathUtils.random(-6f, 6f);
        soundPlayed = false;
        alpha = 0;
        if (count > 5) {
            isDone = true;
        } else {
            ++count;
        }
    }

    @Override
    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.6f) {
            c.fadingOut = true;
        }

        if (duration < SOUND_AT && !soundPlayed) {
            soundPlayed = true;
            CardCrawlGame.sound.play("ORB_FROST_CHANNEL");
        }

        if (duration < ANIM_START_AT && duration > ANIM_START_AT - ANIM_DUR) {
            alpha = Interpolation.fade.apply((ANIM_START_AT - duration) / ANIM_DUR);
        }

        c.update();

        if (duration < 0.0f) {
            isDone = true;
            --count;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!isDone) {
            c.render(sb);

            Color drawColor = new Color(1, 1, 1, alpha * c.transparency);
            sb.setColor(drawColor);

            TextureHelper.drawScaledAndRotated(
                    sb,
                    VfxMaster.FROZEN_CARD_VFX,
                    c.current_x,
                    c.current_y,
                    1f,
                    0f
            );
        }
    }

    @Override
    public void dispose() {
    }
}
