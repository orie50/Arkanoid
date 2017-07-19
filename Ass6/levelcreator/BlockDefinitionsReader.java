package levelcreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

import score.SerializationException;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-06-6 */
public class BlockDefinitionsReader {

    /**
     * Create a factory of blocks from a file.
     * <p>
     * @param reader - the block definition file.
     * @return new factory that contain symbols of blocks and other parameters.
     * @throws IOException if Failed closing file.
     * */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) throws IOException {

        Map<String, String> defaultDef = new TreeMap<String, String>();
        Map<String, String> blockDef = new TreeMap<String, String>();
        Map<String, Integer> spaceDef = new TreeMap<String, Integer>();
        BufferedReader bufferReader = new BufferedReader(reader);
        // Read the block definition file.
        try {
            String line = null;
            // Run in a loop until finish to read the file.
            while ((line = bufferReader.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(" ");
                switch (parts[0]) {
                // case that the line is about the default of blocks.
                case "default":
                    // Put the default parameters in the default map.
                    for (int i = 1; i < parts.length; i++) {
                        String[] parts2 = parts[i].split(":");
                        defaultDef.put(parts2[0], parts2[1]);
                    }
                    break;
                    // case that the line is about blocks.
                case "bdef":
                    // Split the block symbol and give it his parameters.
                    String[] parts2 = parts[1].split(":");
                    if (parts2[0].equals("symbol") && parts2[1].length() == 1) {
                        blockDef.put(parts2[1], line.substring(14));
                    } else {
                        throw new SerializationException();
                    }
                    break;
                    // case that the line is about spaces.
                case "sdef":
                    // Split the symbol and give it his width value.
                    String[] parts3 = parts[1].split(":");
                    if (parts3[0].equals("symbol") && parts3[1].length() == 1) {
                        Integer value = Integer.parseInt(parts[2].split(":")[1]);
                        spaceDef.put(parts3[1], value);
                    } else {
                        throw new SerializationException();
                    }
                    break;

                default:
                    throw new SerializationException();
                }
            }
            try {
                // Return new factory.
                return new BlocksFromSymbolsFactory(defaultDef, blockDef, spaceDef);
            } catch (SerializationException e) {
                throw e;
            }
        } catch (SerializationException e) {
            throw e;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
    }
}
