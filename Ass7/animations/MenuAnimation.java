package animations;

import java.awt.Color;
import java.util.ArrayList;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameobjects.Sprite;
import general.Menu;
import general.Selection;
/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-06
 * @param <T> - the return value type*/
public class MenuAnimation<T> implements Menu<T> {
    private ArrayList<Selection<T>> selections;
    private ArrayList<Selection<Menu<T>>> subMenus;
    private T status;
    private Menu<T> menuStatus;
    private Sprite background;
    private boolean stop;
    private KeyboardSensor keyboard;

    /** Menu Animation constructor.
     * <p>
     * @param background - the animation background
     * @param keyboard - KeyboardSensor
     */
    public MenuAnimation(Sprite background, KeyboardSensor keyboard) {
        this.background = background;
        this.selections = new ArrayList<Selection<T>>();
        this.subMenus = new ArrayList<Selection<Menu<T>>>();
        this.status = null;
        this.menuStatus = null;
        this.stop = false;
        this.keyboard = keyboard;
    }

    @Override
    /** draw one frame of the animation.
     * <p>
     * @param dt - the speed per frame.
     * @param d - a draw surface to draw the frame on. */
    public void doOneFrame(DrawSurface d, double dt) {
        this.background.drawOn(d);
        d.drawText(50, 50, "Space invaders" , 50);
        int i = 250;
        //draw the sub menus selections
        for (Selection<Menu<T>> select : this.subMenus) {
            d.setColor(Color.BLACK);
            d.drawText(160, i, "(" + select.getKey() + ")", 30);
            d.drawText(200, i, select.getMessage() , 30);
            i += 50;
        }
        //draw the selections
        for (Selection<T> select : this.selections) {
            d.setColor(Color.BLACK);
            d.drawText(160, i, "(" + select.getKey() + ")", 30);
            d.drawText(200, i, select.getMessage() , 30);
            i += 50;
        }
        //check if one of the submenus been selected
        for (Selection<Menu<T>> select : this.subMenus) {
            if (this.keyboard.isPressed(select.getKey())) {
                this.menuStatus = select.getReturnVal();
                this.stop = true;
            }
        }
        //check if one of the selections been selected
        for (Selection<T> select : this.selections) {
            if (this.keyboard.isPressed(select.getKey())) {
                this.status = select.getReturnVal();
                this.stop = true;
            }
        }
    }

    @Override
    /** returns information about the continuation of the animation.
     * <p>
     * @return stop - if the animation should stop' returns true. */
    public boolean shouldStop() {
        return stop;
    }

    @Override
    /** adds a selection to the menu
     * <p>
     * @param key - the choosing key
     * @param message - message to draw in the animation
     * @param returnVal - element that will be returned if this selection has been chosen */
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<T>(key, message, returnVal));
    }

    @Override
    /** adds a Sub-Menu to the menu
     * <p>
     * @param key - the choosing key
     * @param message - message to draw in the animation
     * @param subMenu- menu that will be showed if this selection has been chosen */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new Selection<Menu<T>>(key, message, subMenu));
    }

    @Override
    /** adds a selection to the menu
     * <p>
     * @param select - selection as an object */
    public void addSelection(Selection<T> select) {
        this.selections.add(select);
    }


    @Override
    /** returns the selection return value that was chosen.
     * <p>
     * @return status - return value of the selection. */
    public T getStatus() {
        return this.status;
    }

    @Override
    /** returns the sub menu that was chosen.
     * <p>
     * @return status - return value of the selection. */
    public Menu<T> getMenuStatus() {
        return this.menuStatus;
    }

    @Override
    /** reset the status of the menu.*/
    public void resetStatus() {
        this.status = null;
        this.menuStatus = null;
        this.stop = false;
    }
}
