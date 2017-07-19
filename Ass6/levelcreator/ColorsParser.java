package levelcreator;

import java.awt.Color;

import score.SerializationException;


/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-8 */
public class ColorsParser {

    /**
     * Return a color from a string of color name.
     * <p>
     * @param line - the line with the color name.
     * @return the color.
     * @throws SerializationException if failed read the color. */
    public Color colorFromString(String line) throws SerializationException {
        Color color;
        // Check if the color is created by RBG.
        if (line.startsWith("color(RGB")) {
            // Cut the line and give split to r, g, b.
            String colorLine = line.substring(10, line.length() - 2);
            String[] colors = colorLine.split(",");
            int r = Integer.parseInt(colors[0]);
            int g = Integer.parseInt(colors[1]);
            int b = Integer.parseInt(colors[2]);
            return new Color(r, g, b);
        }
        // else, check if the color is one of specific colors, if not throw exception.
        String cutLine = line.substring(6);
        String colorLine = cutLine.substring(0, cutLine.length() - 1);
        switch (colorLine) {
        case "black":
            color = Color.black;
            break;
        case "blue":
            color = Color.blue;
            break;
        case "cyan":
            color = Color.cyan;
            break;
        case "gray":
            color = Color.gray;
            break;
        case "lightGray":
            color = Color.lightGray;
            break;
        case "green":
            color = Color.green;
            break;
        case "orange":
            color = Color.orange;
            break;
        case "pink":
            color = Color.pink;
            break;
        case "red":
            color = Color.red;
            break;
        case "white":
            color = Color.white;
            break;
        case "yellow":
            color = Color.yellow;
            break;
        default:
            throw new SerializationException(" Color not exist.");
        }
        return color;
    }
}