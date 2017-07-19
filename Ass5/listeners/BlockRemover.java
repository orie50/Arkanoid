package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public class BlockRemover implements HitListener {
   private GameLevel game;
   private Counter blocksCounter;

   /** BlockRemover constructor.
    * <p>
    * @param game - the game to remove from.
    * @param blocksCounter - num of blocksCounter Counter. */
   public BlockRemover(GameLevel game, Counter blocksCounter) {
       this.game = game;
       this.blocksCounter = blocksCounter;
   }

   @Override
   /**
    * Called when beingHit object is hit.
    * <p>
    * @param beingHit - the block that hit.
    * @param hitter - the ball that hit. */
   public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getMaxHits() == 0) {
           beingHit.removeHitListener(this);
           beingHit.removeFromGame(game);
           this.blocksCounter.decrease(1);
           System.out.println(blocksCounter.getValue());
       }
   }
}