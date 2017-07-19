package gameobjects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Integer maxHits;
    private Color stroke;
    private List<HitListener> hitListeners;
    private Map<Integer, Color> fillColor;
    private Map<Integer, BufferedImage> fillImage;

    /** Block constructor.
     * <p>
     * @param shape - the collision rectangle.
     * @param hitPoints - hits available.
     * @param stroke - the block stroke.
     * @param fillColor - map of the fills from colors.
     * @param fillImage - map of the fills from images. */
    public Block(Rectangle shape, int hitPoints, Color stroke,
            Map<Integer, Color> fillColor , Map<Integer, BufferedImage> fillImage) {
        this.shape = shape;
        this.maxHits = hitPoints;
        this.stroke = stroke;
        this.fillColor = fillColor;
        this.fillImage = fillImage;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /** Block constructor.
     * <p>
     * @param upperLeft - coordinate
     * @param width - width of the rectangle
     * @param height - height of the rectangle
     * @param maxHits - hits available.
     * @param stroke - the block stroke*/
    public Block(Point upperLeft, double width, double height, int maxHits, Color stroke) {
        this.shape = new Rectangle(upperLeft, width, height);
        this.maxHits = maxHits;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /** Block constructor.
     * <p>
     * @param shape - the collision rectangle
     * @param maxHits - hits available.
     * @param stroke - the block stroke*/
    public Block(Rectangle shape, int maxHits, Color stroke) {
        this.shape = shape;
        this.maxHits = maxHits;
        this.stroke = stroke;
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
     * Draw the block frame and fills it.
     * <p>
     * @param surface - the surface to be drew on */
    public void drawOn(DrawSurface surface) {
        Color color = null;
        BufferedImage image = null;
        // If block have no hit points go to 1 (not to max hit).
        if (maxHits == -1) {
            color = this.fillColor.get(1);
            image = this.fillImage.get(1);
        } else {
            color = this.fillColor.get(maxHits);
            image = this.fillImage.get(maxHits);
        }
        if (color != null) {
            // If the fill is color.
            surface.setColor(color);
            this.shape.fillOn(surface);
        } else {
            // if the fill is image.
            Point p = this.shape.getUpperLeft();
            surface.drawImage((int) p.getX(), (int) p.getY(), image);
        }
        // Draw the stroke.
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            this.shape.drawOn(surface);
        }
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
     * notify the block that the main animation loop continued.
     * <p>
     * @param dt - the speed per frame. */
    public void timePassed(double dt) {
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