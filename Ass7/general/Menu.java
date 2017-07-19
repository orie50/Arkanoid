package general;

import animations.Animation;
/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06
 * @param <T> - the return value type*/
public interface Menu<T> extends Animation {
    /** adds a Sub-Menu to the menu.
     * <p>
     * @param key - the choosing key
     * @param message - message to draw in the animation
     * @param subMenu - menu that will be showed if this selection has been chosen */
    void addSubMenu(String key, String message, Menu<T> subMenu);
    /** adds a selection to the menu.
     * <p>
     * @param key - the choosing key
     * @param message - message to draw in the animation
     * @param returnVal - element that will be returned if this selection has been chosen */
    void addSelection(String key, String message, T returnVal);
    /** adds a selection to the menu.
     * <p>
     * @param select - selection as an object */
    void addSelection(Selection<T> select);
    /** returns the selection return value that was chosen.
     * <p>
     * @return status - return value of the selection. */
    T getStatus();
    /** returns the sub menu that was chosen.
     * <p>
     * @return status - return value of the selection. */
    Menu<T> getMenuStatus();
    /** reset the status of the menu.*/
    void resetStatus();
}
