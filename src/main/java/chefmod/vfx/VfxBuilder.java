package chefmod.vfx;

import chefmod.util.TextureHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class VfxBuilder {
    public float duration;
    public float scale = 1f;
    public float angle = 0f;
    public float alpha = 1f;
    public float x;
    public float y;
    public Texture texture;
    public ArrayList<Predicate<Float>> updaters;

    public VfxBuilder(Texture img) {
        texture = img;
        updaters = new ArrayList<>();
    }

    private Function<Float, Float> interpolator(float from, float to, Interpolations interpolation) {
        switch (interpolation) {
            case BOUNCE:
                return t -> Interpolation.bounceOut.apply(from, to, t);
            case BOUNCEIN:
                return t -> Interpolation.bounceIn.apply(from, to, t);
            case CIRCLE:
                return t -> Interpolation.circleOut.apply(from, to, t);
            case CIRCLEIN:
                return t -> Interpolation.circleIn.apply(from, to, t);
            case ELASTIC:
                return t -> Interpolation.elasticOut.apply(from, to, t);
            case ELASTICIN:
                return t -> Interpolation.elasticIn.apply(from, to, t);
            case EXP5:
                return t -> Interpolation.exp5Out.apply(from, to, t);
            case EXP5IN:
                return t -> Interpolation.exp5In.apply(from, to, t);
            case EXP10:
                return t -> Interpolation.exp10Out.apply(from, to, t);
            case EXP10IN:
                return t -> Interpolation.exp10In.apply(from, to, t);
            case SMOOTH:
                return t -> Interpolation.smooth.apply(from, to, t);
            case SWING:
                return t -> Interpolation.swingOut.apply(from, to, t);
            case SWINGIN:
                return t -> Interpolation.swingIn.apply(from, to, t);
            case LINEAR: // fallthrough
            default:
                return t -> Interpolation.linear.apply(from, to, t);
        }
    }

    public VfxBuilder xRange(float from, float to, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(from, to, interpolation);
        updaters.add(t -> {
            x = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    public VfxBuilder xRange(float from, float to) {
        return xRange(from, to, Interpolations.EXP5);
    }

    public VfxBuilder yRange(float from, float to, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(from, to, interpolation);
        updaters.add(t -> {
            y = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    public VfxBuilder yRange(float from, float to) {
        return yRange(from, to, Interpolations.EXP5);
    }

    public VfxBuilder gravity(float strength) {
        updaters.add(t -> {
            y -= strength * t / duration;
            return false;
        });
        return this;
    }

    public VfxBuilder arc(float fromX, float fromY, float toX, float toY, float maxHeight) {
        Function<Float, Float> upFn = interpolator(fromY, maxHeight, Interpolations.CIRCLE);
        Function<Float, Float> downFn = interpolator(maxHeight, toY, Interpolations.CIRCLEIN);
        Function<Float, Float> xFn = interpolator(fromX, toX, Interpolations.LINEAR);
        updaters.add(t -> {
            x = xFn.apply(t / duration);
            return false;
        });
        updaters.add(t -> {
            float halfDuration = duration / 2f;
            if (t > halfDuration) {
                return true;
            }
            y = upFn.apply(t / halfDuration);
            return false;
        });
        updaters.add(t -> {
            float halfDuration = duration / 2f;
            if (t > halfDuration) {
                y = downFn.apply(t - halfDuration / duration);
            }
            return false;
        });
        return this;
    }

    public VfxBuilder velocity(float angle, float speed) {
        updaters.add(t -> {
            x += MathUtils.cos(angle) * speed;
            y += MathUtils.sin(angle) * speed;
            return false;
        });
        return this;
    }

    public VfxBuilder setAlpha(float value) {
        alpha = value;
        return this;
    }

    public VfxBuilder fadeIn(float fadeTime) {
        updaters.add(t -> {
            alpha = Interpolation.fade.apply(t / fadeTime);
            return t > fadeTime;
        });
        return this;
    }

    public VfxBuilder fadeOut(float fadeTime) {
        updaters.add(t -> {
            alpha = (duration - t) > fadeTime ? Interpolation.fade.apply((duration - t) / fadeTime) : 1f;
            return false;
        });
        return this;
    }

    public VfxBuilder setScale(float value) {
        scale = value;
        return this;
    }

    public VfxBuilder scale(float from, float to, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(from, to, interpolation);
        updaters.add(t -> {
            scale = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    public VfxBuilder scale(float from, float to) {
        return scale(from, to, Interpolations.SWING);
    }

    public VfxBuilder rotate(float speed) {
        updaters.add(t -> {
            angle += speed;
            return false;
        });
        return this;
    }

    public VfxBuilder playSoundAt(String key, float timeOffset) {
        updaters.add(t -> {
            if (t >= timeOffset) {
                CardCrawlGame.sound.play(key);
                return true;
            }
            return false;
        });
        return this;
    }

    public VfxBuilder triggerVfxAt(BiFunction<Float, Float, AbstractGameEffect> effectFn, float timeOffset) {
        updaters.add(t -> {
            if (t >= timeOffset) {
                AbstractDungeon.effectsQueue.add(effectFn.apply(x, y));
                return true;
            }
            return false;
        });
        return this;
    }

    public AbstractGameEffect build(float x, float y, float duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        return new BuiltEffect(this);
    }

    private static class BuiltEffect extends AbstractGameEffect {
        private final VfxBuilder builder;
        private float t;

        public BuiltEffect(VfxBuilder builder) {
            this.builder = builder;
            t = 0;
            startingDuration = duration = builder.duration;
        }

        @Override
        public void update() {
            t += Gdx.graphics.getDeltaTime();
            if (t >= duration) {
                isDone = true;
            }
            builder.updaters.removeIf(fn -> fn.test(t));
        }

        @Override
        public void render(SpriteBatch sb) {
            Color color = new Color(1f, 1f, 1f, builder.alpha);
            sb.setColor(color);
            TextureHelper.drawScaledAndRotated(
                    sb,
                    builder.texture,
                    builder.x,
                    builder.y,
                    builder.scale,
                    builder.angle
            );
        }

        @Override
        public void dispose() {
        }
    }

    public enum Interpolations {
        BOUNCE,
        CIRCLE,
        ELASTIC,
        EXP5,
        EXP10,
        LINEAR,
        SMOOTH,
        SWING,
        BOUNCEIN,
        CIRCLEIN,
        ELASTICIN,
        EXP5IN,
        EXP10IN,
        SWINGIN
    }
}
