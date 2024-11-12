import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int day = 0;
        int month = 0;
        int year = 0;

        boolean validInput = false;
        do {
            System.out.print("Write the day of the date in numbers: ");
            day = scanner.nextInt();
            if (day >= 1 && day <= 31)
                validInput = true;
            else
                System.out.println("Not a valid day, please enter a valid day (1 to 31)");

        } while (!validInput);

        validInput = false;
        do {
            System.out.print("Write the month of the date in numbers: ");
            month = scanner.nextInt();
            if (month >= 1 && month <= 12)
                validInput = true;
            else
                System.out.println("Not a valid month, please enter a valid month (1 to 12)");

        } while (!validInput);

        System.out.print("Write the year of the date in numbers: ");
        year = scanner.nextInt();

        System.out.println(String.format("Your date is %d/%d/%d", day, month, year));


    }
}