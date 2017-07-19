package listeners;

import gameobjects.Ball;
import gameobjects.HitNotifier;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /** BlockRemover constructor.
     * <p>
     * @param currentScore - score Counter. */
    public ScoreTrackingListener(Counter currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    /**
     * Called when beingHit object is hit.
     * <p>
     * @param beingHit - the block that hit.
     * @param hitter - the ball that hit. */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        if (hitter.getVelocity().getDy() > 0) {
            return;
        }
        this.currentScore.increase(100);
    }
}
