package gameobjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import animations.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-04-02 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private int maxHits;
    private Color color;
    private List<HitListener> hitListeners;

    /** Block constructor.
     * <p>
     * @param upperLeft - coordinate
     * @param width - width of the rectangle
     * @param height - height of the rectangle
     * @param maxHits - hits available.
     * @param color - the block color*/
    public Block(Point upperLeft, double width, double height, int maxHits, Color color) {
        this.shape = new Rectangle(upperLeft, width, height);
        this.maxHits = maxHits;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /** Block constructor.
     * <p>
     * @param shape - the collision rectangle
     * @param maxHits - hits available.
     * @param color - the block color*/
    public Block(Rectangle shape, int maxHits, Color color) {
        this.shape = shape;
        this.maxHits = maxHits;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /** returns the remaining hits available.
     * <p>
     * @return maxHits*/
    public int getMaxHits() {
        return maxHits;
    }

    /** returns the collision rectangle.
     * <p>
     * @return collision rectangle*/
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }
    /**
     * notify the block that it been hit
     * return appropriate velocity to the ball.
     * <p>
     * @param hitter - the ball that hit the block.
     * @param collisionPoint - the next collision coordinate
     * @param currentVelocity - the velocity that needs to be changed
     * @return Velocity - new appropriate velocity*/
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //enum like variables
        final int up = 0;
        final int right = 1;
        final int down = 2;
        final int left = 3;
        //velocity properties
        double  dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Velocity newVelocity = null;
        //returns the place of the collision point inside the block
        int hitPlace = this.shape.placeInsideMe(collisionPoint);
        //update the hit counter
        if (this.maxHits > 0) {
            this.maxHits--;
        }
        //setting new velocity according to the hit place
        switch(hitPlace) {
        case up:
            newVelocity = new Velocity(dx, -Math.abs(dy));
            break;
        case down:
            newVelocity = new Velocity(dx, Math.abs(dy));
            break;
        case right:
            newVelocity = new Velocity(Math.abs(dx), dy);
            break;
        case left:
            newVelocity = new Velocity(-Math.abs(dx), dy);
            break;
        default:
            System.out.println("Error: no velocity");
        }
        // Notify on the hit.
        this.notifyHit(hitter);
        return newVelocity;
    }

    /** Notify all the listeners that about the event.
     * <p>
     * @param hitter - the ball that hit the block. */
    private void notifyHit(Ball hitter) {
        // Copy the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /** Block drawing method.
     * <p>
     * drawing to black with a black frame,
     * filled with his set color.
     * <p>
     * @param surface - the surface to be drew on*/
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        this.shape.drawOn(surface);
    }

    /** add the Block to the game Database.
     * <p>
     * @param game - the game to be add to*/
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /** remove the Block from the game Database.
     * <p>
     * @param game - the game to be remove*/
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);

    }

    /**
     * notify the block that the main animation loop continued.*/
    public void timePassed() {
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}