package general;
import java.util.List;

import animations.AnimationRunner;
import animations.EndGameAnimation;
import animations.GameLevel;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import listeners.Counter;

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

    /**
     * Run the game.
     * Get ArrayList of levels and run it in a loop as long left lives
     * <p>
     * @param levels - the levels ArrayList. */
    public void runLevels(List<LevelInformation> levels) {
        // Create the score counter and lives counter.
        Counter scoreCounter = new Counter();
        Counter numberOfLives = new Counter(7);;
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
                this.animationRunner.run(new EndGameAnimation(false, scoreCounter, this.keyboard));
                this.gui.close();
                return;
            }
        }
        if (i == levels.size()) {
            this.animationRunner.run(new EndGameAnimation(true, scoreCounter, this.keyboard));
        }
    }
}