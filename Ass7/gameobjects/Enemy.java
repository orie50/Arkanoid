package gameobjects;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
 * @since 2016-06-17 */
public class Enemy implements Collidable, Sprite, HitNotifier {

    private Rectangle shape;
    private BufferedImage image1;
    private BufferedImage image2;
    private long imageTiming;
    private int imageNum;
    private Velocity velocity;
    private List<HitListener> hitListeners;
    private int formationNum;

    /**
     * Instantiates a new enemy.
     *
     * @param shape - the shape
     * @param image1 - first image to draw
     * @param image2  -  second image to draw
     * @param velocity - the enemy velocity velocity
     * @param formationNum - the place in the formation row*/
    public Enemy(Rectangle shape, BufferedImage image1, BufferedImage image2, Velocity velocity, int formationNum) {
        this.shape = shape;
        this.image1 = image1;
        this.image2 = image2;
        this.imageNum = 1;
        this.velocity = velocity;
        this.hitListeners = new ArrayList<HitListener>();
        this.formationNum = formationNum;
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    @Override
    public void drawOn(DrawSurface d) {
        long currentTime = System.currentTimeMillis();
        // chekink which image was presented and which one should be presented now
        switch (imageNum) {
        case 1:
            d.drawImage((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(), this.image1);
            if ((currentTime - this.imageTiming) / 1000 >= 0.09) {
                this.imageTiming = currentTime;
                this.imageNum = 2;
            }
            break;
        case 2:
            d.drawImage((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(), this.image2);
            if ((currentTime - this.imageTiming) / 1000 >= 0.9) {
                this.imageTiming = currentTime;
                this.imageNum = 1;
            }
            break;
        default:
            System.out.println("something wrong happend in drawOn of Enemy");
            break;
        }
    }

    /**
     * Notify the sprite that the time passed.
     * <p>
     * change the shape to a new location acording to the velocity
     * @param dt - the speed per frame. */
    @Override
    public void timePassed(double dt) {
        this.shape = new Rectangle(this.velocity.applyToPoint(this.shape.getUpperLeft(), dt),
                this.shape.getWidth(), this.shape.getHeight());
    }

    @Override
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
    }

    @Override
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(ball);
        return null;
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

    /**
     * Shoot.
     *
     * @return the ball
     */
    public Ball shoot() {
        Ball shot = new Ball((int) (this.shape.getUpperLeft().getX() + this.shape.getWidth() / 2),
                (int) (this.shape.getUpperLeft().getY() + this.shape.getHeight() + 1), 5, Color.RED);
        shot.setVelocity(Velocity.fromAngleAndSpeed(180, 400));
        return shot;
    }

    /**
     * Gets the Enemy location.
     *
     * @return the location
     */
    public Point getLocation() {
        return this.shape.getUpperLeft();
    }

    /**
     * Removes the from swarm.
     *
     * @param swarm - the swarm
     */
    public void removeFromSwarm(Swarm swarm) {
        swarm.removeEnemy(this);

    }

    /**
     * Sets the new place.
     *
     * @param x - the x axis coordinate
     * @param y - the y axis coordinate
     */
    public void setNewPlace(int x, int y) {
        int column = (this.formationNum / 6);
        int line = (this.formationNum % 6);
        this.shape = new Rectangle(new Point(x + (column * 50),  y + (line * 40)), 40, 30);
    }
}