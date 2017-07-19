package general;
import java.io.File;
import java.io.IOException;

import animations.AnimationRunner;
import animations.EndGameAnimation;
import animations.GameLevel;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import animations.MenuAnimation;
import backgrounds.MenuBackground;
import biuoop.KeyboardSensor;
import levels.SpaceLevel;
import listeners.Counter;
import score.HighScoresTable;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-18 */
public class GameFlow {

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboard;
    private biuoop.GUI gui;


    /**
     * Constructor - Create the GUI the AnimationRunner and KeyboardSensor of the game. */
    public GameFlow() {
        this.gui = new biuoop.GUI("Space-Invaderes", 800, 600);
        this.animationRunner = new AnimationRunner(this.gui, 60);
        this.keyboard = gui.getKeyboardSensor();
    }

    /** show the main menu.
     * <p>
     * @param levelSet - path to the level set file */
    public void showMenu(String levelSet) {
        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>(new MenuBackground(), keyboard);
        menu.addSelection("s", "Start Game", new Task<Void>() {
            public Void run() {
                runLevels();
                return null;
            }
        });
        // add the high score option
        menu.addSelection("h", "High Scores", new Task<Void>() {
            public Void run() {
                File file = new File("highscore.txt");
                HighScoresTable table = HighScoresTable.loadFromFile(file);
                try {
                    table.save(file);
                } catch (IOException e) {
                    System.err.println("Unable to find file: " + file.getName());
                }
                animationRunner.run(
                        new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                                new HighScoresAnimation(table, KeyboardSensor.SPACE_KEY, keyboard)));
                return null;
            }
        });
        // add the exit option
        menu.addSelection("e", "Exit", new Task<Void>() {
            public Void run() {
                System.exit(0);
                return null;
            }
        });
        while (true) {
            this.animationRunner.run(menu);
            //try to get status or submenu from the menu
            Task<Void> t = menu.getStatus();
            Menu<Task<Void>> subMenu = menu.getMenuStatus();
            //the menu result is a task
            if (t != null) {
                t.run();
                //the menu result is a sub-menu
            } else {
                this.animationRunner.run(subMenu);
                t = subMenu.getStatus();
                t.run();
                subMenu.resetStatus();
            }
            menu.resetStatus();
        }
    }
    /**
     * Run the game.
     * <p>
     * run the levels in a loop as long left lives*/
    public void runLevels() {
        // Create the score counter and lives counter.
        Counter enemyCounter = new Counter();
        Counter scoreCounter = new Counter();
        Counter numberOfLives = new Counter(3);
        File file = new File("./highscore.txt");
        HighScoresTable table = HighScoresTable.loadFromFile(file);
        try {
            table.save(file);
        } catch (IOException e) {
            System.err.println("Unable to find file: " + file.getName());
        }
        GameLevel level = null;
        Double turn = new Double(0);
        // while the level left blocks and still have lives run one turn.
        while (numberOfLives.getValue() > 0) {
            System.out.println(enemyCounter.getValue());
            if (enemyCounter.getValue() == 0) {
                turn++;
                // Create the level
                level = new GameLevel(new SpaceLevel(turn), this.keyboard,
                        this.animationRunner, this.gui, scoreCounter, numberOfLives, enemyCounter);
                // Initialize the level.
                level.initialize();
            }
            level.playOneTurn();
        }

        // if no more lives game end.
        if (numberOfLives.getValue() == 0) {
            this.animationRunner.run(
                    new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                            new EndGameAnimation(false, scoreCounter, this.keyboard)));
            table.newScore(gui, scoreCounter);
        }

        this.animationRunner.run(
                new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(table, KeyboardSensor.SPACE_KEY, keyboard)));
        try {
            table.save(file);
        } catch (IOException e) {
            System.err.println("Unable to find file: " + file.getName());
        }
    }
}