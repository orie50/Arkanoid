package animations;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Collidable;
import gameobjects.Enemy;
import gameobjects.LevelName;
import gameobjects.LivesIndicator;
import gameobjects.ScoreIndicator;
import gameobjects.Spaceship;
import gameobjects.Sprite;
import gameobjects.SpriteCollection;
import gameobjects.Swarm;
import gameobjects.Velocity;
import general.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.BonusRemover;
import listeners.Counter;
import listeners.EnemyShotListener;
import listeners.HitListener;
import listeners.ScoreTrackingListener;
import listeners.ShootListener;
import listeners.SpaceshipRemover;
import listeners.SpaceShipShotListener;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.2
 * @since 2016-04-03 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.KeyboardSensor keyboard;
    private AnimationRunner runner;
    private Counter scoreCounter;
    private Counter numberOfLives;
    private Counter numberOfSpaceships;
    private Counter enemyCounter;
    private boolean running;
    private LevelInformation level;
    private ArrayList<Ball> balls;
    private Swarm swarm;
    private Enemy bonusEnemy;
    private long lastBonusTime;

    /**
     * Constructor - Create a list of sprites a new environment and a gui for the game.
     * @param animationRunner - the AnimationRunner of the game.
     * @param keyboard - the KeyboardSensor of the game.
     * @param gui - the gui of the game.
     * @param numberOfLives - Counter with the lives that left for the game.
     * @param scoreCounter - Counter with the score of the player.
     * @param enemyNum - Counter with num of enemys.
     * @param level - the level that played.*/
    public GameLevel(LevelInformation level, KeyboardSensor keyboard, AnimationRunner animationRunner,
            GUI gui, Counter scoreCounter, Counter numberOfLives, Counter enemyNum) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(new Point(800, 600) , new Point(0, 0));
        this.scoreCounter = scoreCounter;
        this.numberOfLives = numberOfLives;
        this.numberOfSpaceships = new Counter(numberOfLives.getValue());
        this.enemyCounter = enemyNum;
        this.runner = animationRunner;
        this.keyboard = keyboard;
        this.level = level;
        this.balls = new ArrayList<Ball>();
        this.lastBonusTime = 0;
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
        // Create the HitListener for blocks, shots and score.
        HitListener blockRemover = new BlockRemover(this);
        HitListener scoreListener = new ScoreTrackingListener(this.scoreCounter);
        HitListener ballRemover = new BallRemover(this);
        // Add the background of the level.
        level.getBackground().addToGame(this);
        // Create the borders.
        this.createBorder(ballRemover);
        // Create the sprites that show the score lives and the level name.
        ScoreIndicator score = new ScoreIndicator(this.scoreCounter);
        score.addToGame(this);
        LivesIndicator lives = new LivesIndicator(this.numberOfLives);
        lives.addToGame(this);
        LevelName name = new LevelName(this.level.levelName());
        name.addToGame(this);
        List<Block> blockList = this.level.blocks();
        for (Block block : blockList) {
            block.addHitListener(blockRemover);
            block.addHitListener(ballRemover);
            block.addToGame(this);
        }
        // add the enemy to the game with a enemyShot listener
        ShootListener sl = new EnemyShotListener(this.balls, this, environment);
        this.swarm = new Swarm(this.numberOfSpaceships, this.level.initialBallVelocities().get(0), this.enemyCounter);
        this.swarm.addShootListener(sl);
        this.swarm.createEnemySwarm(scoreListener, this);
        this.swarm.addToGame(this);
    }


    /**
     * Run one turn (one live) of the game.
     * Create the balls and the paddle and run the level. */
    public void playOneTurn() {
        // Create the spaceship and add it to the game.
        InputStream is1 = ClassLoader.getSystemClassLoader().getResourceAsStream("spaceship.png");
        BufferedImage spaceshipImage = null;
        try {
            spaceshipImage = ImageIO.read(is1);
        } catch (IOException e) {
            System.out.println("can't load spaceship pic");
        } finally {
            try {
                is1.close();
            } catch (IOException e) {
                System.out.println("can't close spaceship img input stream");
                e.printStackTrace();
                System.exit(1);
            }
        }
        Point spaceshipPoint = new Point(400 - (level.paddleWidth() / 2), 570);
        Spaceship spaceship = new Spaceship(new Rectangle(spaceshipPoint, level.paddleWidth(), 20),
                 level.paddleSpeed(), this.keyboard, 0, 800, spaceshipImage);
        HitListener ballRemover = new BallRemover(this);
        HitListener spaceshipRemover = new SpaceshipRemover(this, this.numberOfSpaceships);
        ShootListener shotListener = new SpaceShipShotListener(this.balls, this, environment);
        spaceship.addHitListener(ballRemover);
        spaceship.addHitListener(spaceshipRemover);
        spaceship.addShootListener(shotListener);
        spaceship.addToGame(this);
        this.running = true;
        // Countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        // Use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
        // remove the spaceship to create new spaceship in the middle.
        spaceship.removeFromGame(this);
        // reset the swarm and the balls pool to reorganize the frame
        this.swarm.resetSwarm();
        for (Ball ball : this.balls) {
            ball.removeFromGame(this);
        }
        balls.clear();
    }

    /**
     * Creates the bonus ship.
     */
    private void createBonusShip() {
        // if the bonus already exists (the player didn't shoot it) ,reset its place
        if (this.bonusEnemy != null && this.bonusEnemy.getLocation().getX() < 0) {
            this.bonusEnemy.setNewPlace(850, 50);
        } else { // create new bonus ship
            InputStream is1 = ClassLoader.getSystemClassLoader().getResourceAsStream("bonus enemy.png");
            try {
                BufferedImage bonusEnemyImage = ImageIO.read(is1);
                this.bonusEnemy = new Enemy((new Rectangle(new Point(850, 50), 40, 22)),
                        bonusEnemyImage, bonusEnemyImage, Velocity.fromAngleAndSpeed(-90, 200), 0);
                this.bonusEnemy.addHitListener(new BonusRemover(this, this.scoreCounter));
                this.bonusEnemy.addHitListener(new BallRemover(this));
                this.bonusEnemy.addToGame(this);
                this.addSprite(bonusEnemy);
            } catch (IOException e) {
                System.out.println("can't load bonusEnemy pic");
                return;
            } finally {
                try {
                    is1.close();
                } catch (IOException e) {
                    System.out.println("can't close bonus img input stream");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Create the border.
     * Create 4 blocks for the border and add them to the game.
     * @param ballRemover - listener for the borders, so they will remove balls */
    public void createBorder(HitListener ballRemover) {
        TreeMap<Integer, Color> fillColor = new TreeMap<Integer, Color>();
        TreeMap<Integer, BufferedImage> fillImage = new TreeMap<Integer, BufferedImage>();
        fillColor.put(1, Color.BLACK);
        Block upFrame = new Block(new Rectangle(new Point(0, 20), 800, 1), -1, Color.BLACK, fillColor, fillImage);
        Block lowFrame = new Block(new Rectangle(new Point(0, 600), 800, 1), -1, Color.BLACK, fillColor, fillImage);
        Block lFrame = new Block(new Rectangle(new Point(-25, 40), 25, 575), -1, Color.BLACK, fillColor, fillImage);
        Block rFrame = new Block(new Rectangle(new Point(800, 40), 25, 575), -1, Color.BLACK, fillColor, fillImage);
        upFrame.addHitListener(ballRemover);
        lowFrame.addHitListener(ballRemover);
        lFrame.addHitListener(ballRemover);
        rFrame.addHitListener(ballRemover);
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
     * @param dt - the speed per frame. */
    public void doOneFrame(DrawSurface d, double dt) {
        // Pause the game.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen()));
        }
        // bonus enemy condition
        if ((System.currentTimeMillis() - this.lastBonusTime) / 1000 >= 20) {
            this.createBonusShip();
            this.lastBonusTime = System.currentTimeMillis();
        }
        // Draw all the sprites.
        this.environment.setSurface(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        // If the balls over lose one life
        if (this.enemyCounter.getValue() == 0) {
            // Else if win the level increase 100 points.
            this.scoreCounter.increase(1000);
            this.running = false;
        } else if (this.numberOfLives.getValue() != this.numberOfSpaceships.getValue()) {
            this.numberOfLives.decrease(1);
            this.running = false;
        }
    }
}