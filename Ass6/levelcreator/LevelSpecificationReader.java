package levelcreator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import backgrounds.ColorBackground;
import backgrounds.ImgeBackground;
import gameobjects.Block;
import gameobjects.Sprite;
import gameobjects.Velocity;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-5 */
public class LevelSpecificationReader {

    private int startX;
    private int xPosition;
    private int yPosition;
    private int rowHeight;
    private boolean readBlockLines;

    /**
     * Read file with level definition and create the levels.
     * <p>
     * @param reader - the file to read.
     * @return list of levels.
     * @throws FileNotFoundException - if can't find/read the file.
     * */
    public List<LevelInformation> fromReader(java.io.Reader reader) throws FileNotFoundException {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        Level level = null;
        BlocksFromSymbolsFactory factory = null;
        // The file to read.
        BufferedReader bufferReader = new BufferedReader(reader);
        try {
            String line = null;
            // run in a loop and any iteration read line.
            while ((line = bufferReader.readLine()) != null) {
                // If line is empty skip the line.
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                } else if ("START_LEVEL".equals(line)) {
                    // Start new level.
                    level = new Level();
                } else if ("END_LEVEL".equals(line)) {
                    // End the level, add the level to the list and reset the variables.
                    levels.add(level);
                    xPosition = 0;
                    yPosition = 0;
                    rowHeight = 0;
                    factory = null;
                } else {
                    // Split the line to key and value.
                    String[] parts = line.split(":");
                    String key = parts[0];
                    if (key.equals("START_BLOCKS")) {
                        // Start read the blocks line.
                        readBlockLines = true;
                    } else if (key.equals("END_BLOCKS")) {
                        // finish read the blocks line.
                        readBlockLines = false;
                    } else if (readBlockLines) {
                        // read all blocks and spaces lines.
                        for (int i = 0; i < line.length(); ++i) {
                            // read any char in the current line.
                            String symbol = Character.toString(line.charAt(i));
                            // if the space symbol exist.
                            if (factory.isSpaceSymbol(symbol)) {
                                xPosition += factory.getSpaceWidth(symbol);
                            } else if (factory.isBlockSymbol(symbol)) {
                                // if the block symbol exist, create the block and add it to the list..
                                Block block = factory.getBlock(symbol, xPosition, yPosition);
                                level.blockList.add(block);
                                xPosition += factory.getBlockWidth(symbol);
                            }
                        }
                        // go down a row and reset the x to the start of the row.
                        yPosition += rowHeight;
                        xPosition = startX;
                    } else {
                        String value = parts[1];
                        if (key.equals("level_name")) {
                            // Initialize the level name.
                            level.levelName = (value);
                        } else if (key.equals("ball_velocities")) {
                            // Create the list of velocities.
                            String[] velocities = value.split(" ");
                            for (int i = 0; i < velocities.length; i++) {
                                String[] velocity = velocities[i].split(",");
                                level.velocities.add(Velocity.fromAngleAndSpeed(Double.parseDouble(velocity[0]),
                                        (Double.parseDouble(velocity[1]))));
                            }
                            // Initialize the number of balls.
                            level.numberOfBalls = level.velocities.size();
                        } else if (key.equals("background")) {
                            ColorsParser colorParser = new ColorsParser();
                            if (value.startsWith("color")) {
                                // If the background is a color create the color from string.
                                Color color = colorParser.colorFromString(value);
                                level.background = new ColorBackground(color);
                            } else if (value.startsWith("image")) {
                                // If the background is image get the image file and put in the background.
                                String imageLine = value.substring(6, value.length() - 1);
                                try {
                                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageLine);
                                    BufferedImage image = ImageIO.read(is);
                                    level.background = new ImgeBackground(image);
                                } catch (IOException e) {
                                    System.out.println("Failed read the image");
                                    System.exit(0);
                                }
                            }
                        } else if (key.equals("paddle_speed")) {
                            // Initialize the paddle speed.
                            level.paddleSpeed = Integer.parseInt(value);
                        } else if (key.equals("paddle_width")) {
                            // Initialize the paddle width.
                            level.paddleWidth = Integer.parseInt(value);
                        } else if (key.equals("block_definitions")) {
                            // Create blocks factory (send the block definition file).
                            try {
                                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(value);
                                reader = new BufferedReader(
                                        new InputStreamReader(is));
                                factory = BlockDefinitionsReader.fromReader(reader);
                            } catch (IOException e) {
                                System.err.println("Failed reading the file ");
                            }
                        } else if (key.equals("blocks_start_x")) {
                            // Initialize the x start.
                            this.xPosition = Integer.parseInt(value);
                            startX = xPosition;
                        } else if (key.equals("blocks_start_y")) {
                            // Initialize the y start.
                            this.yPosition = Integer.parseInt(value);
                        } else if (key.equals("row_height")) {
                            // Initialize the row height.
                            this.rowHeight = Integer.parseInt(value);
                        } else if (key.equals("num_blocks")) {
                            // Initialize the number of blocks to remove.
                            level.numberOfBlocksToRemove = Integer.parseInt(value);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find level specs file ");
            throw e;
        } catch (IOException e) {
            System.err.println("Failed reading level specs file " + ", message:" + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
        return levels;
    }

    /**
     * Create a single level. */
    private class Level implements LevelInformation {

        private List<Velocity> velocities = new ArrayList<Velocity>();
        private List<Block> blockList = new ArrayList<Block>();
        private int numberOfBlocksToRemove;
        private int paddleSpeed;
        private int paddleWidth;
        private String levelName;
        private int numberOfBalls;
        private Sprite background;

        @Override
        /** @return the number of the balls in the level. */
        public int numberOfBalls() {
            return this.numberOfBalls;
        }

        @Override
        /** Create ArrayList of velocity and put the balls velocity in it.
         * <p>
         * @return a list of velocity for all the balls in the level. */
        public List<Velocity> initialBallVelocities() {
            return this.velocities;
        }

        @Override
        /** @return the speed of the paddle. */
        public int paddleSpeed() {
            return this.paddleSpeed;
        }

        @Override
        /** @return the width of the paddle. */
        public int paddleWidth() {
            return this.paddleWidth;
        }

        @Override
        /** @return string of the level name. */
        public String levelName() {
            return this.levelName;
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
            return blockList;
        }

        @Override
        /** @return the starting number of the blocks in the level. */
        public int numberOfBlocksToRemove() {
            return this.numberOfBlocksToRemove;
        }
    }
}