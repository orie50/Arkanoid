package levelcreator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import gameobjects.Block;
import geometry.Point;
import geometry.Rectangle;
import score.SerializationException;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-8 */
public class BlocksCreator implements BlockCreator {

    private int width;
    private int height;
    private int hitPoints;
    private Map<Integer, Color> fillColor;
    private Map<Integer, BufferedImage> fillImage;
    private Color stroke;

    /** BlocksCreator constructor - give the block all his parameters.
     * <p>
     * @param block - a map with the width, height, hit points and stroke of the block.
     * @param fills - map of the fills of the block.
     * @throws SerializationException if failed read stroke color. */
    public BlocksCreator(Map<String, String> block, Map<Integer, String> fills) throws SerializationException {
        this.width = Integer.parseInt(block.get("width"));
        this.height = Integer.parseInt(block.get("height"));
        this.hitPoints = Integer.parseInt(block.get("hit_points"));
        setStroke(block.get("stroke"));
        this.fillColor = new TreeMap<Integer, Color>();
        this.fillImage = new TreeMap<Integer, BufferedImage>();
        this.setFills(fills);
    }

    @Override
    /**
     * Create a new block.
     * <p>
     * @param xpos - the x value of the upper left point of the block.
     * @param ypos - the y value of the upper left point of the block.
     * @return new block.
     * */
    public Block create(int xpos, int ypos) {
        Point point = new Point(xpos, ypos);
        return new Block(new Rectangle(point, width, height), hitPoints, stroke, this.fillColor, this.fillImage);
    }

    /**
     * @return the block width. */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the stroke color from a string.
     * <p>
     * @param color - the color name.
     * @throws SerializationException if failed read stroke color. */
    public void setStroke(String color) throws SerializationException {
        if (color.isEmpty()) {
            this.stroke = null;
            return;
        }
        ColorsParser colorParser = new ColorsParser();
        this.stroke = colorParser.colorFromString(color);
    }

    /**
     * Divided the fill to 2 maps (colors and image).
     * run with iterator on the fills map check if it's
     * color or image and put it in the right map.
     * <p>
     * @param fills - the map of the fills.
     * */
    public void setFills(Map<Integer, String> fills) {
        Set<Entry<Integer, String>> values = fills.entrySet();
        Iterator<Entry<Integer, String>> i = values.iterator();
        Entry<Integer, String> value = null;
        // Run on fills map.
        while (i.hasNext()) {
            value = i.next();
            String s = value.getValue();
            if (s.contains("image")) {
                // If it's image put in images map.
                try {
                    String imageLine = s.substring(6, s.length() - 1);
                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageLine);
                    BufferedImage image = ImageIO.read(is);
                    fillImage.put(value.getKey(), image);
                } catch (IOException e) {
                    System.out.println("Failed read the image");
                    System.exit(0);
                }
            } else {
                // If it's color create color from string put in colors map.
                ColorsParser cp = new ColorsParser();
                try {
                    fillColor.put(value.getKey(), cp.colorFromString(s));
                } catch (SerializationException e) {
                    System.out.println("Failed converting color from string");
                    System.exit(0);
                }
            }
        }
    }
}
