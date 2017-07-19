package gameobjects;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-20 */
public class LevelName implements Sprite {

    private Color color;
    private String levelName;

    /**
     * Constructor - give the LevelName the name and the color.
     * <p>
     * @param levelName - string with the name of the level. */
    public LevelName(String levelName) {
        this.levelName = levelName;
        this.color = Color.BLACK;
    }


    @Override
    /**
     * draw the level name on the top of the screen.
     * <p>
     * @param surface - the given DrawSurface. */
    public void drawOn(DrawSurface surface) {
        String name = "levelName: " + this.levelName;
        surface.setColor(this.color);
        //draw the level name.
        surface.drawText(550, 17, name, 20);
    }

    @Override
    public void timePassed() {
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
