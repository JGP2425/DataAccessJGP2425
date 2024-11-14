import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word you want to search for within the file: ");
        String wordToSearch = scanner.nextLine();
        String filePath = ".\\file.txt";
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            ArrayList<String> lineList = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }

            boolean wordsFounded = false;
            for (int i = 0; i < lineList.size(); i++) {
                if (lineList.get(i).toLowerCase().contains(wordToSearch.toLowerCase())) {
                    System.out.println(String.format("%d. %s", i+1, lineList.get(i)));
                    wordsFounded = true;
                }
            }

            if (!wordsFounded)
                System.out.println(String.format("The words: %s are not present in the file", wordToSearch));
        }
        else
            System.out.println("File not founded.");
    }
}