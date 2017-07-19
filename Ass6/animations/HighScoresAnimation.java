package animations;

import java.awt.Color;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import score.HighScoresTable;
import score.ScoreInfo;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-6 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable table;
    private String endKey;
    private boolean stop;
    private KeyboardSensor keyboard;

    /** HighScoresAnimation constructor.
     * <p>
     * @param keyboard - keyboard sensor.
     * @param table - the high score table.
     * @param endKey - key to stop the animation run (if pressed).
     *  */
    public HighScoresAnimation(HighScoresTable table, String endKey, KeyboardSensor keyboard) {
        this.table = table;
        this.endKey = endKey;
        this.stop = false;
        this.keyboard = keyboard;
    }

    @Override
    /** draw one frame of the animation.
    * <p>
    * @param d - a draw surface to draw the frame on.
    * @param dt - @param dt - the speed per frame. */
    public void doOneFrame(DrawSurface d, double dt) {
        List<ScoreInfo> scores = this.table.getHighScores();
        d.setColor(Color.GREEN);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.YELLOW);
        d.drawText(50, 50, "High Scores" , 50);
        d.drawText(150, 200, "Name" , 40);
        d.drawText(450, 200, "Score" , 40);
        d.setColor(Color.BLACK);
        d.drawLine(100, 205, 600, 205);
        d.drawLine(100, 208, 600, 208);
        int i = 250;
        for (ScoreInfo score : scores) {
            d.setColor(Color.BLACK);
            d.drawText(160, i, score.getName(), 30);
            d.drawText(460, i, Integer.toString(score.getScore()), 30);
            i += 50;
        }
        // Stop running the animation.
        if (this.keyboard.isPressed(this.endKey)) {
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