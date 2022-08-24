package by.bsuir.lab;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import static by.bsuir.lab.Utilities.findFilePath;

class Tree {
    private Node rootNode;
    private int minWayNum;
    private String outStr;
    private final ArrayList<Integer> centralApex = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> arrOfWays = new ArrayList<>();

    public Tree() { // Пустое дерево
        rootNode = null;
    }


    public void insertNode(int value) {
        Node newNode = new Node();
        newNode.setValue(value);
        if (rootNode == null) {
            rootNode = newNode;
        }
        else {
            Node currentNode = rootNode;
            Node parentNode;
            boolean isDone = false;
            while (!isDone) {
                parentNode = currentNode;
                if(value == currentNode.getValue()) {
                    isDone = true;
                }
                else if (value < currentNode.getValue()) {
                    currentNode = currentNode.getLeftChild();
                    if (currentNode == null){
                        parentNode.setLeftChild(newNode);
                        isDone = true;
                    }
                }
                else {
                    currentNode = currentNode.getRightChild();
                    if (currentNode == null) {
                        parentNode.setRightChild(newNode);
                        isDone = true;
                    }
                }
            }
        }
    }

    public void deleteNode(int value)
    {
        Node currentNode = rootNode;
        Node parentNode;
        boolean isLeftChild;
        while (currentNode.getValue() != value) {
            parentNode = currentNode;
            if (value < currentNode.getValue()) {
                isLeftChild = true;
                currentNode = currentNode.getLeftChild();
            } else {
                isLeftChild = false;
                currentNode = currentNode.getRightChild();
            }
            if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
                if (currentNode == rootNode) {
                    rootNode = null;
                }
                else if (isLeftChild) {
                    parentNode.setLeftChild(null);
                }
                else {
                    parentNode.setRightChild(null);
                }
            } else if (currentNode.getRightChild() == null) {
                if (currentNode == rootNode)
                    rootNode = currentNode.getLeftChild();
                else if (isLeftChild)
                    parentNode.setLeftChild(currentNode.getLeftChild());
                else
                    parentNode.setRightChild(currentNode.getLeftChild());
            } else if (currentNode.getLeftChild() == null) {
                if (currentNode == rootNode)
                    rootNode = currentNode.getRightChild();
                else if (isLeftChild)
                    parentNode.setLeftChild(currentNode.getRightChild());
                else
                    parentNode.setRightChild(currentNode.getRightChild());
            } else {
                Node heir = receiveHeir(currentNode);
                if (currentNode == rootNode)
                    rootNode = heir;
                else if (isLeftChild)
                    parentNode.setLeftChild(heir);
                else
                    parentNode.setRightChild(heir);
            }
        }
    }
    private Node receiveHeir(Node node) {
        Node parentNode = node;
        Node heirNode = node;
        Node currentNode = node.getRightChild();
        while (currentNode != null){
            parentNode = heirNode;
            heirNode = currentNode;
            currentNode = currentNode.getLeftChild();
        }
        // Если преемник не является
        if (heirNode != node.getRightChild()) {
            parentNode.setLeftChild(heirNode.getRightChild());
            heirNode.setRightChild(node.getRightChild());
        }
        return heirNode;
    }

    public void printTree() {
        Stack globalStack = new Stack();
        globalStack.push(rootNode);
        int gaps = 32;
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);
        while (!isRowEmpty) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (!globalStack.isEmpty()) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.getValue());
                    localStack.push(temp.getLeftChild());
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null ||
                            temp.getRightChild() != null)
                        isRowEmpty = false;
                }
                else {
                    System.out.print("__");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
        System.out.println(separator);
    }

    public void deleteTree(){
        rootNode = null;
    }

    public void readTreeInFile() {
        boolean isCorrect = true;
        String temp = null;
        String[] sArr = null;
        this.deleteTree();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(findFilePath()));
            temp = reader.readLine();
        } catch (IOException e) {
            System.out.println("Файл поврежден.");
            isCorrect = false;
        }
        if (Objects.isNull(temp)) {
            System.out.println("Файл поврежден.");
            isCorrect = false;
        }
        if (isCorrect) {
            sArr = Utilities.ownSplit(temp);
            isCorrect = Utilities.areNumbersInArray(sArr);
        }
        if (isCorrect) {
            for (String str : sArr) {
                this.insertNode(Integer.parseInt(str));
            }
            System.out.println("Успешно загружено.");
        }
    }

    public void findNumToMinWay(){
        int wayNum = 0;
        minWayNum = 1000;
        minWay(rootNode, wayNum);
    }

    public void findWays(){
        findNumToMinWay();
        int wayNum = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        outStr = "";
        createMinWay(rootNode, wayNum, arr);

    }

    private void createMinWay(Node currentNode, int wayNum, ArrayList<Integer> arr){
        wayNum++;
        arr.add(currentNode.getValue());
        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
            if (wayNum == minWayNum) {
                System.out.println("Путь: ");
                outStr = outStr + "Путь: ";
                for (Integer integer : arr) {
                    System.out.println(integer);
                    outStr = outStr + integer;
                }
                ArrayList<Integer> newArr = new ArrayList<>(arr);
                arrOfWays.add(newArr);
                if (arrOfWays.size() % 2 != 0) {
                    centralApex.add(arr.get((int) Math.ceil(arr.size() / 2)));
                }
            }
        }
        if (currentNode.getLeftChild() != null && wayNum <= minWayNum) {
            createMinWay(currentNode.getLeftChild(), wayNum, arr);
        } else if (arr.get(arr.size() - 1) == currentNode.getValue()) {
            arr.remove(arr.size() - 1);
        }
        if (currentNode.getRightChild() != null && wayNum <= minWayNum) {
            createMinWay(currentNode.getRightChild(), wayNum, arr);
        } else if (arr.get(arr.size() - 1) == currentNode.getValue()) {
            arr.remove(arr.size() - 1);
        }
    }

    public void deleteCentralApex(){
        for (Integer apex : centralApex) {
            this.deleteNode(apex);
        }
    }


    private void minWay(Node currentNode, int wayNum){
        wayNum++;
        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null){
            if (wayNum < minWayNum){
                minWayNum = wayNum;
            }
        } else {
            if (currentNode.getLeftChild() != null) minWay(currentNode.getLeftChild(), wayNum);
            if (currentNode.getRightChild() != null) minWay(currentNode.getRightChild(), wayNum);
        }
    }

    public void saveWay() {
        if (!Objects.equals(outStr, "") && outStr != null) {
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter(findFilePath()));
                writer.write(outStr);
                writer.close();
            } catch (IOException e) {
                InfoController.showIOException();
            }
            InfoController.showSuccessfulSave();
        } else {
            System.out.println("Ошибка в сохранении. Результат не вычислен.");
        }
    }
}
