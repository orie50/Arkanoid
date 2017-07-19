import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-04-05 */
public class BlockFactory {
    private Point lowerFrameEdge;
    private Point upperFrameEdge;
    /**
     * BlockFactory constructor.
     * <p>
     * @param lowerFrameEdge - the game frame lower boundary coordinate
     * @param upperFrameEdge - the game frame upper boundary coordinate*/
    public BlockFactory(Point lowerFrameEdge, Point upperFrameEdge) {
        this.lowerFrameEdge = lowerFrameEdge;
        this.upperFrameEdge = upperFrameEdge;
    }
    /**
     * standard Block creator.
     * <p>
     * @param upperLeft - the upper left coordinate.
     * @param maxHits - maximum hits available for the block
     * @param color - block fill color
     * @return Block - standard block*/
    public Block create(Point upperLeft, int maxHits, Color color) {
        return new Block(upperLeft, 50, 20, maxHits, color);
    }
    /**
     * Block raw creator.
     * <p>
     * creates a specific number of standard blocks in a raw
     * from a given point to the right
     * <p>
     * @param start - the upper left start coordinate.
     * @param numOfBlocks - the number of blocks in the raw
     * @param maxHits - maximum hits available for the block
     * @param color - block fill color
     * @return BlockList*/
    public List createBlockRaw(Point start, int numOfBlocks, int maxHits, Color color) {
        List blockList = new ArrayList();
        Velocity velocity = new Velocity(50, 0);
        for (int i = 0; i < numOfBlocks; i++) {
            blockList.add(this.create(start, maxHits, color));
            start = velocity.applyToPoint(start);
        }
        return blockList;
    }
    /**
     * Block raw creator.
     * <p>
     * creates a standard blocks raw, from a given
     * to the right boundary of the frame
     * <p>
     * @param start - the upper left start coordinate.
     * @param maxHits - maximum hits available for the block
     * @param color - block fill color
     * @return BlockList*/
    public List createBlockRaw(Point start, int maxHits, Color color) {
        List blockList = new ArrayList();
        int width = (int) (this.lowerFrameEdge.getX() - this.upperFrameEdge.getX()) - 20;
        int numOfBlocks =  (int) ((width  - start.getX()) / 50);
        blockList = createBlockRaw(start, numOfBlocks, maxHits, color);
        return blockList;
    }
}
