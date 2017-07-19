package levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import backgrounds.BackgroundLevelThree;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Sprite;
import gameobjects.Velocity;
import geometry.Point;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-19 */
public class LevelThree implements LevelInformation {

    private BackgroundLevelThree background;

    /** Constructor - create the background for the level. */
    public LevelThree() {
        this.background = new BackgroundLevelThree();
    }

    @Override
    /** @return the number of the balls in the level. */
    public int numberOfBalls() {
        return 2;
    }

    @Override
    /** Create ArrayList of velocity and put the balls velocity in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocity = new ArrayList<Velocity>();
        // Make a velocity as the number of the balls and put on the list.
        velocity.add(Velocity.fromAngleAndSpeed(30, 7));
        velocity.add(Velocity.fromAngleAndSpeed(330, 7));
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
        return "Green 3";
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
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<Block>();
        int y = 200;
        // Array of colors for the block.
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.WHITE};
        // Create the blocks and put them in a list first row with 2 hit.
        for (int i = 0; i < 5; i++) {
            int x = 730;
            if (i == 0) {
                for (int j = i; j < 10; j++) {
                    blockList.add(new Block(new Point(x, y), 50, 20, 2, colors[i]));
                    x -= 50;
                }
            } else {
                for (int j = i; j < 10; j++) {
                    blockList.add(new Block(new Point(x, y), 50, 20, 1, colors[i]));
                    x -= 50;
                }
            }
            y += 20;
        }
        return blockList;
    }

    @Override
    /** Create ArrayList of balls and put all the balls in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    public List<Ball> balls() {
        List<Ball> ballList = new ArrayList<Ball>();
        // Create the balls and give them velocity from the velocity list.
        for (int i = 0; i < this.numberOfBalls(); i++) {
            Ball ball = new Ball(400, 575, 5, Color.WHITE);
            ball.setVelocity(this.initialBallVelocities().get(i));
            ballList.add(ball);
        }
        return ballList;
    }

    @Override
    /** @return the starting number of the blocks in the level. */
    public int numberOfBlocksToRemove() {
        return 40;
    }

}
