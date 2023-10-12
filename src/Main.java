import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.ConsoleColors;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) throws IOException {
        tasks = loadDataToTab(FILE_NAME);
        Options(OPTIONS);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "exit":
                saveTabToFile(FILE_NAME, tasks);
                System.out.println(ConsoleColors.RED + "Bye");
                System.exit(0);
                break;
            case "add":
                addTask();
                break;
            case "remove":
                removeTask(tasks, enterIndexToRemove());
                System.out.println("Removed Successfully");
                break;
            case "list":
                printTab(tasks);
                break;
            default:
                System.out.println("Select correct option: ");
        }
        Options(OPTIONS);
    }

    public static void Options(String[] tab) {
        System.out.println(pl.coderslab.ConsoleColors.BLUE);
        System.out.println("Choose what you want to do" + ConsoleColors.RESET);
        for (Object o : tab) {
            System.out.println(o);
        }
    }

    public static int enterIndexToRemove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which element do you want to remove?" + ConsoleColors.RESET);
        String n = scanner.nextLine();
        while (isNumberOtherThanZero(n)) {
            System.out.println("Incorrect value, enter one more time: " + ConsoleColors.RESET);
        }
        return Integer.parseInt(n);
    }

    public static boolean isNumberOtherThanZero(String arg) {
        if (NumberUtils.isParsable(arg)) {
            return Integer.parseInt(arg) >= 0;
        }
        return false;
    }

    public static String[][] loadDataToTab(String fileName) throws IOException {
        Path file = Paths.get(fileName);
        if (!Files.exists(file)) {
            System.out.println("File doesn't exist");
            System.exit(0);
        }
        String[][] data = null;
        try {
            List<String> wordList = Files.readAllLines(file);
            data = new String[wordList.size()][wordList.get(0).split(",").length];
            for (int i = 0; i < wordList.size(); i++) {
                String[] split = wordList.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    data[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.println(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add task description: ");
        String description = scanner.nextLine();
        System.out.println("Add task due date: ");
        String dueDate = scanner.nextLine();
        System.out.println("Is it important? true/false");
        String isImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;
    }

    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Lack of element in tab");
        }
    }

    public static void saveTabToFile(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

