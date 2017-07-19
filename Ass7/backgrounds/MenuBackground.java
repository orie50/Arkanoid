package backgrounds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import animations.GameLevel;
import biuoop.DrawSurface;
import gameobjects.Sprite;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-10 */
public class MenuBackground implements Sprite {

    private BufferedImage image;

    /** Constructor - create the background rectangle and give it color. */
    public MenuBackground() {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("space invaders.jpg");
        try {
            this.image = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("can't load background img");
            System.exit(1);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println("can't close background img");
                System.exit(1);
            }
        }
    }

    @Override
    /**
     * draw the background.
     * <p>
     * @param surface - the given DrawSurface. */
    public void drawOn(DrawSurface surface) {
        surface.drawImage(0, 0, image);
    }

    /**
     * adds the background to the game.
     * <p>
     * @param game - the game. */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * remove the background from the game.
     * <p>
     * @param game - the game*/
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub
    }
}
