package general;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06
 * @param <T> - the return value type*/
public class Selection<T> {
    private String key;
    private String message;
    private T returnVal;

    /**
     * Instantiates a new selection.
     *
     * @param key - the choosing key
     * @param message - message to draw in the animation
     * @param returnVal - element that will be returned if this selection has been chosen*/
    public Selection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the return value.
     *
     * @return the return value
     */
    public T getReturnVal() {
        return returnVal;
    }
}
