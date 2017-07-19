package score;
public class ScoreInfo implements Comparable<ScoreInfo>, Serialization<ScoreInfo>{
    private Integer score;
    private String name;
    
    public ScoreInfo(String name, int score) { 
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return this.name;    
    }
    public int getScore() {
        return this.score;
    }
    
    @Override
    public int compareTo(ScoreInfo score) {
        if (this.score < score.getScore())
            return 1;
        else if (this.score > score.getScore()) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        sb.append(",");
        sb.append(this.score.toString());
        return sb.toString();
    }

    @Override
    public ScoreInfo deserialize(String s) throws SerializationException {
        try {
            String[] parts = s.split(",", 2);
            return new ScoreInfo(parts[0], Integer.parseInt(parts[1]));
        } catch (RuntimeException e) {
            throw new SerializationException(e);
        }
    }
}
