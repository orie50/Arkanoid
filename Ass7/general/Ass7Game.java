package general;

import java.util.ArrayList;
import java.util.List;

import levels.LevelInformation;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-22-05 */
public class Ass7Game {

    /**
     * Create ArrayList of levels.
     * @param order - list of the level to run
     * @return list of the levels. */
    public static List<LevelInformation> levels(List<Integer> order) {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
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
        GameFlow game = new GameFlow();
        if (args.length == 0) {
            game.showMenu("level_sets.txt");
        } else {
            game.showMenu(args[0]);
        }
    }
}