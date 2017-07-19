package general;

import java.util.ArrayList;
import java.util.List;

import levels.LevelFour;
import levels.LevelInformation;
import levels.LevelOne;
import levels.LevelThree;
import levels.LevelTwo;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-22-05 */
public class Ass5Game {

    /**
     * Create ArrayList of levels.
     * @param order - list of the level to run
     * @return list of the levels. */
    public static List<LevelInformation> levels(List<Integer> order) {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        // Add all the levels to the ArrayList.
        levels.add(new LevelOne());
        levels.add(new LevelTwo());
        levels.add(new LevelThree());
        levels.add(new LevelFour());
        List<LevelInformation> playLevels = new ArrayList<LevelInformation>();
        if (order.isEmpty()) {
            return levels;
        }
        for (int num : order) {
            if (num <= 4 && num > 0) {
                playLevels.add(levels.get(num - 1));
            }
        }
        return playLevels;
    }

    /**
    * cast a string type to int type.
    * <p>
    * gets a string array and returns its elements as int array
    * <p>
    * @param numbers - the string of numbers.
    * @return intNums - array of int. */
    public static List<Integer> stringsToInts(String[] numbers) {
        int size = numbers.length;
        List<Integer> intNums = new ArrayList<Integer>();
        int i = 0;
        while (i < size) {
            try {
                intNums.add(Integer.parseInt(numbers[i]));
                i++;
            } catch (NumberFormatException e) {
                i++;
            }
        }
        return intNums;
    }

    /**
     * Main function than run the game.
     * <p>
     * @param args - string of arguments to the main.*/
    public static void main(String[] args) {
        // Create a new GameFlow and run it.
        GameFlow game = new GameFlow();
        game.runLevels(levels(stringsToInts(args)));
    }
}