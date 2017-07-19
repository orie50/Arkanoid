package levels;

import java.util.List;

import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Sprite;
import gameobjects.Velocity;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-18 */
public interface LevelInformation {

    /** @return the number of the balls in the level. */
    int numberOfBalls();

    /** Create ArrayList of velocity and put the balls velocity in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    List<Velocity> initialBallVelocities();

    /** @return the speed of the paddle. */
    int paddleSpeed();

    /** @return the width of the paddle. */
    int paddleWidth();

    /** @return string of the level name. */
    String levelName();

    /** @return the background of the level. */
    Sprite getBackground();

    /** Create ArrayList of blocks and put all the blocks in it.
     * <p>
     * @return a list of blocks. */
    List<Block> blocks();

    /** Create ArrayList of balls and put all the balls in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    List<Ball> balls();

    /** @return the starting number of the blocks in the level. */
    int numberOfBlocksToRemove();
}