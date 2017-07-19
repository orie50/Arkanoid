package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.HitNotifier;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17 */
public class BonusRemover implements HitListener {
    private GameLevel game;
    private Counter score;

    /** BonusRemover constructor.
     * <p>
     * @param game - the game to remove from.
     * @param score - the score Counter. */
    public BonusRemover(GameLevel game, Counter score) {
        this.game = game;
        this.score = score;
    }

    @Override
    /**
     * Called when beingHit object is hit.
     * <p>
     * @param beingHit - the bonus ship.
     * @param hitter - the ball that hit it. */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        game.removeSprite(beingHit);
        this.score.increase(500);
    }
}