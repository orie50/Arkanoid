package general;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06
 * @param <T> - the return value type*/
public interface Task<T> {

    /**
     * Run the task.
     * <p>
     * @return T - the running result*/
    T run();
}
