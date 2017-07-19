

import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

/**
* @author Adiel Cahana <adielcahana@gmail.com>
* @version 1.0
* @since 2016-03-22 */
public class AbstractArtDrawing {
    private int width;
    private int height;
    private int numOfLines;

    /**
     * Constructor of the class.
     * <p>
     * Create the frame and the numbers of the lines.
     * <p>
     * @param width - the width of the frame.
     * @param height - the height of the frame.
     * @param numOfLines -  the numbers of lines to draw. */
    public AbstractArtDrawing(int width, int height, int numOfLines) {
        this.width = width;
        this.height = height;
        this.numOfLines = numOfLines;
    }

    /**
     * Draw a single line.
     * @param l - the line to draw.
     * @param d - the surface to draw. */
    public void drawLine(Line l, DrawSurface d) {
        Point start = l.start();
        Point end = l.end();
        d.setColor(Color.BLACK);
        d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
    }

    /**
     * Draw the point in blue.
     * @param p - the point to draw.
     * @param d - the surface to draw */
    public void drawPoint(Point p, DrawSurface d) {
        //d.setColor(Color.BLUE);
        d.fillCircle((int) p.getX(), (int) p.getY(), 3);
    }

    /**
     * Create a random line.
     * Give the line a random start, end points and create a new line.
     * @return the random line that created. */
    public Line generateRandomLine() {
        Point start = generateRandomPoint();
        Point end = generateRandomPoint();
        Line randline = new Line(start, end);
        return randline;
        }

    /**
     * Create a random point.
     * Give the point a random x,y values in the frame boundaries and create a new point.
     * @return the random point that created. */
    public Point generateRandomPoint() {
        Random rand = new Random();
        double x = rand.nextInt(this.width) + 1;
        double y = rand.nextInt(this.height) + 1;
        Point randPoint = new Point(x, y);
        return randPoint;
        }

    /**
     * Create a list of lines.
     * Create a new list of random lines.
     * @return the line list that created. */
    public Line[] lineList() {
        Line[] listOfLines = new Line[this.numOfLines];
        Line newLine = null;
        int i = 0 , j;
        // Create the numbers of lines that given.
        while (i < this.numOfLines) {
            newLine = generateRandomLine();
            j = 0;
            while (j <= i) {
                if (newLine.equals(listOfLines[i])) {
                    break;
                    }
                j++;
                }
            if (j > i) {
                // Add new line to the list.
                listOfLines[i] = newLine;
                i++;
                }
            }
        return listOfLines;
    }

    /**
     * Draw the lines in a graph.
     * Create a list of random lines and draw the lines and the middle, intersection points in the graph. */
    public void drawGraph() {
        Line[] listOfLines = lineList();
        Point intersectPoint = null;
        // Create the frame.
        GUI gui = new GUI("Random Abstraect Art", width, height);
        DrawSurface d = gui.getDrawSurface();
        // Draw all the lines in the graph.
        for (int i = 0; i < this.numOfLines; i++) {
            drawLine(listOfLines[i], d);
            d.setColor(Color.BLUE);
            // Draw the middle points of all the lines in blue.
            drawPoint(listOfLines[i].middle(), d);
            for (int j = i + 1; j < this.numOfLines; j++) {
                intersectPoint = listOfLines[i].intersectionWith(listOfLines[j]);
                //if (listOfLines[i].isIntersecting(listOfLines[j]) == true){
                if (intersectPoint != null) {
                    d.setColor(Color.RED);
                    // Draw all the intersection points in red.
                    drawPoint(intersectPoint, d);
                    }
                }
            }
        gui.show(d);
    }
    /**
     * The main method of the class.
     * Create a graph and draw a random lines and points in it.
     * @param args - the arguments that the main get. */
    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing(500, 500, 10);
        art.drawGraph();
        }
    }