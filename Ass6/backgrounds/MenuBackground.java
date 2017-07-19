package backgrounds;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import gameobjects.Cloud;
import gameobjects.Sprite;
import geometry.Point;
import geometry.Rectangle;
/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-10 */
public class MenuBackground implements Sprite {

    private Rectangle frame;
    private Color color;

    /** Constructor - create the background rectangle and give it color. */
    public MenuBackground() {
        this.frame = new Rectangle(new Point(0, 0), 800, 600);
        this.color = new Color(0, 128, 255);
    }

    @Override
    /**
     * draw the background.
     * <p>
     * @param surface - the given DrawSurface. */
    public void drawOn(DrawSurface surface) {
        // Draw the background.
        surface.setColor(this.color);
        this.frame.fillOn(surface);
        // Draw 2 clouds.
        Cloud cloud1 = new Cloud(600, 500, 30, 100);
        cloud1.drawOnCloud1(surface);
        Cloud cloud2 = new Cloud(100, 400, 15, 210);
        cloud2.drawOnCloud2(surface);
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
