import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-04-03 */
public class SpriteCollection {
    private List spriteCollection;

    /**
     * Contractor - Create a new array list to the sprites. */
    public SpriteCollection() {
        this.spriteCollection = new ArrayList();
        }

    /**
     * Add a sprite to the list of sprites.
     * <p>
     * @param s - the sprite that added to the list. */
    public void addSprite(Sprite s) {
        this.spriteCollection.add(s);
        }

    /**
     * Notify all the sprites in the list that time passed. */
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.spriteCollection.size(); i++) {
            Sprite s = (Sprite) this.spriteCollection.get(i);
            s.timePassed();
            }
        }

    /**
     * Draw all the sprites in the list.
     * <p>
     * @param surface - the surface of the sprite to draw. */
    public void drawAllOn(DrawSurface surface) {
        for (int i = 0; i < this.spriteCollection.size(); i++) {
            Sprite s = (Sprite) this.spriteCollection.get(i);
            s.drawOn(surface);
            }
        }
    }