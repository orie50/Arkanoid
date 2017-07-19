package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-2 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /** constructor - give the block all his parameters.
     * <p>
     * @param sensor - the keyboard sensor.
     * @param key - the pressed key that continue the animation.
     * @param animation - the animation that run. */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed  = true;
    }

    @Override
    /**
     * Do one frame of the animation.
     * Check if the key is already pressed and stop if it's pressed.
     * <p>
     * @param d - the given surface.
     * @param dt - the speed per frame. */
    public void doOneFrame(DrawSurface d, double dt) {
        animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed  = false;
        }
    }

    @Override
    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
        return this.stop;
    }
}