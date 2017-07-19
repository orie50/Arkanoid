package gameobjects;

import geometry.Point;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-03-25 */
public class Velocity {
    private double dx;
    private double dy;
    /**
     * velocity constructor.
     * <p>
     * @param dx - the change in x axis.
     * @param dy - the change in y axis.*/
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * returns the velocity change in x.
     * <p>
     * @return this.dx - the change in x axis */
    public double getDx() {
        return this.dx;
    }
    /**
     * returns the velocity change in y.
     * <p>
     * @return this.dy - the change in y axis */
    public double getDy() {
        return this.dy;
    }
    /**
     * Take a point with position (x,y) and return a new point
     * with position (x + dx, y + dy).
     * <p>
     * @param point - the point to be changed.
     * @param dt - the speed per frame.
     * @return new Point - the new point after the change */
    public Point applyToPoint(Point point, double dt) {
        return new Point(point.getX() + this.dx * dt, point.getY() + this.dy * dt);
    }
    /**
     * calculate the velocity speed ( the 'size' of the speed)
     * and returns it.
     * <p>
     * @return speed */
    public double getSpeed() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }

    /**
     * Take a point with position (x,y) and return a new point
     * with position (x + dx, y + dy).
     * <p>
     * @param  angle - velocity vector angle.
     * @param  speed - velocity vector size.
     * @return new Velocity - the new Velocity */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.cos(Math.toRadians(angle - 90));
        double dy = speed * Math.sin(Math.toRadians(angle - 90));
        return new Velocity(dx, dy);
    }

    /**
     * Dx setter.
     * <p>
     * @param x - the change in x axis. */
    public void setDx(double x) {
        this.dx = x;
    }

    /**
     * Dy setter.
     * <p>
     * @param y - the change in y axis. */
    public void setDy(double y) {
        this.dy = y;
    }
}
