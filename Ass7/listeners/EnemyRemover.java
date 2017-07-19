package listeners;

import animations.GameLevel;
import gameobjects.Ball;
import gameobjects.Enemy;
import gameobjects.HitNotifier;
import gameobjects.Swarm;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17 */
public class EnemyRemover implements HitListener {
    private GameLevel game;
    private Swarm swarm;
    private Counter enemyCounter;

    /** Enemy Remover constructor.
     * <p>
     * @param game - the game to remove from.
     * @param enemyCounter - num of enemys Counter.
     * @param swarm - the swarm */
    public EnemyRemover(GameLevel game, Counter enemyCounter, Swarm swarm) {
        this.game = game;
        this.swarm = swarm;
        this.enemyCounter = enemyCounter;
    }

    @Override
    /**
     * Called when Enemy object is hit.
     * <p>
     * @param beingHit - the Enemy that been hit.
     * @param hitter - the ball that hit. */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        //downward moving balls are only from enemys
        // if its an enemy shot' do nothing
        if (hitter.getVelocity().getDy() > 0) {
            return;
        }
        ((Enemy) beingHit).removeFromSwarm(this.swarm);
        beingHit.removeFromGame(this.game);
        this.enemyCounter.decrease(1);
    }
}