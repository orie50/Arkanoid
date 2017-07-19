import biuoop.DrawSurface;

import java.awt.Color;;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-04-02 */
public class Circle {

    private double x;
    private double y;
    private double radius;

    /** Circle constructor.
     * <p>
     * @param radius - the radius of the circle. */
    public Circle(double radius) {
        this.x = 0;
        this.y = 0;
        this.radius = radius;
    }

    /**
     * Give the center point circle x value.
     * <p>
     * @param xCenter - the center point x value. */
    public void setX(double xCenter) {
        this.x = xCenter;
    }

    /**
     * Give the center point circle y value.
     * <p>
     * @param yCenter - the center point y value. */
    public void setY(double yCenter) {
        this.y = yCenter;
    }

    /**
     * Draw the circle.
     * Fill the circle in blue and draw the frame in black.
     * <p>
     * @param ds - the surface of the circle to draw. */
    public void drawOn(DrawSurface ds) {
        ds.setColor(Color.BLUE);
        ds.fillCircle((int) x, (int) y, (int) radius);
        ds.setColor(Color.BLACK);
        ds.drawCircle((int) x, (int) y, (int) radius);
    }
}