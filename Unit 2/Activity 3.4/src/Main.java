import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyDate myDate = new MyDate();

        boolean validInput = false;
        do {
            System.out.print("Write the month of the date in numbers: ");
            myDate.setMonth(scanner.nextInt());
            if (myDate.getMonth() >= 1 && myDate.getMonth() <= 12)
                validInput = true;
            else
                System.out.println("Not a valid month, please enter a valid month (1 to 12)");

        } while (!validInput);

        validInput = true;
        do {
            System.out.print("Write the day of the date in numbers: ");
            myDate.setDay(scanner.nextInt());
            if (myDate.getDay() == 0) {
                validInput = false;
                System.out.println("Not a valid day, please enter a valid day");
            }
            else
                validInput = true;
        } while (!validInput);

        System.out.print("Write the year of the date in numbers: ");
        myDate.setYear(scanner.nextInt());

        myDate.printDate();
    }
}