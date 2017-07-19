package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-16 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /** PauseScreen constructor.
     * <p>
     * @param k - keyboard sensor. */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    @Override
    /** draw one frame of the animation.
    * <p>
    * @param d - a draw surface to draw the frame on. */
    public void doOneFrame(DrawSurface d) {
        d.drawText((d.getWidth() / 5) + 2, d.getHeight() / 2, "paused -- press space to continue", 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) { this.stop = true; }
    }
    @Override
    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
        return this.stop;
    }



}
