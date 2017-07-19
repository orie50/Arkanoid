package general;

import animations.Animation;

public interface Menu<T> extends Animation {
    void addSelection(String key, String message, T returnVal);
    void addSelection(Selection<T> select);
    //void addSubMenu(String key, String message, Menu<T> subMenu);
    T getStatus();
    public Menu<T> getMenuStatus();
    public void addSubMenu(String key, String message, Menu<T> subMenu);
    public void resetStatus();
}
