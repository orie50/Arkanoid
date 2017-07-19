package geometry;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * @author Adiel Chana <adielcahana@gmail.com>
 * @version 1.0
 * @since 2016-03-27 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /** Create a new rectangle with location and width/height.
     * <p>
     * @param upperLeft - coordinate
     * @param width - width of the rectangle
     * @param height - height of the rectangle
     * */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /** Return a (possibly empty) List of intersection points
     * with the specified line.
     * <p>
     * @param line - the line to check intersection with the rectangle
     * @return intersection List*/
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<Point>();
        //get all the rectangle lines
        Line[] lines = this.myLines();
        //check if the line intersect with all the rectangle
        for (int i = 0; i < 4; i++) {
            if (line.isIntersecting(lines[i])) {
                intersectionPoints.add(line.intersectionWith(lines[i]));
            }
        }
        return intersectionPoints;
    }

    /** returns a numeric representation of the edge
     * that been hit.
     * <p>
     * the numeric logic follows myLines() logic
     * @param p - the intersection point
     * @return edge index*/
    public int placeInsideMe(Point p) {
        Line[] lines = this.myLines();
        int i = 0;
        //check for each of the rectangle lines if p is in it
        while (i < 4) {
            if (p.isInLine(lines[i])) {
                break;
            }
            i++;
        }
        return i;
    }
    /** returns an array of the rectangle edge.
     * <p>
     * the array order is 0 for the upper line and so on
     * moving clockwise on the rectangle edges
     * @return lines - the rectangle lines*/
    public Line[] myLines() {
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY();
        Line[] lines = new Line[4];
        //up
        lines[0] = new Line(x, y, x + width, y);
        //right
        lines[1] = new Line(x + width, y, x + width, y + height);
        //down
        lines[2] = new Line(x, y + height, x + width, y + height);
        //left
        lines[3] = new Line(x, y, x, y + height);
        return lines;
    }

    /** returns the rectangle width.
     * <p>
     * @return width*/
    public double getWidth() {
        return this.width;
    }

    /** returns the rectangle height.
     * <p>
     * @return width*/
    public double getHeight() {
        return this.height;
    }

    /** Returns the upper-left point of the rectangle.
     * <p>
     * @return upper left point*/
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /** draws the rectangle on a given DrawSurface.
     * <p>
     * @param surface - the DrawSurface*/
    public void fillOn(DrawSurface surface) {
        surface.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) this.width, (int) this.height);
    }

    /** draws the rectangle frame on a given DrawSurface.
     * <p>
     * @param surface - the DrawSurface*/
    public void drawOn(DrawSurface surface) {
        surface.drawRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) this.width, (int) this.height);
    }

}