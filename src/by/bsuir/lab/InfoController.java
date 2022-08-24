package by.bsuir.lab;

public class InfoController {
    public static void showInfo() {
        System.out.println("""
                Данная программа формирует и выводит бинарное дерево,
                находит пути минимальной длины между корнем и листьями
                и удаляет центральные вершины этих путей.
                Центральной вершиной считается - кол-во вершин в пути / 2
                (Если кол-во вершин четное - удаление не производится).
                """);
    }

    public static void showExitLabel() {
        System.out.println("Выход...");
    }

    public static void showIOException() {
        System.out.println("I/O exception");
    }

    public static void fileNotFound() {
        System.out.println("Файл не найден");
    }

    public static void showSuccessfulSave(){
        System.out.println("Успешно сохранено.");
    }
}

