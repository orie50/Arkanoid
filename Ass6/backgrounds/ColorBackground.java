package backgrounds;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import gameobjects.Sprite;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-5 */
public class ColorBackground implements Sprite {

    private Color color;

    /**
     * Constructor - give the background his color.
     * <p>
     * @param color - the background color. */
    public ColorBackground(Color color) {
        this.color = color;
    }

    @Override
    /**
     * Draw the Background
     * Fill rectangle as the screen size with the given color.
     * <p>
     * @param surface - the given surface to be drew on. */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle(0, 0, surface.getWidth(), surface.getHeight());
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    /**
     * adds the background to the game.
     * <p>
     * @param game - the game. */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    @Override
    /**
     * remove the background from the game.
     * <p>
     * @param game - the game*/
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

}
