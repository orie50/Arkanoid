package gameobjects;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import listeners.Counter;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-15 */
public class LivesIndicator implements Sprite {
    private Color color;
    private Counter lives;

    /**
     * Constructor - give the LivesIndicator the number of lives and the color.
     * <p>
     * @param lives - counter of the lives. */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
        this.color = Color.BLACK;
    }

    @Override
    /**
     * draw the Lives that left on the top of the screen.
     * <p>
     * @param surface - the given DrawSurface. */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.white);
        surface.fillRectangle(0, 0, 200, 20);
        String live = "lives: " + Integer.toString(this.lives.getValue());
        surface.setColor(this.color);
        surface.drawText(100, 17, live, 20);
    }

    @Override
    public void timePassed(double dt) {
    }

    /**
     * adds the sprite to the game.
     * <p>
     * @param game - the game. */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * remove the sprite from the game.
     * <p>
     * @param game - the game*/
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}
