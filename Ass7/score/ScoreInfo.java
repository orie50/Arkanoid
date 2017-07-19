package score;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06*/
public class ScoreInfo implements Comparable<ScoreInfo>, Serialization<ScoreInfo> {

    private Integer score;
    private String name;

    /**
     * Instantiates a new score info.
     *
     * @param name - the player name
     * @param score - the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the player name.
     * <p>
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the player score.
     * <p>
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(ScoreInfo scoreInfo) {
        if (this.score < scoreInfo.getScore()) {
            return 1;
        } else if (this.score > scoreInfo.getScore()) {
            return -1;
        }
        return 0;
    }

    @Override
    /**
     * Serialize ScoreInfo into a string.
     *
     * @return string - serialization result*/
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        sb.append(",");
        sb.append(this.score.toString());
        return sb.toString();
    }


    @Override
    /**
     * Deserialize a ScoreINnfo object from a string.
     *
     * @param s - string representation of an object
     * @return ScoreInfo - the object.
     * @throws SerializationException the serialization exception*/
    public ScoreInfo deserialize(String s) throws SerializationException {
        try {
            String[] parts = s.split(",", 2);
            return new ScoreInfo(parts[0], Integer.parseInt(parts[1]));
        } catch (RuntimeException e) {
            throw new SerializationException(e);
        }
    }
}
