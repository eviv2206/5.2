package by.bsuir.lab;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utilities {
    public static String findFilePath() {
        String filePath;
        FileReader reader = null;
        boolean isIncorrect;
        do {
            System.out.print("Введите путь к файлу: ");
            filePath = ScannerController.findDataFromConsole();
            isIncorrect = false;
            try {
                reader = new FileReader(filePath);
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден.");
                isIncorrect = true;
            }
        } while (isIncorrect);
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("I/O error.");
        }
        return filePath;
    }

    public static String[] ownSplit(String str){
        int counter = 0;
        char[] cArray = str.toCharArray();
        int n = cArray.length;
        for (int i = 0; i < n; i++){
            if (cArray[i] == ' '){
                counter++;
            }
        }
        String temp = "";
        int k = 0;
        String[] strArray = new String[counter + 1];
        for (int i = 0; i < n; i++){
            if (cArray[i] == ' '){
                strArray[k] = temp;
                k++;
                temp = "";
            } else {
                temp = temp + cArray[i];
            }
            strArray[k] = temp;
        }
        return strArray;
    }

    public static boolean areNumbersInArray(String[] arr){
        int i = 0;
        boolean isCorrect = true;
        while (isCorrect && i < arr.length){
            try {
                Integer.parseInt(arr[i]);
            } catch (NumberFormatException e){
                isCorrect = false;
            }
            i++;
        }
        return isCorrect;
    }

}
