package listeners;

import java.util.List;

import animations.GameLevel;
import gameobjects.Ball;
import general.GameEnvironment;

public class SpaceShipShotListener implements ShootListener {
    List<Ball> spaceShipShots;
    private GameLevel game;
    private GameEnvironment environment;
    private long lastShootTime;

    public SpaceShipShotListener(List<Ball> spaceShipShots, GameLevel game, GameEnvironment environment) {
        this.spaceShipShots = spaceShipShots;
        this.game = game;
        this.environment = environment;
        this.lastShootTime = 0;
    }

    @Override
    public void shootEvent(Ball shot) {
        if ((System.currentTimeMillis() - this.lastShootTime) / 400 >= 0.35) {
            shot.addToGame(this.game);
            shot.setGameEnvironment(this.environment);
            spaceShipShots.add(shot);       
            this.lastShootTime = System.currentTimeMillis();
        }
    }
}
