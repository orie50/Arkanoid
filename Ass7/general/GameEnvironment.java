package general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import biuoop.DrawSurface;
import gameobjects.Collidable;
import geometry.Line;
import geometry.Point;
import geometry.PointByDistanceComparator;
import geometry.Rectangle;

/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-04-02 */
public class GameEnvironment {
    private List<Collidable> collidables;
    private Point lowerFrameEdge;
    private Point upperFrameEdge;
    private DrawSurface surface;
    /**
     * GameEnvironment constructor.
     * <p>
     * the drawsurface is null by defualt
     * <p>
     * @param lowerFrameEdge - the game frame lower boundary coordinate
     * @param upperFrameEdge - the game frame upper boundary coordinate*/
    public GameEnvironment(Point lowerFrameEdge, Point upperFrameEdge) {
        this.collidables = new ArrayList<Collidable>();
        this.lowerFrameEdge = lowerFrameEdge;
        this.upperFrameEdge = upperFrameEdge;
        this.setSurface(null);
    }
    /**
     * returns the Lower Frame Edge.
     * <p>
     * @return lowerFrameEdge - the game frame lower boundary coordinate*/
    public Point getLowerFrameEdge() {
        return lowerFrameEdge;
    }
    /**
     * returns the upper Frame Edge.
     * <p>
     * @return upperFrameEdge - the game frame upper boundary coordinate*/
    public Point getUpperFrameEdge() {
        return upperFrameEdge;
    }
    /**
     * add the given collidable to the environment.
     * <p>
     * @param c - a collidable object*/
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    /**
     * get the collidable with the given index
     * from the environment.
     * <p>
     * @param index - the colldable index
     * @return Collidable - the collidable with the given index
     * and null if it doesn't exists*/
    public Collidable getCollidable(int index) {
        if (this.collidables.size() > index) {
            return (Collidable) this.collidables.get(index);
        }
        return null;
    }

    /**
     * remove the collidable with the given index
     * from the environment.
     * <p>
     * @param c - the colldable index*/
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * get the next collision information, using the trajectory.
     * <p>
     * return null if there is no collision
     * <p>
     * @param trajectory - the route in which the ball will move assuming he will not collide anything
     * @return the next collision information*/
    public CollisionInfo getClosestCollision(Line trajectory) {
        //counter
        int i = 0;
        List<Point> collisionsPoints = new ArrayList<Point>();
        List<Point> sortedCollisionsPoints = new ArrayList<Point>();
        List<Collidable> blocks = new ArrayList<Collidable>();
        //add only the collidables and collision points in the trajectory route to List
        while (i < this.collidables.size()) {
            Collidable c = this.collidables.get(i);
            Rectangle rect = c.getCollisionRectangle();
            Point temp = trajectory.closestIntersectionToStartOfLine(rect);
            if (temp != null) {
                blocks.add(c);
                collisionsPoints.add(temp);
            }
            i++;
        }
        if (collisionsPoints.isEmpty() || blocks.isEmpty()) {
            System.out.println("Error: no CollisionInfo");
            return getClosestCollision(new Line(trajectory.start(),
                    new Point(trajectory.end().getX() + 1, trajectory.end().getY())));
        }
        //copy the collision points to another array and sort them
        sortedCollisionsPoints.addAll(collisionsPoints);
        Collections.sort(sortedCollisionsPoints, new PointByDistanceComparator(trajectory.start()));
        //get the closest point (the minimal distantce point) index in the original list
        i = collisionsPoints.indexOf(sortedCollisionsPoints.get(0));
        return new CollisionInfo((Point) collisionsPoints.get(i), (Collidable) blocks.get(i));
    }
    /**
     * get the environment Draw Surface.
     * <p>
     * @return DrawSurface*/
    public DrawSurface getSurface() {
        return surface;
    }
    /**
     * set the environment DrawSurface to the give surface.
     * <p>
     * @param ds - DrawSurface*/
    public void setSurface(DrawSurface ds) {
        this.surface = ds;
    }
}