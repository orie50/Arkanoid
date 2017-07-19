package gameobjects;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import listeners.Counter;
/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-12 */
public class ScoreIndicator implements Sprite {

    private final Color color = Color.BLACK;
    private Counter scoreCounter;

    /** ScoreIndicator constructor.
     * <p>
     * @param scoreCounter - Counter*/
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.white);
        surface.fillRectangle(200, 0, 300, 20);
        String hits = "Score: " + Integer.toString(this.scoreCounter.getValue());
        surface.setColor(this.color);
        surface.drawText(300, 17, hits, 20);
    }

    @Override
    public void timePassed(double dt) {
    }

    /** add the ScoreIndicator to the game Database.
     * <p>
     * @param game - the game to be add to*/
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /** remove the ScoreIndicator from the game Database.
     * <p>
     * @param game - the game to be remove*/
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

}
