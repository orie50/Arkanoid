package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import listeners.Counter;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-06 */
public class EndGameAnimation implements Animation {
    private boolean stop;
    private boolean win;
    private KeyboardSensor keyboard;
    private Counter scoreCounter;

    /** End Game Animation constructor.
     * <p>
     * @param win - game situation (winner or looser).
     * @param scoreCounter - score.
     * @param keyboard - keyboard sensor. */
    public EndGameAnimation(boolean win, Counter scoreCounter, KeyboardSensor keyboard) {
        this.stop = false;
        this.win = win;
        this.scoreCounter = scoreCounter;
        this.keyboard = keyboard;
    }

    @Override
    /** draw one frame of the animation.
    * <p>
    * @param dt - the speed per frame.
    * @param d - a draw surface to draw the frame on. */
    public void doOneFrame(DrawSurface d, double dt) {
        if (win) {
            d.drawText((d.getWidth() / 5) + 2, d.getHeight() / 2,
                       "You Win! Your score is " + this.scoreCounter.getValue(), 32);
        } else {
            d.drawText((d.getWidth() / 5) + 2, d.getHeight() / 2,
                       "Game Over. Your score is " + this.scoreCounter.getValue(), 32);
        }
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
        return this.stop;
    }

}