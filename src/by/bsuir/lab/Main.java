package by.bsuir.lab;

import static by.bsuir.lab.MenuConstants.MENU_EXIT_INDEX;

public class Main {

    public static void main(String[] args) {
        int key;
        do {
            MenuController.showMenu();
            key = MenuController.findMenuItem();
            MenuController.doChosenMenuItem(key);
        } while (key != MENU_EXIT_INDEX);
        InfoController.showExitLabel();
    }
}
