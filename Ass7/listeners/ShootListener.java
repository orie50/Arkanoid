package listeners;

import gameobjects.Ball;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17 */
public interface ShootListener {

    /**
     * Called when a shot is made.
     * <p>
     * @param shot - the ball that been shot. */
   void shootEvent(Ball shot);
}