package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.HitNotifier;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public class BlockRemover implements HitListener {
    private GameLevel game;

    /** BlockRemover constructor.
     * <p>
     * @param game - the game to remove from.*/
    public BlockRemover(GameLevel game) {
        this.game = game;
    }

    @Override
    /**
     * Called when beingHit object is hit.
     * <p>
     * @param beingHit - the block that hit.
     * @param hitter - the ball that hit. */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);
    }
}