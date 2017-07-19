package score;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06
 * @param <E> - the return value type*/
public interface Serialization<E> {

    /**
     * Serialize an object into a string.
     *
     * @return string - serialization result*/
    String serialize();

    /**
     * Deserialize an E object from a string.
     *
     * @param s - string representation of an object
     * @return e - the object.
     * @throws SerializationException the serialization exception*/
    E deserialize(String s) throws SerializationException;
}
