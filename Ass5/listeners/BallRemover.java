package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;
/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter ballsCounter;

    /** BallRemover constructor.
     * <p>
     * @param game - the game to remove from.
     * @param ballsCounter - num of balls Counter. */
    public BallRemover(GameLevel game, Counter ballsCounter) {
        this.game = game;
        this.ballsCounter = ballsCounter;
    }
    @Override
    /**
     * Called when beingHit object is hit.
     * <p>
     * @param beingHit - the block that hit.
     * @param hitter - the ball that hit. */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        ballsCounter.decrease(1);
    }
}
