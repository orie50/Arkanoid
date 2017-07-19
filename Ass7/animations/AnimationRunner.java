package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-06 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    /** AnimationRunner constructor.
     * <p>
     * @param gui - GUI.
     * @param framesPerSecond - the frame rate. */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }
    /** Animation running method.
     * <p>
     * @param animation - the animation to run. */
    public void run(Animation animation) {
       int millisecondsPerFrame = 1000 / this.framesPerSecond;
       while (!animation.shouldStop()) {
          // timing
          long startTime = System.currentTimeMillis();
          DrawSurface d = gui.getDrawSurface();
          double dt = 1 / (double) this.framesPerSecond;
          //create one frame of the animation
          animation.doOneFrame(d, dt);
          gui.show(d);
          // timing
          long usedTime = System.currentTimeMillis() - startTime;
          long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
          if (milliSecondLeftToSleep > 0) {
              this.sleeper.sleepFor(milliSecondLeftToSleep);
          }
       }
    }
}
