package levelcreator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

import animations.MenuAnimation;
import backgrounds.MenuBackground;
import biuoop.KeyboardSensor;
import general.GameFlow;
import general.Menu;
import general.Selection;
import general.Task;

public class LevelSetReader {
    private GameFlow game;

    public LevelSetReader(GameFlow game) {
        this.game = game;
    }

    private class LevelTask<T> implements Task<T>{
        private List<LevelInformation> levelList;

        @Override
        public T run() {
            game.runLevels(levelList);
            return null;
        }

        public LevelTask(List<LevelInformation> levelList) {
            this.levelList = levelList;
        }
    }

    public Menu<Task<Void>> getLevelSetMenu(KeyboardSensor keyboard, String levelSet) throws FileNotFoundException{
        LineNumberReader reader = null;
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(new MenuBackground(), keyboard);
        try {
            // bytes to characters wrapper0
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSet);
            System.out.println(levelSet);
            reader = new LineNumberReader(new InputStreamReader(is));
            int linenum = reader.getLineNumber();
            String line = reader.readLine();
            String[] parts = null;
            List<LevelInformation> levelList = null;
            while (line != null) {
                System.out.println(line);
                if (linenum % 2 == 0) {
                    parts = line.split(":");
                } else {
                    LevelSpecificationReader lsReader = new LevelSpecificationReader();
                    InputStream is1 = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
                    //bytes to characters wrapper
                    levelList = lsReader.fromReader(
                            new BufferedReader(
                                    new InputStreamReader(is1)));
                    Selection<Task<Void>> select =
                            new Selection<Task<Void>>(parts[0], parts[1], new LevelTask<Void>(levelList));
                    menu.addSelection(select);
                }
                linenum = reader.getLineNumber();
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file");
            throw e;
        } catch (IOException e) {
            System.err.println("Failed reading file"+ ", message:" + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + levelSet);
            }
        }
        return menu;
    }
}