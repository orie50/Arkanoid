package score;


import java.io.IOException;

/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-06
* Exception representing that something went wrong
* during the serialization process.*/
public class SerializationException extends IOException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new serialization exception.
     */
    public SerializationException() {
    }

    /**
     * Instantiates a new serialization exception.
     *
     * @param message - the exception message
     */
    public SerializationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new serialization exception.
     *
     * @param message - the exception message
     * @param cause - the cause
     */
    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new serialization exception.
     *
     * @param cause the cause
     */
    public SerializationException(Throwable cause) {
        super(cause);
    }
}
