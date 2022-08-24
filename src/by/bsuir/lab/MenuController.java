package by.bsuir.lab;

import static by.bsuir.lab.MenuConstants.MENU_MAX_INDEX;
import static by.bsuir.lab.MenuConstants.MENU_MIN_INDEX;

public class MenuController {
    private static Tree tree = new Tree();
    public static void showMenu() {
        System.out.println("""
                    1.Добавить элемент в дерево.
                    2.Показать дерево.
                    3.Очистить дерево.
                    4.Создать дерево из файла.
                    5.Найти путь минимальной длины между корнем и листьями и удалить центральную вершину.
                    6.Сохранить результат в файл.
                    7.О программе.
                    8.Выход.
                    """);
    }

    public static void doChosenMenuItem(Integer key){
        switch (key){
            case(1) -> addElementToTree();
            case(2) -> showTree();
            case(3) -> deleteTree();
            case(4) -> findTreeFromFile();
            case(5) -> showMinWaysAndDelete();
            case(6) -> saveResult();
            case(7) -> showAboutTheProgram();
        }
    }

    private static void saveResult() {
        tree.saveWay();
    }

    private static void showMinWaysAndDelete(){
        tree.findWays();
        tree.deleteCentralApex();
    }


    private static void findTreeFromFile() {
        tree.readTreeInFile();
    }

    private static void showAboutTheProgram() {
        InfoController.showInfo();
    }

    private static void deleteTree(){
        tree.deleteTree();
    }

    private static void showTree(){
        tree.printTree();
    }

    private static void addElementToTree(){
        int element = findTreeElement();
        tree.insertNode(element);
        System.out.println("Успешно добавлено.");
    }

    private static int findTreeElement(){
        String elem;
        do {
            System.out.println("Введите элемент");
            elem = ScannerController.findDataFromConsole();
        } while (!isNum(elem));
        return Integer.parseInt(elem);
    }

    public static int findMenuItem(){
        String tempItem;
        do {
            tempItem = ScannerController.findDataFromConsole();
        } while (!isValidMenuItem(tempItem));
        return Integer.parseInt(tempItem);
    }

    private static boolean isValidMenuItem(String key){
        boolean isCorrect = isNum(key);
        if (isCorrect && (Integer.parseInt(key) < MENU_MIN_INDEX || Integer.parseInt(key) > MENU_MAX_INDEX)){
            isCorrect = false;
        }
        if (!isCorrect){
            showUncorrectedMenuItem();
        }
        return isCorrect;
    }

    private static void showUncorrectedMenuItem(){
        System.out.println("Выберите корректный пункт меню:");
    }

    private static boolean isNum(String key){
        boolean isCorrect = true;
        try{
            Integer.parseInt(key);
        } catch (NumberFormatException e){
            isCorrect = false;
        }
        return isCorrect;
    }
}
