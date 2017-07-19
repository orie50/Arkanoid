package gameobjects;

import listeners.ShootListener;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-06-17 */
public interface ShootNotifier extends Sprite {

    /**
     * Add the Shoot listener to the list of listeners.
     * <p>
     * @param sl - the ShootListener that listens.*/
    void addShootListener(ShootListener sl);

    /**
     * remove the Shootlistener from the list of listeners.
     * <p>
     * @param sl - the ShootListener that listens.*/
    void removeShootListener(ShootListener sl);
}