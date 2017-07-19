package listeners;
import gameobjects.Ball;
import gameobjects.Block;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public class PrintingHitListener implements HitListener {
    /**
     * Called when beingHit object is hit.
     * <p>
     * @param beingHit - the block that hit.
     * @param hitter - the ball that hit. */
   public void hitEvent(Block beingHit, Ball hitter) {
      System.out.println("A Block with " + beingHit.getMaxHits() + " points was hit.");
   }
}