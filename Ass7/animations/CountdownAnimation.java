package animations;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import gameobjects.SpriteCollection;
import geometry.Circle;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-06 */
public class CountdownAnimation implements Animation {
    private double frameRate;
    private int countLeft;
    private SpriteCollection gameScreen;
    private Sleeper sleeper;
    private boolean stop;
    /** CountdownAnimation constructor.
     * <p>
     * will display the given gameScreen,
     * for numOfSeconds seconds, and on top of them it will show
     * a countdown from countFrom back to 1
     * <p>
     * @param numOfSeconds - amount of sec to display the countdown.
     * @param countFrom - the num to count from.
     * @param gameScreen - the frame rate. */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.frameRate = (numOfSeconds * 1000) / (countFrom);
        this.countLeft = countFrom;
        this.stop = false;
        this.gameScreen = gameScreen;
        this.sleeper = new Sleeper();
    }
    /** draw one frame of the animation.
    * <p>
    * @param dt - the speed per frame.
    * @param d - a draw surface to draw the frame on. */
    public void doOneFrame(DrawSurface d, double dt) {
        // timing
        long startTime = System.currentTimeMillis();
        gameScreen.drawAllOn(d);
        //draw a background circle to the countdown display
        Circle background = new Circle(40);
        background.setX(d.getWidth() / 2);
        background.setY(d.getHeight() / 2);
        d.setColor(Color.GRAY);
        background.drawOn(d);
        d.setColor(Color.BLACK);
        //countdown logic
        if (this.countLeft > 0) {
            d.drawText(d.getWidth() / 2 - 6, d.getHeight() / 2 + 10, Integer.toString(this.countLeft), 32);
            this.countLeft--;
        } else {
            d.drawText(d.getWidth() / 2 - 6, d.getHeight() / 2 + 10, Integer.toString(this.countLeft), 32);
            this.stop = true;
        }
        // timing
        long usedTime = System.currentTimeMillis() - startTime;
        long milliSecondLeftToSleep = (long) this.frameRate - usedTime;
        if (milliSecondLeftToSleep > 0) {
            this.sleeper.sleepFor(milliSecondLeftToSleep);
        }
    }
    /** returns information about the continuation of the animation.
    * <p>
    * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
       return this.stop;
    }
}
