package levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import backgrounds.BackgroundLevelTwo;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Sprite;
import gameobjects.Velocity;
import geometry.Point;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-19 */
public class LevelTwo implements LevelInformation {

    private BackgroundLevelTwo background;

    /** Constructor - create the background for the level. */
    public LevelTwo() {
        this.background = new BackgroundLevelTwo();
    }

    @Override
    /** @return the number of the balls in the level. */
    public int numberOfBalls() {
        return 10;
    }

    @Override
    /** Create ArrayList of velocity and put the balls velocity in it.
     * <p>
     * @return a list of velocity for all the balls in the level. */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocity = new ArrayList<Velocity>();
        int j = 300;
        // Make a velocity as the number of the balls and put on the list.
        for (int i = 0; i < this.numberOfBalls(); i++) {
            velocity.add(Velocity.fromAngleAndSpeed(j % 360, 7));
            j += 12;
            if (j == 360) {
                j += 12;
            }
        }
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
        return 600;
    }

    @Override
    /** @return string of the level name. */
    public String levelName() {
        return "Wide Easy";
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
        // Array of colors for the block.
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.CYAN};
        double j = 20;
        int k = 0;
        // Create the blocks and put them in a list.
        for (int i = 0; i < 15; i++) {
            blockList.add(new Block(new Point(j, 300), 760 / 15 + 0.7, 20, 1, colors[k]));
            // Create two blocks from every color and three green blocks in the middle.
            if ((i < 6) && (i % 2 == 1)) {
                k++;
            } else if ((i > 7) && (i % 2 == 0)) {
                k++;
            }
            j += 760 / 15 + 0.7;
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
        return 15;
    }

}
