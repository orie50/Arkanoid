package levelcreator;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import gameobjects.Block;
import score.SerializationException;
/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-7 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths = new TreeMap<String, Integer>();
    private Map<String, BlocksCreator> blockCreators = new TreeMap<String, BlocksCreator>();

    /**
     * Constructor - create map for the blocks and map for the spaces.
     * <p>
     * @param defaultDef - the map of the default parameters for block.
     * @param blockDef - the parameters of the blocks by symbols.
     * @param spaceDef - the width of the spaces by symbols.
     * @throws SerializationException - if can't find/read the file.
     * */
    public BlocksFromSymbolsFactory(Map<String, String> defaultDef, Map<String, String> blockDef,
            Map<String, Integer> spaceDef) throws SerializationException {
        setSpaceCreators(spaceDef);
        setBlockCreators(defaultDef, blockDef);
    }

    /**
     * @param s - the given symbol of space.
     * @return true if the given symbol belong to the spaces map. */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * @param s - the given symbol of block.
     * @return true if the given symbol belong to the blocks map. */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * @param s - the given symbol of block.
     * @return the width of the given block. */
    public int getBlockWidth(String s) {
        return blockCreators.get(s).getWidth();
    }

    /**
     * Create new block by symbol and coordinate.
     * <p>
     * @param s - the given symbol of block.
     * @param xpos - the x value of the block upper left point.
     * @param ypos - the y value of the block upper left point.
     * @return new block. */
    public Block getBlock(String s, int xpos, int ypos) {
        return blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * @param s - the given symbol of space.
     * @return the width of the given space. */
    public int getSpaceWidth(String s) {
        return spacerWidths.get(s);
    }

    /**
     * Create the blocks creator map.
     * put for each block symbol all the parameters.
     * <p>
     * @param defaultDef - the map of the default parameters for block
     * @param blockDef - the parameters of the blocks by symbols.
     * @throws SerializationException - if can't find/read the file. */
    public void setBlockCreators(Map<String, String> defaultDef,
            Map<String, String> blockDef) throws SerializationException {
        // Temporary map with empty keys.
        Map<String, String> parameters = new TreeMap<String, String>();
        parameters.put("symbol", null);
        parameters.put("width", null);
        parameters.put("height", null);
        parameters.put("hit_points", null);
        parameters.put("stroke", "");
        // Run of all the blockDef map with iterator.
        Set<Entry<String, String>> values = blockDef.entrySet();
        Iterator<Entry<String, String>> i = values.iterator();
        Entry<String, String> value = null;
        while (i.hasNext()) {
            Map<Integer, String> fills = new TreeMap<Integer, String>();
            value = i.next();
            // Every block symbol get the default map.
            parameters.putAll(defaultDef);
            parameters.put("symbol", value.getKey());
            String line = value.getValue();
            // Split and put the value to every key parameter.
            String[] parts = line.split(" ");
            for (int j = 0; j < parts.length; j++) {
                String[] parts1 = parts[j].split(":");
                switch (parts1[0]) {
                case "width":
                    parameters.put("width", parts1[1]);
                    break;
                case "height":
                    parameters.put("height", parts1[1]);
                    break;
                case "hit_points":
                    parameters.put("hit_points", parts1[1]);
                    break;
                case "stroke":
                    parameters.put("stroke", parts1[1]);
                    break;
                default:
                    if (parts1[0].contains("fill")) {
                        String fillNum = null;
                        // If fill have no number.
                        if (parts1[0].length() > 4) {
                            fillNum = parts1[0].substring(5);
                        } else {
                            fillNum = "1";
                        }
                        fills.put(Integer.parseInt(fillNum), parts1[1]);
                    } else {
                        throw new SerializationException("Wrong parameters");
                    }
                    break;
                }
            }
            if (parameters.containsValue(null)) {
                throw new SerializationException("Not enough parameters");
            }
            // Create BlocksCreator and add it to the map with his symbol.
            BlocksCreator block = new BlocksCreator(parameters, fills);
            blockCreators.put(value.getKey(), block);
        }
    }

    /**
     * Create the spaces map.
     * put for each space symbol his width.
     * <p>
     * @param spaceDef - the width of the spaces by symbols.
     * @throws SerializationException - if can't find/read the file. */
    public void setSpaceCreators(Map<String, Integer> spaceDef) throws SerializationException {
        Set<Entry<String, Integer>> values = spaceDef.entrySet();
        Iterator<Entry<String, Integer>> i = values.iterator();
        Entry<String, Integer> value = null;
        while (i.hasNext()) {
            value = i.next();
            spacerWidths.put(value.getKey(), value.getValue());
        }
    }
}