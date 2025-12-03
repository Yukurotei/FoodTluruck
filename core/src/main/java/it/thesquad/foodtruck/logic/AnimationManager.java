package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class AnimationManager {

    public enum Easing {
        LINEAR,
        EASE_IN_QUAD,
        EASE_OUT_QUAD,
        EASE_IN_OUT_QUAD,
        EASE_IN_CUBIC,
        EASE_OUT_CUBIC,
        EASE_IN_OUT_CUBIC,
        EASE_IN_SINE,
        EASE_OUT_SINE,
        EASE_IN_OUT_SINE,
        EASE_IN_EXPO,
        EASE_OUT_EXPO,
        EASE_IN_OUT_EXPO
    }

    private final Array<Animation> animations = new Array<>();
    private final Pool<Animation> animationPool = new Pool<Animation>() {
        @Override
        protected Animation newObject() {
            return new Animation();
        }
    };

    public void update(float delta) {
        for (int i = animations.size - 1; i >= 0; i--) {
            Animation anim = animations.get(i);
            if (anim.isFinished()) {
                animations.removeIndex(i);
                animationPool.free(anim);
            } else {
                anim.update(delta);
            }
        }
    }

    public void animateMove(AnimatedSprite target, float toX, float toY, float duration, Easing easing) {
        Animation anim = animationPool.obtain();
        anim.initMove(target, toX, toY, duration, easing);
        animations.add(anim);
    }

    public void animateFade(AnimatedSprite target, float toOpacity, float duration, Easing easing) {
        Animation anim = animationPool.obtain();
        anim.initFade(target, toOpacity, duration, easing);
        animations.add(anim);
    }

    public void animateScale(AnimatedSprite target, float toScaleX, float toScaleY, float duration, Easing easing) {
        Animation anim = animationPool.obtain();
        anim.initScale(target, toScaleX, toScaleY, duration, easing);
        animations.add(anim);
    }

    public void animateRotation(AnimatedSprite target, float toRotation, float duration, Easing easing) {
        Animation anim = animationPool.obtain();
        anim.initRotation(target, toRotation, duration, easing);
        animations.add(anim);
    }

    private static class Animation implements Pool.Poolable {
        private AnimatedSprite target;
        private Easing easing;
        private float duration;
        private float time;

        // Position
        private float startX, startY;
        private float toX, toY;

        // Opacity
        private float startOpacity, toOpacity;

        // Scale
        private float startScaleX, startScaleY;
        private float toScaleX, toScaleY;

        // Rotation
        private float startRotation, toRotation;


        public void initMove(AnimatedSprite target, float toX, float toY, float duration, Easing easing) {
            this.target = target;
            this.duration = duration;
            this.easing = easing;
            this.time = 0;

            this.startX = target.getX();
            this.startY = target.getY();
            this.toX = toX;
            this.toY = toY;

            this.toOpacity = Float.NaN;
            this.toScaleX = Float.NaN;
            this.toScaleY = Float.NaN;
            this.toRotation = Float.NaN;
        }

        public void initFade(AnimatedSprite target, float toOpacity, float duration, Easing easing) {
            this.target = target;
            this.duration = duration;
            this.easing = easing;
            this.time = 0;

            this.startOpacity = target.getAlpha();
            this.toOpacity = toOpacity;

            this.toX = Float.NaN;
            this.toY = Float.NaN;
            this.toScaleX = Float.NaN;
            this.toScaleY = Float.NaN;
            this.toRotation = Float.NaN;
        }

        public void initScale(AnimatedSprite target, float toScaleX, float toScaleY, float duration, Easing easing) {
            this.target = target;
            this.duration = duration;
            this.easing = easing;
            this.time = 0;

            this.startScaleX = target.getScaleX();
            this.startScaleY = target.getScaleY();
            this.toScaleX = toScaleX;
            this.toScaleY = toScaleY;

            this.toX = Float.NaN;
            this.toY = Float.NaN;
            this.toOpacity = Float.NaN;
            this.toRotation = Float.NaN;
        }

        public void initRotation(AnimatedSprite target, float toRotation, float duration, Easing easing) {
            this.target = target;
            this.duration = duration;
            this.easing = easing;
            this.time = 0;

            this.startRotation = target.getRotation();
            this.toRotation = toRotation;

            this.toX = Float.NaN;
            this.toY = Float.NaN;
            this.toScaleX = Float.NaN;
            this.toScaleY = Float.NaN;
            this.toOpacity = Float.NaN;
        }

        public void update(float delta) {
            if (isFinished()) return;

            time += delta;
            float progress = Math.min(1f, time / duration);
            float easedProgress = applyEasing(progress);

            if (!Float.isNaN(toX)) {
                target.setX(startX + (toX - startX) * easedProgress);
            }
            if (!Float.isNaN(toY)) {
                target.setY(startY + (toY - startY) * easedProgress);
            }
            if (!Float.isNaN(toOpacity)) {
                target.setAlpha(startOpacity + (toOpacity - startOpacity) * easedProgress);
            }
            if (!Float.isNaN(toScaleX)) {
                target.setScaleX(startScaleX + (toScaleX - startScaleX) * easedProgress);
            }
            if (!Float.isNaN(toScaleY)) {
                target.setScaleY(startScaleY + (toScaleY - startScaleY) * easedProgress);
            }
            if (!Float.isNaN(toRotation)) {
                target.setRotation(startRotation + (toRotation - startRotation) * easedProgress);
            }

            if (progress >= 1f) {
                time = duration; // Ensure time is exactly duration when finished
            }
        }

        public boolean isFinished() {
            return time >= duration;
        }

        private float applyEasing(float t) {
            switch (easing) {
                case EASE_IN_QUAD:
                    return t * t;
                case EASE_OUT_QUAD:
                    return t * (2 - t);
                case EASE_IN_OUT_QUAD:
                    return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
                case EASE_IN_CUBIC:
                    return t * t * t;
                case EASE_OUT_CUBIC:
                    return 1 - (float) Math.pow(1 - t, 3);
                case EASE_IN_OUT_CUBIC:
                    return t < 0.5f ? 4 * t * t * t : 1 - (float) Math.pow(-2 * t + 2, 3) / 2;
                case EASE_IN_SINE:
                    return 1 - (float) Math.cos((t * Math.PI) / 2);
                case EASE_OUT_SINE:
                    return (float) Math.sin((t * Math.PI) / 2);
                case EASE_IN_OUT_SINE:
                    return -((float) Math.cos(Math.PI * t) - 1) / 2;
                case EASE_IN_EXPO:
                    return t == 0 ? 0 : (float) Math.pow(2, 10 * t - 10);
                case EASE_OUT_EXPO:
                    return t == 1 ? 1 : 1 - (float) Math.pow(2, -10 * t);
                case EASE_IN_OUT_EXPO:
                    if (t == 0) return 0;
                    if (t == 1) return 1;
                    if (t < 0.5f) return (float) Math.pow(2, 20 * t - 10) / 2;
                    return (2 - (float) Math.pow(2, -20 * t + 10)) / 2;
                case LINEAR:
                default:
                    return t;
            }
        }

        public AnimatedSprite getAnimatedSprite() {
            return target;
        }

        @Override
        public void reset() {
            target = null;
            easing = Easing.LINEAR;
            duration = 0;
            time = 0;

            startX = startY = toX = toY = Float.NaN;
            startOpacity = toOpacity = Float.NaN;
            startScaleX = startScaleY = toScaleX = toScaleY = Float.NaN;
            startRotation = toRotation = Float.NaN;
        }
    }
}
