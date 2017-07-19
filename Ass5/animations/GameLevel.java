package animations;

import java.awt.Color;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Collidable;
import gameobjects.LevelName;
import gameobjects.LivesIndicator;
import gameobjects.Paddle;
import gameobjects.ScoreIndicator;
import gameobjects.Sprite;
import gameobjects.SpriteCollection;
import general.GameEnvironment;
import levels.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.Counter;
import listeners.HitListener;
import listeners.ScoreTrackingListener;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.1
 * @since 2016-04-03 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.KeyboardSensor keyboard;
    private AnimationRunner runner;
    private Counter ballsCounter;
    private Counter blocksCounter;
    private Counter scoreCounter;
    private Counter numberOfLives;
    private boolean running;
    private LevelInformation level;

    /**
     * Constructor - Create a list of sprites a new environment and a gui for the game.
     * @param animationRunner - the AnimationRunner of the game.
     * @param keyboard - the KeyboardSensor of the game.
     * @param gui - the gui of the game.
     * @param numberOfLives - Counter with the lives that left for the game.
     * @param scoreCounter - Counter with the score of the player.
     * @param level - the level that played. */
    public GameLevel(LevelInformation level, KeyboardSensor keyboard, AnimationRunner animationRunner,
            GUI gui, Counter scoreCounter, Counter numberOfLives) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(new Point(800, 600) , new Point(0, 0));
        this.ballsCounter = new Counter();
        this.blocksCounter = new Counter();
        this.scoreCounter = scoreCounter;
        this.numberOfLives = numberOfLives;
        this.runner = animationRunner;
        this.keyboard = keyboard;
        this.level = level;
    }

    /**
     * Add the collidable object to the collidable list.
     * <p>
     * @param c - the collidable object to add. */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * remove the collidable object from the sprites list.
     * <p>
     * @param c - the collidable object to remove. */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Add the sprite object to the sprites list.
     * <p>
     * @param s - the sprite object to add. */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * remove the sprite object from the sprites list.
     * <p>
     * @param s - the sprite object to remove. */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize the level.
     * Create the sprites for the current level. */
    public void initialize() {
        // Create the HitListener for blocks and score.
        HitListener blockRemover = new BlockRemover(this, this.blocksCounter);
        HitListener scoreListener = new ScoreTrackingListener(this.scoreCounter);
        // Create the borders.
        this.createBorder();
        // Create the sprites that show the score lives and the level name.
        ScoreIndicator score = new ScoreIndicator(this.scoreCounter);
        score.addToGame(this);
        LivesIndicator lives = new LivesIndicator(this.numberOfLives);
        lives.addToGame(this);
        LevelName name = new LevelName(this.level.levelName());
        name.addToGame(this);
        // Add the background of the level.
        level.getBackground().addToGame(this);
        // Get list of blocks from the level information and add them to the game.
        List<Block> blockList = new ArrayList<Block>(this.level.blocks());
        for (Block block : blockList) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreListener);
        }
        // Add the number of blocks to the block counter.
        this.blocksCounter.increase(level.numberOfBlocksToRemove());
    }

    /**
     * Run one turn (one live) of the game.
     * Create the balls and the paddle and run the level. */
    public void playOneTurn() {
        // Get list of balls from the level information and add them to the game.
        List<Ball> ballList = new ArrayList<Ball>(this.level.balls());
        for (Ball ball : ballList) {
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        }
        // Add the number of balls to the balls counter.
        this.ballsCounter.increase(level.numberOfBalls());
        // Create the paddle and add it to the game.
        Point paddlePoint = new Point(400 - (level.paddleWidth() / 2), 580);
        Paddle paddle = new Paddle(new Rectangle(paddlePoint, level.paddleWidth(), 20),
                Color.YELLOW, level.paddleSpeed(), this.keyboard, 20, 780);
        paddle.addToGame(this);
        this.running = true;
        // Countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        // Use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
        // remove the paddle to create new paddle in the middle.
        paddle.removeFromGame(this);
    }

    /**
     * Create the border.
     * Create 4 blocks for the border and add them to the game. */
    public void createBorder() {
        Block upFrame = new Block(new Point(0, 20), 800, 20, -1, Color.GRAY);
        Block lowFrame = new Block(new Point(0, 600), 800, 20, -1, Color.GRAY);
        lowFrame.addHitListener(new BallRemover(this, this.ballsCounter));
        Block lFrame = new Block(new Point(0, 40), 20, 580, -1, Color.GRAY);
        Block rFrame = new Block(new Point(780, 40), 20, 580, -1, Color.GRAY);
        lFrame.addToGame(this);
        rFrame.addToGame(this);
        upFrame.addToGame(this);
        lowFrame.addToGame(this);
    }

    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Do one step of the game ( draw all the sprites and notify that time past).
     * <p>
     * @param d - the given surface.
     */
    public void doOneFrame(DrawSurface d) {
        // Pause the game.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }
        // Draw all the sprites.
        this.environment.setSurface(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        // If the balls over lose one life
        if (this.ballsCounter.getValue() == 0) {
            this.numberOfLives.decrease(1);
            this.running = false;
        } else if (this.blocksCounter.getValue() == 0) {
            // Else if win the level increase 100 points.
            this.scoreCounter.increase(100);
            this.sprites.drawAllOn(d);
            this.running = false;
        }
    }

    /**
     * Check if left blocks in the level.
     * <p>
     * @return true if left blocks else return false. */
    public boolean haveBlocks() {
        if (blocksCounter.getValue() > 0) {
            return true;
        }
        return false;
    }
}