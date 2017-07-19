package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.HitNotifier;
/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public class BallRemover implements HitListener {
    private GameLevel game;

    /** BallRemover constructor.
     * <p>
     * @param game - the game to remove from.*/
    public BallRemover(GameLevel game) {
        this.game = game;
    }
    @Override
    /**
     * Called when beingHit object is hit.
     * <p>
     * @param beingHit - the block that hit.
     * @param hitter - the ball that hit. */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        hitter.removeFromGame(game);
    }
}
