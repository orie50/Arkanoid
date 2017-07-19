package animations;

import java.awt.Color;
import java.util.ArrayList;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameobjects.Sprite;
import general.Menu;
import general.Selection;

public class MenuAnimation<T> implements Menu<T> {
    private ArrayList<Selection<T>> selections;
    private ArrayList<Selection<Menu<T>>> subMenus;
    private T status;
    private Menu<T> menuStatus;
    private Sprite background;
    private boolean stop;
    private KeyboardSensor keyboard;

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
    public void doOneFrame(DrawSurface d, double dt) {
        this.background.drawOn(d);
        d.drawText(50, 50, "Arknoid" , 50);
        int i = 250;
        for (Selection<Menu<T>> select : this.subMenus) {
            d.setColor(Color.BLACK);
            d.drawText(160, i, "(" + select.getKey() + ")", 30);
            d.drawText(200, i, select.getMessage() , 30);
            i += 50;
        }
        for (Selection<T> select : this.selections) {
            d.setColor(Color.BLACK);
            d.drawText(160, i, "(" + select.getKey() + ")", 30);
            d.drawText(200, i, select.getMessage() , 30);
            i += 50;
        }
        for (Selection<Menu<T>> select : this.subMenus) {
            if (this.keyboard.isPressed(select.getKey())) { 
                this.menuStatus = select.getReturnVal();
                this.stop = true;
            }
        }
        for (Selection<T> select : this.selections) {
            if (this.keyboard.isPressed(select.getKey())) {
                this.status = select.getReturnVal();
                this.stop = true;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<T>(key, message, returnVal));
    }
    
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new Selection<Menu<T>>(key, message, subMenu));
    }
    
    @Override
    public void addSelection(Selection<T> select) {
        this.selections.add(select);    
    }


    @Override
    public T getStatus() {
        return this.status;
    }
    
    @Override
    public Menu<T> getMenuStatus() {
        return this.menuStatus;
    }

    @Override
    public void resetStatus() {
        this.status = null;
        this.menuStatus = null;
        this.stop = false;
    }
}
