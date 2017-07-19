package animations;

import biuoop.DrawSurface;
/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-16 */
public class PauseScreen implements Animation {
    private boolean stop;

    /** PauseScreen constructor. */
    public PauseScreen() {
        this.stop = false;
    }

    @Override
    /** draw one frame of the animation.
     * <p>
     * @param d - a draw surface to draw the frame on. */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText((d.getWidth() / 5) + 2, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
        return this.stop;
    }



}
