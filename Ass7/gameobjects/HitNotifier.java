package gameobjects;

import listeners.HitListener;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-10 */
public interface HitNotifier extends Sprite {

    /**
     * Add the hit listener to the list of listeners to hit events.
     * <p>
     * @param hl - the HitListener that listend.*/
    void addHitListener(HitListener hl);

    /**
     * remove the hit listener from the list of listeners to hit events.
     * <p>
     * @param hl - the HitListener that listend.*/
    void removeHitListener(HitListener hl);
}