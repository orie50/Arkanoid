import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
* @author Adiel Cahana <adielcahana@gmail.com>
* @version 1.0
* @since 2016-03-22 */
public class BouncingBallAnimation {

    /**
     * Draw a bouncing ball.
     * <p>
     * Create a ball and a frame and draw the bouncing ball.
     * <p>
     * @param args - the string of numbers (the size of the balls). */
    public static void main(String[] args) {
        // Create the frame.
        GUI gui = new GUI("title", 400, 400);
        Sleeper sleeper = new Sleeper();
        // Make a new ball and give it velocity.
        Ball ball = new Ball(50, 50, 30, java.awt.Color.BLACK, new Point(400, 400), new Point(0, 0));
        Velocity v = Velocity.fromAngleAndSpeed(10, 10);
        ball.setVelocity(v);
        // Draw the ball while he move.
        while (true) {
            // Move the ball.
            ball.moveOneStep();
            DrawSurface surface = gui.getDrawSurface();
            // Draw the ball in the frame.
            ball.drawOn(surface);
            gui.show(surface);
            // Wait for 50 milliseconds.
            sleeper.sleepFor(50);
            }
        }
    }