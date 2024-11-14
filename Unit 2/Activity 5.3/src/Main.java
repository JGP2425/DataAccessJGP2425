import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\file.txt";
        File file = new File(filePath);

        if (file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            int counter = 1;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (counter == 23)
                {
                    counter = 0;
                    System.out.println("---- To continue reading 23 more lines of the file press any button ----");
                    System.in.read();
                }
                counter++;

            }
        }
        else {
            System.out.print("File not founded.");
        }
    }
}