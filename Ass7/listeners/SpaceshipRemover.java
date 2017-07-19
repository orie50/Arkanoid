package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.HitNotifier;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17 */
public class SpaceshipRemover implements HitListener {
    private GameLevel game;
    private Counter numberOfSpaceships;

    /** BlockRemover constructor.
     * <p>
     * @param game - the game to remove from.
     * @param numberOfSpaceships - num of Spaceships Counter. */
    public SpaceshipRemover(GameLevel game, Counter numberOfSpaceships) {
        this.game = game;
        this.numberOfSpaceships = numberOfSpaceships;
    }

    @Override
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        this.numberOfSpaceships.decrease(1);
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);
    }
}