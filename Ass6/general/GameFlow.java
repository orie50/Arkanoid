package general;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import animations.AnimationRunner;
import animations.EndGameAnimation;
import animations.GameLevel;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import animations.MenuAnimation;
import backgrounds.MenuBackground;
import biuoop.KeyboardSensor;
import levelcreator.LevelInformation;
import levelcreator.LevelSetReader;
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
     * Constructor - Create the GUI the AnimationRunner and KeyboardSensor of the game.. */
    public GameFlow() {
        this.gui = new biuoop.GUI("title", 800, 600);
        this.animationRunner = new AnimationRunner(this.gui, 60);
        this.keyboard = gui.getKeyboardSensor();
    }

    public void showMenu(String levelSet) {
        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>(new MenuBackground(), keyboard);
        LevelSetReader reader = new LevelSetReader(this);
        try {
            Menu<Task<Void>> subMenu = reader.getLevelSetMenu(keyboard, levelSet);
            menu.addSubMenu("s", "Start Game", subMenu);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        menu.addSelection("h", "High Scores", new Task<Void>() {
           public Void run() {
                File file = new File("./highscore.txt");
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
        menu.addSelection("e", "Exit", new Task<Void>(){
            public Void run() {
                System.exit(0);
                return null;
            }
        });
        while (true) {
            this.animationRunner.run(menu);
            Task<Void> t = menu.getStatus();
            Menu<Task<Void>> subMenu = menu.getMenuStatus();
            if (t != null) {
                t.run();
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
    public void runLevels(List<LevelInformation> levels) {
        // Create the score counter and lives counter.
        Counter scoreCounter = new Counter();
        Counter numberOfLives = new Counter(7);
        File file = new File("./highscore.txt");
        HighScoresTable table = HighScoresTable.loadFromFile(file);
        try {
            table.save(file);
        } catch (IOException e) {
            System.err.println("Unable to find file: " + file.getName());
        }
        int i;
        for (i = 0; i < levels.size(); i++) {

            // Create the level
            GameLevel level = new GameLevel(levels.get(i), this.keyboard,
                    this.animationRunner, this.gui, scoreCounter, numberOfLives);

            // Initialize the level.
            level.initialize();

            // while the level left blocks and still have lives run one turn.
            while (level.haveBlocks() && numberOfLives.getValue() > 0) {
                level.playOneTurn();
            }

            // if no more lives game end.
            if (numberOfLives.getValue() == 0) {
                this.animationRunner.run(
                        new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                                new EndGameAnimation(false, scoreCounter, this.keyboard)));
                table.newScore(gui, scoreCounter);
                break;
            }
        }
        if (i == levels.size()) {
            this.animationRunner.run(
                    new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                            new EndGameAnimation(true, scoreCounter, this.keyboard)));
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