package listeners;

import java.util.List;

import animations.GameLevel;
import gameobjects.Ball;
import general.GameEnvironment;


/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17
 */
public class EnemyShotListener implements ShootListener {

    private List<Ball> shots;
    private GameLevel game;
    private GameEnvironment environment;

    /**
     * Instantiates a new enemy shot listener.
     *
     * @param shots - the game's pool of balls
     * @param game - the game
     * @param environment - the environment
     */
    public EnemyShotListener(List<Ball> shots, GameLevel game, GameEnvironment environment) {
        this.shots = shots;
        this.game = game;
        this.environment = environment;
    }

    @Override
    public void shootEvent(Ball shot) {
        shot.addToGame(this.game);
        shot.setGameEnvironment(this.environment);
        shots.add(shot);
    }
}
