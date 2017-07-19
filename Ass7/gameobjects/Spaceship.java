package gameobjects;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import animations.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import listeners.ShootListener;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-02 */
public class Spaceship implements Sprite, Collidable, HitNotifier, ShootNotifier {

    private biuoop.KeyboardSensor keyboard;
    private Rectangle shape;
    private BufferedImage image;
    private double leftBoundary;
    private double rightBoundary;
    private int speed;
    private List<HitListener> hitListeners;
    private List<ShootListener> shootListeners;

    /**
     * Contractor - Create the Spaceship as rectangle with color keyboard sensor and boundaries.
     * <p>
     * @param shape - the rectangle shape of the Spaceship.
     * @param keyboard - the keyboard sensor of the Spaceship.
     * @param image - the spaceship image.
     * @param leftBoundary - the down left boundary of the frame.
     * @param rightBoundary - the down right boundary of the frame.
     * @param speed - the Spaceship speed. */
    public Spaceship(Rectangle shape, int speed, KeyboardSensor keyboard,
            double leftBoundary, double rightBoundary, BufferedImage image) {
        this.speed = speed;
        this.keyboard = keyboard;
        this.shape = shape;
        this.image = image;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.hitListeners = new ArrayList<HitListener>();
        this.shootListeners = new ArrayList<ShootListener>();
    }

    /**
     * The Spaceship move left.
     * <p>
     * @param dt - the speed per frame. */
    public void moveLeft(double dt) {
        double x  = this.shape.getUpperLeft().getX();
        double y  = this.shape.getUpperLeft().getY();
        double width = this.shape.getWidth();
        double height = this.shape.getHeight();
        // If the Spaceship stay in the boundaries after the move, make the move left.
        if (x > this.leftBoundary) {
            this.shape = new Rectangle(new Point(x - (speed * dt), y), width, height);
        } else {
            // Else go to the left boundary.
            this.shape = new Rectangle(new Point(this.leftBoundary, y), width, height);
        }
    }

    /**
     * The Spaceship move right.
     * <p>
     * @param dt - the speed per frame. */
    public void moveRight(double dt) {
        double x  = this.shape.getUpperLeft().getX();
        double y  = this.shape.getUpperLeft().getY();
        double width = this.shape.getWidth();
        double height = this.shape.getHeight();
        // If the Spaceship stay in the boundaries after the move, make the move right.
        if (x + width + (speed * dt) < this.rightBoundary) {
            this.shape = new Rectangle(new Point(x + (speed * dt), y), width, height);
        } else {
            // Else go to the right boundary.
            this.shape = new Rectangle(new Point(this.rightBoundary - width, y), width, height);
        }
    }

    /**
     * Move the Spaceship when notify that time passed.
     * If pressed left arrow move left, if pressed right arrow move right.
     * if pressed space the Spaceship shoot.
     * <p>
     * @param dt - the speed per frame. */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.notifyShoot(this.shoot());
        }
    }

    /**
     * The spaceship shoot a little ball.
     * <p>
     * @return the ball that shoot by the spaceship. */
    private Ball shoot() {
        Ball shot = new Ball((int) (this.shape.getUpperLeft().getX() + this.shape.getWidth() / 2),
                (int) this.shape.getUpperLeft().getY() - 10, 3, Color.WHITE);
        shot.setVelocity(Velocity.fromAngleAndSpeed(0, 400));
        return shot;
    }

    /**
     * @return the rectangle that the trajectory collision with it. */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * If a shot hit the spaceship, notify a hit.
     * <p>
     * @param hitter - the shot that hit the spaceship.
     * @param collisionPoint - the collision point of the ball with the spaceship.
     * @param currentVelocity - the current velocity of the point.
     * @return the new velocity vector of the ball. */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        return null;
    }

    /**
     * Add the spaceship to the game as sprite and as collidable.
     * <p>
     * @param game - the game that the spaceship added to. */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * remove the spaceship from the game as sprite and as collidable.
     * <p>
     * @param game - the game that the spaceship added to. */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * Draw the spaceship.
     * <p>
     * @param surface - the surface of the spaceship that draw. */
    public void drawOn(DrawSurface surface) {
        surface.drawImage((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(), this.image);
    }

    /** Notify all the listeners that about the hit.
     * <p>
     * @param hitter - the ball that hit the spaceship. */
    private void notifyHit(Ball hitter) {
        // Copy the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /** Notify all the listeners that about the shot.
     * <p>
     * @param shot - the ball that hit the spaceship. */
    private void notifyShoot(Ball shot) {
        // Copy the hitListeners before iterating over them.
        List<ShootListener> listeners = new ArrayList<ShootListener>(this.shootListeners);
        // Notify all listeners about a hit event.
        for (ShootListener hl : listeners) {
            hl.shootEvent(shot);
        }
    }

    @Override
    /**
     * Add hit listener to the list of hit listeners.
     * <p>
     * @param hl - the hit listener. */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    /**
     * remove the hit listener from the list of hit listeners.
     * <p>
     * @param hl - the hit listener. */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    @Override
    /**
    * Add shoot listener to the list of shot listeners.
    * <p>
    * @param sl - the shot listener. */
    public void addShootListener(ShootListener sl) {
        this.shootListeners.add(sl);
    }

    @Override
    /**
    * remove the shoot listener from the list of shot listeners.
    * <p>
    * @param sl - the shot listener. */
    public void removeShootListener(ShootListener sl) {
        this.shootListeners.add(sl);
    }
}