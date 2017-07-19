import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-03-25 */
public class MultipleFramesBouncingBallsAnimation {
    /**
     * draw 2 frames with balls.
     * <p>
     * @param args - sizes for the balls. */
    public static void main(String[] args) {
        //create an array for half of the sizes
        String[] sizes = new String[args.length / 2];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = args[i];
        }
        Ball[] balls1 = MultipleBouncingBallsAnimation.getBallsArray(sizes, new Point(50, 50),
                                                                     new Point(500, 500));
        //create an array for the other half of the sizes
        sizes = new String[args.length - args.length / 2];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = args[i + args.length / 2];
        }
        Ball[] balls2 = MultipleBouncingBallsAnimation.getBallsArray(sizes, new Point(450, 450),
                                                                     new Point(600, 600));
        Sleeper sleeper = new Sleeper();
        GUI gui = new GUI("Multiple frames", 610, 610);
        while (true) {
            DrawSurface surface = gui.getDrawSurface();
            //draw 2 frames
            surface.setColor(Color.GRAY);
            surface.drawRectangle(50, 50, 450, 450);
            surface.setColor(Color.YELLOW);
            surface.drawRectangle(450, 450, 150, 150);
            MultipleBouncingBallsAnimation.drawBallArray(balls1, surface);
            MultipleBouncingBallsAnimation.drawBallArray(balls2, surface);
            gui.show(surface);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
            }
    }
}