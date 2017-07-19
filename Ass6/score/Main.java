package score;
import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        HighScoresTable table = new HighScoresTable(6);
//        for (int i = 0; i < 6; i++) {
//            table.add(new ScoreInfo("name" + i, i));
//        }
        File file = new File("C:/Users/Adiel/workspace/ass6/highscores.txt");
        try {
            table.load(file);
        } catch (IOException e) {
            System.out.println("error");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("great");
    }

}
