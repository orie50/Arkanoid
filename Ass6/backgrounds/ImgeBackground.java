package backgrounds;

import java.awt.image.BufferedImage;

import animations.GameLevel;
import biuoop.DrawSurface;
import gameobjects.Sprite;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-5 */
public class ImgeBackground implements Sprite {

    private BufferedImage image;

    /**
     * Constructor - give the buffer image for the background.
     * <p>
     * @param image - the background image. */
    public ImgeBackground(BufferedImage image) {
        this.image = image;
    }

    @Override
    /**
     * Draw the Background
     * Draw image as the background.
     * <p>
     * @param surface - the given surface to be drew on. */
    public void drawOn(DrawSurface surface) {
        surface.drawImage(0, 0, image);
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
