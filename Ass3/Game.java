import java.awt.Color;
import java.util.List;
import java.util.Random;

import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-04-03 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.GUI gui;

    /**
    * Contractor - Create a list of sprites a new environment and a gui for the game. */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(new Point(600, 600) , new Point(0, 0));
        this.gui = new biuoop.GUI("title", 800, 600);
    }

    /**
     * Add the collidable object to the collidable list.
     * <p>
     * @param c - the collidable object to add. */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add the sprite object to the sprites list.
     * <p>
     * @param s - the collidable object to add. */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize the game.
     * Create the variables for the games. */
    public void initialize() {
        Random rand = new Random();
        // Create 2 balls and add them to the game.
        Ball ball1 = new Ball(new Point(400, 400), 5 , Color.RED, this.environment);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(360), 7));
        ball1.addToGame(this);
        Ball ball2 = new Ball(new Point(200, 200), 5 , Color.BLUE, this.environment);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(360), 7));
        ball2.addToGame(this);
        // Create the keyboard sensor for the paddle.
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        // Create the paddle and add it to the game.
        Paddle paddle = new Paddle(new Rectangle(new Point(200, 560), 80, 20), Color.BLACK, 5, keyboard, 20, 780);
        paddle.addToGame(this);
        // Create the frame & blocks.
        this.createFrame();
        // The colors for the blocks.
        Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.PINK, Color.GREEN, Color.MAGENTA};
        Point start = new Point(230, 150);
        // Create all the blocks and add them to the game.
        BlockFactory blockFactory = new BlockFactory(new Point(800, 600) , new Point(0, 0));
        Velocity velocity = new Velocity(50, 20);
        for (int i = 0; i < 6; i++) {
            List blockList = null;
            // Create the first line of the blocks.
            if (i == 0) {
                blockList = blockFactory.createBlockRaw(start, 2, colors[i]);
            } else {
                // Create the other block lines.
                blockList = blockFactory.createBlockRaw(start, 1, colors[i]);
                }
            // Add all the blocks to the games.
            for (int j = 0; j < blockList.size(); j++) {
                ((Block) blockList.get(j)).addToGame(this);
                }
            start = velocity.applyToPoint(start);
            }
        }

    /**
     * Run the game.
     * Create the variables for the games. */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        // Draw all the objects and move the ball in the frame.
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.environment.setSurface(d);
            this.sprites.drawAllOn(d);
            this.sprites.notifyAllTimePassed();
            gui.show(d);
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Create the frame.
     * Create 4 blocks for the frame and add them to the game. */
    public void createFrame() {
        Block upFrame = new Block(new Point(0, 0), 800, 20, -1, Color.GRAY);
        Block lowFrame = new Block(new Point(0, 580), 800, 20, -1, Color.GRAY);
        Block lFrame = new Block(new Point(0, 20), 20, 580, -1, Color.GRAY);
        Block rFrame = new Block(new Point(780, 20), 20, 580, -1, Color.GRAY);
        lFrame.addToGame(this);
        rFrame.addToGame(this);
        upFrame.addToGame(this);
        lowFrame.addToGame(this);
        }
    }
