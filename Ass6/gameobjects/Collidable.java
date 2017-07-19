package gameobjects;

import geometry.Point;
import geometry.Rectangle;

/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-04-02 */
public interface Collidable {
    /** get the collision Rectangle in order to calculate the collision place.
     * @return Rectangle - the "collision shape" of the object.*/
    Rectangle getCollisionRectangle();

    /** Notify the object that we collided with it at collisionPoint with
    * a given velocity.
    * <p>
    * @param ball - the ball that hit the Collidable.
    * @param collisionPoint - the next collision coordinate
    * @param currentVelocity - the velocity that needs to be changed
    * @return Velocity - the new velocity expected after the hit. */
    Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity);
}
