package gameobjects;
import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-04-02 */
public class Paddle implements Sprite, Collidable {

    private biuoop.KeyboardSensor keyboard;
    private Rectangle shape;
    private Color color;
    private double leftBoundary;
    private double rightBoundary;
    private int speed;

   /**
    * Contractor - Create the paddle as rectangle with color keyboard sensor and boundaries.
    * <p>
    * @param shape - the rectangle shape of the paddle.
    * @param color - the color of the paddle.
    * @param keyboard - the keyboard sensor of the paddle.
    * @param leftBoundary - the down left boundary of the frame.
    * @param rightBoundary - the down right boundary of the frame.
    * @param speed - the paddle speed. */
    public Paddle(Rectangle shape, Color color, int speed,
            KeyboardSensor keyboard, double leftBoundary, double rightBoundary) {
        this.speed = speed;
        this.keyboard = keyboard;
        this.shape = shape;
        this.color = color;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        }

    /**
     * The paddle move left. */
    public void moveLeft() {
        double x  = this.shape.getUpperLeft().getX();
        double y  = this.shape.getUpperLeft().getY();
        double width = this.shape.getWidth();
        double height = this.shape.getHeight();
        // If the paddle stay in the boundaries after the move, make the move left.
        if (x > this.leftBoundary) {
            this.shape = new Rectangle(new Point(x - speed, y), width, height);
            } else {
                // Else go to the left boundary.
                this.shape = new Rectangle(new Point(this.leftBoundary, y), width, height);
                }
        }

    /**
     * The paddle move right. */
    public void moveRight() {
        double x  = this.shape.getUpperLeft().getX();
        double y  = this.shape.getUpperLeft().getY();
        double width = this.shape.getWidth();
        double height = this.shape.getHeight();
     // If the paddle stay in the boundaries after the move, make the move right.
        if (x + width + speed < this.rightBoundary) {
            this.shape = new Rectangle(new Point(x + speed, y), width, height);
            } else {
                // Else go to the right boundary.
                this.shape = new Rectangle(new Point(this.rightBoundary - width, y), width, height);
                }
        }

    /**
     * Move the paddle when notify that time passed.
     * If pressed left arrow move left, if pressed right arrow move right. */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
            }
        }

    /**
     * @return the rectangle that the trajectory collision with it. */
    public Rectangle getCollisionRectangle() {
        return this.shape;
        }

    /**
     * Change the velocity of the ball when it hit the paddle.
     * Check where the ball hit and return the velocity (like the block).
     *  the upper part of the paddle divided to 5,
     *  and every part return a different angle of the velocity.
     * <p>
     * @param hitter - the ball that hit the paddle.
     * @param collisionPoint - the collision point of the ball with the paddle.
     * @param currentVelocity - the current velocity of the point.
     * @return the new velocity vector of the ball. */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Divide the paddle to 4 parts.
        final int up = 0;
        final int right = 1;
        final int down = 2;
        final int left = 3;
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Velocity newVelocity = null;
        // Check where the ball hit.
        int hitPlace = this.shape.placeInsideMe(collisionPoint);
        switch(hitPlace) {
        // If the ball hit in the upper part, divide the paddle to 5 equal parts.
        case up:
            final int upLeft = 0;
            final int leftMiddle = 1;
            final int middle = 2;
            final int rightMiddle = 3;
            final int upRight = 4;
            double ballSpeed = currentVelocity.getSpeed();
            // Divide the upper part and check where the ball hit.
            hitPlace = this.divideUpPaddle(collisionPoint, 5);
            switch(hitPlace) {
            case upLeft:
                newVelocity = Velocity.fromAngleAndSpeed(300, ballSpeed);
                break;
            case leftMiddle:
                newVelocity = Velocity.fromAngleAndSpeed(330, ballSpeed);
                break;
            case middle:
                newVelocity = Velocity.fromAngleAndSpeed(0, ballSpeed);
                break;
            case rightMiddle:
                newVelocity = Velocity.fromAngleAndSpeed(30, ballSpeed);
                break;
            case upRight:
                newVelocity = Velocity.fromAngleAndSpeed(60, ballSpeed);
                break;
            default:
                System.out.println("Error: no velocity");
                }
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
    return newVelocity;
        }

    /**
     * Give the part of the paddle that the ball hit.
     * Divide the upper line of the paddle and check in what part the ball hit.
     * <p>
     * @param collision - the collision point of the ball with the paddle.
     * @param divides - the numbers of parts than the paddle divided.
     * @return in what divided part the ball hit. */
    public int divideUpPaddle(Point collision, int divides) {
        double x = this.shape.getUpperLeft().getX();
        double y = this.shape.getUpperLeft().getY();
        Line[] lines = new Line[divides];
        int i;
        double divide = this.shape.getWidth() / divides;
        for (i = 0; i < divides; i++) {
            lines[i] = new Line(new Point(x + (divide * i), y), (new Point(x + (divide * (i + 1)), y)));
            }
        i = 0;
        while (i < divides) {
            if (collision.isInLine(lines[i])) {
                break;
                }
            i++;
        }
        return i;
    }

    /**
     * Add the paddle to the game as sprite and as collidable.
     * <p>
     * @param game - the game that the paddle added to. */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
        }

    /**
    * remove the paddle from the game as sprite and as collidable.
    * <p>
    * @param game - the game that the paddle added to. */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * Draw the paddle.
     * <p>
     * @param surface - the surface of the paddle that draw. */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        this.shape.drawOn(surface);
        }

    }