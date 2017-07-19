package gameobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import animations.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import listeners.BallRemover;
import listeners.Counter;
import listeners.EnemyRemover;
import listeners.HitListener;
import listeners.ShootListener;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-22 */
public class Swarm implements Sprite, ShootNotifier {
    private Velocity initialvelocity;
    private Velocity velocity;
    private Counter enemyNum;
    private ArrayList<ArrayList<Enemy>> enemySwarm;
    private List<ShootListener> shootListeners;
    private Boolean goDown;
    private int lowBorder;
    private Counter spaceShipCounter;
    private long lastShootTime;

    /**
     * constructor - create the swarm.
     * <p>
     * @param spaceShipCounter - the number of lives.
     * @param velocity - the velocity of the swarm.
     * @param enemyNum - the numbers of enemies in the swarm.
     * */
    public Swarm(Counter spaceShipCounter, Velocity velocity, Counter enemyNum) {
        this.enemyNum = enemyNum;
        this.spaceShipCounter = spaceShipCounter;
        this.velocity = velocity;
        this.initialvelocity = new Velocity(velocity.getDx(), velocity.getDy());
        this.enemySwarm = new ArrayList<ArrayList<Enemy>>();
        this.goDown = true;
        this.lowBorder = 500;
        this.lastShootTime = 0;
        this.shootListeners = new ArrayList<ShootListener>();
    }

    /**
     * Create a new swarm.
     * <p>
     * @param scoreListener - a score tracking listener of the game.
     * @param game - the game level.
     * */
    public void createEnemySwarm(HitListener scoreListener, GameLevel game) {
        HitListener ballRemover = new BallRemover(game);
        HitListener enemyRemover = new EnemyRemover(game, this.enemyNum, this);
        // Load the enemies images.
        InputStream is1 = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy - 1.png");
        InputStream is2 = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy - 2.png");
        InputStream is3 = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy - 3.png");
        InputStream is4 = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy - 4.png");
        InputStream is5 = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy - 5.png");
        InputStream is6 = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy - 6.png");
        try {
            // Add the images to a list of images
            ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
            images.add(ImageIO.read(is1));
            images.add(ImageIO.read(is2));
            images.add(ImageIO.read(is3));
            images.add(ImageIO.read(is4));
            images.add(ImageIO.read(is5));
            images.add(ImageIO.read(is6));
            int x = 50;
            int y = 80;
            // Create 10 column of enemies, in every column each 2 enemies with different image.
            for (int i = 0; i < 10; i++) {
                int k = -2;
                ArrayList<Enemy> enemyColumn = new ArrayList<Enemy>();
                for (int j = 0; j < 6; j++) {
                    if (j % 2 == 0) {
                        k += 2;
                    }
                    Enemy enemy = new Enemy((new Rectangle(new Point(x, y), 40, 30)), images.get(k),
                            images.get(k + 1), this.velocity, this.enemyNum.getValue());
                    y += 40;
                    // Increse the number of enemies and add it to the listeners and the game.
                    this.enemyNum.increase(1);
                    enemy.addHitListener(ballRemover);
                    enemy.addHitListener(enemyRemover);
                    enemy.addHitListener(scoreListener);
                    enemy.addToGame(game);
                    enemyColumn.add(enemy);
                }
                this.enemySwarm.add(enemyColumn);
                x += 50;
                y = 80;
            }
        } catch (IOException e) {
            // If failed exit.
            System.out.println("Failed to load enemy pic");
            System.exit(1);
        } finally {
            // Close the images.
            try {
                is1.close();
                is2.close();
                is3.close();
                is4.close();
                is5.close();
                is6.close();
            } catch (IOException e) {
                // If failed exit.
                System.out.println("can't close Enemy img input stream");
                e.printStackTrace();
            }

        }
    }

    /**
     * Draw all the enemies in the swarm.
     * <p>
     * @param surface - the given surface. */
    @Override
    public void drawOn(DrawSurface surface) {
        for (ArrayList<Enemy> enemyColumn : this.enemySwarm) {
            for (Enemy enemy : enemyColumn) {
                enemy.drawOn(surface);
            }
        }
    }

    @Override
    /**
     * Check if the swarm in the borders.
     * Notify all the enemies that time passed.
     * Random enemy in the last row shot.
     * <p>
     * @param dt - the speed per frame. */
    public void timePassed(double dt) {
        checkBorders(dt);
        for (ArrayList<Enemy> enemyColumn : this.enemySwarm) {
            for (Enemy enemy : enemyColumn) {
                enemy.timePassed(dt);
            }
        }
        // If past 0.5 second random enemy from last row shoot.
        if ((System.currentTimeMillis() - this.lastShootTime) / 1000 >= 0.5) {
            Random rand = new Random();
            ArrayList<Enemy> enemyColumn = this.enemySwarm.get(rand.nextInt(this.enemySwarm.size()));
            notifyShoot(enemyColumn.get(enemyColumn.size() - 1).shoot());
            this.lastShootTime = System.currentTimeMillis();
        }
    }

    /**
     * Check if the swarm in the border, if not change the direction.
     * <p>
     * @param dt - the speed per frame. */
    private void checkBorders(double dt) {
        double left = this.enemySwarm.get(0).get(0).getLocation().getX();
        double right = this.enemySwarm.get(this.enemySwarm.size() - 1).get(0).getLocation().getX() + 40;
        if (left < 0 || right > 800) {
            if (goDown) {
                // If the swarm go down.
                this.velocity.setDy(1000);
                this.velocity.setDx(-1.1 * this.velocity.getDx());
                goDown = false;
                for (ArrayList<Enemy> enemyColumn : this.enemySwarm) {
                    Enemy enemy = enemyColumn.get(enemyColumn.size() - 1);
                    // If the swarm arrived to the low border.
                    if (this.lowBorder <= this.velocity.applyToPoint(enemy.getLocation(), dt).getY() + 30) {
                        this.spaceShipCounter.decrease(1);
                    }
                }
            }
        } else {
            // If the swarm doesn't go down.
            this.velocity.setDy(0);
            goDown = true;
        }
    }

    /**
     * Reset the swarm.
     * Initialize the enemies places. */
    public void resetSwarm() {
        int x = 50;
        int y = 80;
        for (int i = 0; i < this.enemySwarm.size(); i++) {
            for (int j = 0; j < this.enemySwarm.get(i).size(); j++) {
                Enemy enemy = this.enemySwarm.get(i).get(j);
                enemy.setNewPlace(x, y);
            }
        }
        // Give the swarm his first velocity.
        this.velocity.setDx(initialvelocity.getDx());
        this.velocity.setDy(initialvelocity.getDy());
    }

    @Override
    /** add the swarm to the game.
     * <p>
     * @param game - the game to be add to. */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    @Override
    /** remove the swarm from the game.
     * <p>
     * @param game - the game to be remove from. */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

    /** remove the enemy from the swarm.
     * <p>
     * @param enemy - the enemy to remove. */
    public void removeEnemy(Enemy enemy) {
        for (int i = 0; i < this.enemySwarm.size(); i++) {
            ArrayList<Enemy> enemyColumn = this.enemySwarm.get(i);
            enemyColumn.remove(enemy);
            // if the column is empty remove the column.
            if (enemyColumn.isEmpty()) {
                this.enemySwarm.remove(enemyColumn);
            }
        }
    }

    @Override
    /**
     * Add shoot listener to the list of shot listeners.
     * <p>
     * @param sl - the shot listener. */
    public void addShootListener(ShootListener sl) {
        this.shootListeners.add(sl);
    }

    @Override
    /**
     * remove the shoot listener from the list of shot listeners.
     * <p>
     * @param sl - the shot listener. */
    public void removeShootListener(ShootListener sl) {
        this.shootListeners.add(sl);
    }

    /** Notify all the listeners that about the shot.
     * <p>
     * @param shot - the ball that hit the spaceship. */
    private void notifyShoot(Ball shot) {
        // Copy the hitListeners before iterating over them.
        List<ShootListener> listeners = new ArrayList<ShootListener>(this.shootListeners);
        // Notify all listeners about a hit event.
        for (ShootListener hl : listeners) {
            hl.shootEvent(shot);
        }
    }
}