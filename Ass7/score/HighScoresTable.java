package score;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import biuoop.DialogManager;
import biuoop.GUI;
import listeners.Counter;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06*/
public class HighScoresTable implements Serialization<HighScoresTable> {

    private Integer maxSize;
    private List<ScoreInfo> highScores;

    /**
     * Instantiates a new high scores table.
     * <p>
     * Create an empty high-scores table with the specified size.
     * @param size - the table holds up to size top scores*/
    public HighScoresTable(int size) {
        this.maxSize = size;
        this.highScores = new ArrayList<ScoreInfo>();
    }

    /**
     * Adds a high-score.
     * <p>
     * @param score - highScore information
     */
    public void add(ScoreInfo score) {
        this.highScores.add(score);
        Collections.sort(this.highScores);
        if (this.highScores.size() > this.size()) {
            this.highScores.remove(maxSize);
        }
    }

    /**
     * @return the current table size*/
    public int size() {
        return this.highScores.size();
    }


    /**
     * Gets the high scores as a sorted list.
     * <p>
     * @return list of high scores*/
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * Gets the rank of the current score.
     * <p>
     * where will it be on the list if added
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        ScoreInfo temp = new ScoreInfo("temp", score);
        this.highScores.add(temp);
        Collections.sort(this.highScores);
        int rank =  this.highScores.indexOf(temp) + 1;
        this.highScores.remove(temp);
        return rank;
    }

    /**
     * Clears the table.*/
    public void clear() {
        this.highScores.removeAll(highScores);
    }

    /**
     * Load table data from file.
     * <p>
     * @param filename - the  a table file
     * @throws IOException Signals that an I/O exception has occurred.*/
    public void load(File filename) throws IOException {
        this.clear();
        BufferedReader reader = null;
        try {
            //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename.getName());
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filename)));
            String line = reader.readLine();
            if (line != null) {
                HighScoresTable table = this.deserialize(line);
                this.maxSize = table.maxSize;
                this.highScores.addAll(table.getHighScores());
            }
        } catch (SerializationException e) {
            System.err.println("Deserialization problem " + filename.getName());
            throw e;
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename.getName());
            throw e;
        } catch (IOException e) {
            System.err.println("Failed reading file: " + filename.getName()
            + ", message:" + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename - the  a table file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void save(File filename) throws IOException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filename)));
            writer.print(this.serialize());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Read a table from file and return it.
     * <p>
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename
     * @return the high scores table*/
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable(5);
        try {
            table.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename.getName() + " new Table is created");
        } catch (IOException e) {
            System.err.println("Failed reading file: " + filename.getName()
            + ", message:" + e.getMessage() + " new Table is created");
        }
        return table;
    }


    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.maxSize + ":");
        for (ScoreInfo score : this.highScores) {
            sb.append(score.serialize());
            sb.append(";");
        }
        return sb.toString();
    }

    @Override
    public HighScoresTable deserialize(String s) throws SerializationException {
        String[] parts = s.split(":");
        try {
            HighScoresTable table = new HighScoresTable(Integer.parseInt(parts[0]));
            String[] parts2 = parts[1].split(";");
            ScoreInfo temp = new ScoreInfo("none" , 0);
            for (int i = 0; i < parts2.length; i++) {
                table.add(temp.deserialize(parts2[i]));
            }
            return table;
        } catch (RuntimeException e) {
            throw new SerializationException(e);
        }
    }

    /**
     * New score.
     *
     * @param gui the gui
     * @param scoreCounter the score counter
     */
    public void newScore(GUI gui, Counter scoreCounter) {
        int currentScore = scoreCounter.getValue();
        if (this.getRank(currentScore) <= 5) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("new High score!", "What is your name?", "");
            while (!name.matches("(\\w)*")) {
                name = dialog.showQuestionDialog("new High score!",
                        "What is your name?\nonly [a-z]/[A-Z]/[0-9] is allowed", "");
            }
            this.add(new ScoreInfo(name, currentScore));
        }
    }
}
