package animations;

import biuoop.DrawSurface;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-06 */
public interface Animation {
    /** draw one frame of the animation.
    * <p>
    * @param d - a draw surface to draw the frame on. */
    void doOneFrame(DrawSurface d);

    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    boolean shouldStop();
}
