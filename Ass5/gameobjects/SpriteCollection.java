package gameobjects;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-04-03 */
public class SpriteCollection {
    private List<Sprite> spriteCollection;

    /**
     * Contractor - Create a new array list to the sprites. */
    public SpriteCollection() {
        this.spriteCollection = new ArrayList<Sprite>();
        }

    /**
     * Add a sprite to the list of sprites.
     * <p>
     * @param s - the sprite that added to the list. */
    public void addSprite(Sprite s) {
        this.spriteCollection.add(s);
        }

    /**
     * Remove a sprite from the list of sprites.
     * <p>
     * @param s - the sprite that added to the list. */
    public void removeSprite(Sprite s) {
        this.spriteCollection.remove(s);
        }


    /**
     * Notify all the sprites in the list that time passed. */
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.spriteCollection.size(); i++) {
            Sprite s = this.spriteCollection.get(i);
            s.timePassed();
            }
        }

    /**
     * Draw all the sprites in the list.
     * <p>
     * @param surface - the surface of the sprite to draw. */
    public void drawAllOn(DrawSurface surface) {
        for (int i = 0; i < this.spriteCollection.size(); i++) {
            Sprite s = this.spriteCollection.get(i);
            s.drawOn(surface);
            }
        }
    }