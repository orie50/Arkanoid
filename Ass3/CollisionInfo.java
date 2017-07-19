/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-04-02 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;
    /**
     * CollisionInfo constructor.
     * <p>
     * @param collisionPoint - the next collision coordinate
     * @param collisionObject - the object that we collided with*/
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionObject = collisionObject;
        this.collisionPoint = collisionPoint;
    }
    /**
     * Collision point getter.
     * <p>
     * @return collisionPoint - the point at which the collision occurs.*/
    public Point collisionPoint() {
        return this.collisionPoint;
    }
    /**
     * Collision object getter.
     * <p>
     * @return collisionObject - the collidable object involved in the collision*/
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
