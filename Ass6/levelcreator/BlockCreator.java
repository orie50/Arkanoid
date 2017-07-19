package levelcreator;

import gameobjects.Block;
/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-8 */
public interface BlockCreator {
    /**
     * Create a new block.
     * <p>
     * @param xpos - the x value of the upper left point of the block.
     * @param ypos - the y value of the upper left point of the block.
     * @return new block.
     * */
    Block create(int xpos, int ypos);
}
