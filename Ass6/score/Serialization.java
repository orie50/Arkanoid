package score;

public interface Serialization<E> {
    String serialize();
    E deserialize(String s) throws SerializationException;
}
