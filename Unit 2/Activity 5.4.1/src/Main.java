import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file you wish to create: ");
        String fileName = scanner.nextLine();
        String filePath = ".\\" + fileName + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            System.out.print("The file already exists. Do you want to overwrite it? (Y/N): ");
            String overwriteOrNot = scanner.nextLine();
            if (overwriteOrNot.equalsIgnoreCase("Y")) {
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));
                System.out.println("--- Now you can type as many lines as you want, to stop typing type “exit” ---");
                boolean stop = false;
                int counter = 1;
                do
                {
                    String lineToWrite = scanner.nextLine();
                    if (lineToWrite.equals("exit"))
                        stop = true;
                    else
                        printWriter.println(counter + ". " + lineToWrite);
                    counter++;
                } while (!stop);
                printWriter.close();
            }
            else if (overwriteOrNot.equalsIgnoreCase("N")) {
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
                System.out.println("--- The file will not be overwrited. Now you can type as many lines as you want and will be appended to the filete, to stop typing type “exit” ---");
                boolean stop = false;
                int counter = getLastLineNumber(file);
                do
                {
                    String lineToWrite = scanner.nextLine();
                    if (lineToWrite.equals("exit"))
                        stop = true;
                    else
                        printWriter.println(counter + ". " + lineToWrite);
                    counter++;
                } while (!stop);
                printWriter.close();
            }
        }
        else
        {
            file.createNewFile();
            System.out.println(String.format("The file %s has been created.", fileName));
            System.out.print("Do you want to write on it? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));
                System.out.println("--- Now you can type as many lines as you want, to stop typing type “exit” ---");
                boolean stop = false;
                int counter = 1;
                do
                {
                    String lineToWrite = scanner.nextLine();
                    if (lineToWrite.equals("exit"))
                        stop = true;
                    else
                        printWriter.println(counter + ". " + lineToWrite);
                    counter++;
                } while (!stop);
                printWriter.close();
            }
        }

    }

    public static int getLastLineNumber(File file) throws IOException {
        int lastLineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^\\d+\\.\\s.*")) {
                    String numberPart = line.split("\\.")[0];
                    lastLineNumber = Integer.parseInt(numberPart);
                }
            }
        }

        return lastLineNumber;
    }
}