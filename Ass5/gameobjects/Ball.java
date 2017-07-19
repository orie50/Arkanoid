package gameobjects;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import general.CollisionInfo;
import general.GameEnvironment;
import geometry.Line;
import geometry.Point;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-03-25 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment enviroment;

    /**
     * Ball constructor.
     * <p>
     * @param center - the change in x axis.
     * @param r - the change in y axis.
     * @param color - the change in y axis.*/
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }
    /**
        Ball constructor.
     * <p>
     * @param x - coordinate.
     * @param y - coordinate.
     * @param r - the change in y axis.
     * @param color - the change in y axis.*/
    public Ball(int x, int y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }
    /**
       Ball constructor.
     * <p>
     * @param center - the change in x axis.
     * @param r - the change in y axis.
     * @param color - the change in y axis.
     * @param enviroment - the game envitoment*/
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment enviroment) {
        this(center, r, color);
        this.enviroment = enviroment;
    }
    /**
       Ball constructor.
     * <p>
     * @param x - coordinate.
     * @param y - coordinate.
     * @param r - the change in y axis.
     * @param color - the change in y axis.
     * @param enviroment - the game envitoment*/
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment enviroment) {
        this(new Point(x, y), r, color);
        this.enviroment = enviroment;
    }

    /**
     * x coordinate query.
     * <p>
     * @return x - x coordinate*/
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * y coordinate query.
     * <p>
     * @return y - y coordinate*/
    public int getY() {
        return (int) this.center.getY();
    }
    /**
     * center point query.
     * <p>
     * @return point - center*/
    public Point getCenter() {
        return this.center;
    }
    /**
     * radius query.
     * <p>
     * @return this.radius - radius*/
    public int getSize() {
        return this.radius;
    }
    /**
     * color query.
     * <p>
     * @return Color - ball color*/
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * drawing ball method on the given DrawSurface.
     * <p>
     * @param surface - the given DrawSurface*/
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
    }
    /**
     * velocity query.
     * <p>
     * @return velocity - this.velocity*/
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * velocity setter.
     * <p>
     * @param v - a give new velocity*/
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**
     * velocity setter.
     * <p>
     * @param dx - change in x axis
     * @param dy - change in y axis */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Set the environment to the ball.
     * <p>
     * @param envi - the environment to set to the ball. */
    public void setGameEnvironment(GameEnvironment envi) {
        this.enviroment = envi;
    }

    /**
     * computes the ball trajectory.
     * <p>
     * @return line representation of the trajectory */
    public Line getTrajectory() {
        //compute the frame width and length
        double width = this.enviroment.getLowerFrameEdge().getX() - this.enviroment.getUpperFrameEdge().getX();
        double height = this.enviroment.getLowerFrameEdge().getY() - this.enviroment.getUpperFrameEdge().getY();
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        //the frame diagonal is the longest trajectory inside the frame
        double diagonal = Math.sqrt((width * width) + (height * height));
        // the (x,y) coordinate of the assumed trajectory
        double endX = this.getCenter().getX() + diagonal * dx;
        double endY = this.getCenter().getY() + diagonal * dy;
        return new Line(this.getCenter(), new Point(endX, endY));
    }

    /**
     * ball animation step.
     * <p>
     * changes the center according to the velocity
     * <p>
     * keeps the ball moving correctly in the environment*/
    public void moveOneStep() {
        Line trajectory = this.getTrajectory();
        CollisionInfo info = this.enviroment.getClosestCollision(trajectory);
        //trajectory.drawOn(this.enviroment.getSurface(), Color.BLACK);
        if (info != null) {
            //info.collisionPoint().drawOn(this.enviroment.getSurface(), Color.RED);
            //if a collision will occcure in the next step, change the ball velocity
            //checks if the ball next step will pass the collision point
            if (info.collisionPoint().distance(this.center) - this.velocity.getSpeed() * 1.2 <= this.getSize()) {
                this.velocity = info.collisionObject().hit(this, info.collisionPoint(), this.velocity);
            }
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * adds the ball to the game.
     * <p>
     * @param game - the game*/
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * remove the ball from the game.
     * <p>
     * @param game - the game*/
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
    /**
     * notify the ball that the main animation loop continued.*/
    public void timePassed() {
        this.moveOneStep();
    }
}