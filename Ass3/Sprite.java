import biuoop.DrawSurface;

/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-04-03 */
public interface Sprite {

    /**
     * DrawOn the sprite.
     * <p>
     * @param sprite - the surface of the sprite to draw.    */
   void drawOn(DrawSurface sprite);

   /**
    * Notify the sprite that the time passed. */
   void timePassed();
}