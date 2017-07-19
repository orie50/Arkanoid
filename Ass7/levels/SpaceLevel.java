package levels;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import backgrounds.ColorBackground;
import gameobjects.Block;
import gameobjects.Sprite;
import gameobjects.Velocity;
import geometry.Point;
import geometry.Rectangle;


/**
 * The Class SpaceLevel.
 *
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17
 */
public class SpaceLevel implements LevelInformation {

    private Double turn;

    /**
     * Instantiates a new space level.
     *
     * @param turn - num of turns counter
     */
    public SpaceLevel(Double turn) {
        this.turn = turn;
    }

    @Override
    public int numberOfBalls() {
        return 0;
    }


    @Override
    public List<Velocity> initialBallVelocities() {
        ArrayList<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(Velocity.fromAngleAndSpeed(90, (40 * turn)));
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 650;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Battle no. " + this.turn.intValue();
    }

    @Override
    public Sprite getBackground() {
        return new ColorBackground(Color.BLACK);
    }

    @Override
    /**
     * creates the shield.
     * <p>
     * @return blocks - the shield
     */
    public List<Block> blocks() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        TreeMap<Integer, Color> fillColor = new TreeMap<Integer, Color>();
        TreeMap<Integer, BufferedImage> fillImage = new TreeMap<Integer, BufferedImage>();
        fillColor.put(1, Color.CYAN);
        int x = 100;
        int y = 500;
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < 50; j++) {
                    blocks.add(new Block(new Rectangle(new Point(x, y), 3, 3), 1, null, fillColor, fillImage));
                    x += 3;
                }
                y += 3;
                x -= 150;
            }
            x += 250;
            y = 500;
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 0;
    }
}
