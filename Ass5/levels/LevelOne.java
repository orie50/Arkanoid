package levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import backgrounds.BackgroundLevelOne;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Sprite;
import gameobjects.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-19 */
public class LevelOne implements LevelInformation {

    private BackgroundLevelOne background;

    /** Constructor - create the background for the level. */
    public LevelOne() {
        this.background = new BackgroundLevelOne();
    }

    @Override
    /** return the number of the balls in the level. */
    public int numberOfBalls() {
        return 1;
    }

    @Override
    /** Create ArrayList of velocity and put the balls velocity in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocity = new ArrayList<Velocity>();
        // Make a velocity as the number of the balls and put on the list.
        velocity.add(Velocity.fromAngleAndSpeed(0, 7));
        return velocity;
    }

    @Override
    /** @return the speed of the paddle. */
    public int paddleSpeed() {
        return 10;
    }

    @Override
    /** @return the width of the paddle. */
    public int paddleWidth() {
        return 80;
    }

    @Override
    /** @return string of the level name. */
    public String levelName() {
        return "Direct Hit";
    }


    @Override
    /** @return the background of the level. */
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    /** Create ArrayList of blocks and put all the blocks in it.
     * <p>
     * @return a list of blocks. */
    public ArrayList<Block> blocks() {
        ArrayList<Block> blockList = new ArrayList<Block>();
        // Create one block.
        Block block = new Block(new Rectangle(new Point(390, 200), 20, 20), 1, Color.RED);
        blockList.add(block);
        return blockList;
    }

    @Override
    /** Create ArrayList of balls and put all the balls in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    public List<Ball> balls() {
        List<Ball> ballList = new ArrayList<Ball>();
        // Create the balls and give them velocity from the velocity list.
        Ball ball = new Ball(400, 575, 5, Color.WHITE);
        ball.setVelocity(this.initialBallVelocities().get(0));
        ballList.add(ball);
        return ballList;
    }

    @Override
    /** @return the starting number of the blocks in the level. */
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
