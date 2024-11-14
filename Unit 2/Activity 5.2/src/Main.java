import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the first word file: ");
        String file1Name = scanner.nextLine();
        String file1Path = ".\\" + file1Name + ".txt";
        File file1 = new File(file1Path);
        file1.createNewFile();
        PrintWriter sortedFile1Printer = new PrintWriter(new BufferedWriter(new FileWriter(file1, false)));
        sortedFile1Printer.println("Car");
        sortedFile1Printer.println("Horse");
        sortedFile1Printer.println("Ship");
        sortedFile1Printer.close();

        System.out.print("Enter the name of the second word file: ");
        String file2Name = scanner.nextLine();
        String file2Path = ".\\" + file2Name + ".txt";
        File file2 = new File(file2Path);
        file1.createNewFile();
        PrintWriter sortedFile2Printer = new PrintWriter(new BufferedWriter(new FileWriter(file2, false)));
        sortedFile2Printer.println("Alley");
        sortedFile2Printer.println("Cat");
        sortedFile2Printer.println("Horn");
        sortedFile2Printer.println("Show");
        sortedFile2Printer.println("Tree");
        sortedFile2Printer.close();

        String sortedFilePath = ".\\sorted.txt";
        File file = new File(sortedFilePath);
        file.createNewFile();

        BufferedReader bufferReaderFile1 = new BufferedReader(new FileReader(file1Path));
        BufferedReader bufferReaderFile2 = new BufferedReader(new FileReader(file2Path));
        ArrayList<String> wordsList = new ArrayList<>();
        String word;

        while ((word = bufferReaderFile1.readLine()) != null)
            wordsList.add(word);
        while ((word = bufferReaderFile2.readLine()) != null)
            wordsList.add(word);

        Collections.sort(wordsList);

        PrintWriter sortedFilePrinter = new PrintWriter(new BufferedWriter(new FileWriter(sortedFilePath, false)));
        for (int i = 0; i < wordsList.size(); i++) {
            sortedFilePrinter.println(wordsList.get(i));
        }
        sortedFilePrinter.close();

        System.out.println("Words successfully sorted!");
    }
}
