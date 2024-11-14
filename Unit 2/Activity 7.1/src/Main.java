import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = ".\\contacts.obj";
    private static ArrayList<Contact> contactList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadContactList();

        boolean exit = false;
        while (!exit) {
            printMainMenu();

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createContact(scanner);
                    saveContacts();
                    break;
                case 2:
                    showContacts();
                    break;
                case 3:
                    searchContact(scanner);
                    break;
                case 4:
                    exit = true;
                    printMessage("Goodbye!");
                    break;
                default:
                    printMessage("Invalid option. Please select a valid option.");
            }
        }
    }

    private static void printMainMenu() {
        printMessage("\n--- Contact List ---");
        printMessage("1. Create new contact");
        printMessage("2. Show contact list");
        printMessage("3. Search contact by full name or phone number");
        printMessage("4. Exit");
        printMessage("Choose an option: ");
    }

    private static void loadContactList() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                contactList = (ArrayList<Contact>) ois.readObject();
                printMessage("Contacts loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                printMessage("Error loading contacts.");
            }
        }
        else {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                printMessage("Error loading contacts.");
            }
        }
    }

    private static void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contactList);
            printMessage("Contacts saved successfully.");
        } catch (IOException e) {
            printMessage("Error saving contacts.");
        }
    }

    private static void createContact(Scanner scanner) {
        printMessage("\n--- Create New Contact ---");
        printMessage("Enter name: ");
        String name = scanner.nextLine();
        printMessage("Enter surname: ");
        String surname = scanner.nextLine();
        printMessage("Enter email: ");
        String email = scanner.nextLine();
        printMessage("Enter phone number: ");
        String phone = scanner.nextLine();
        printMessage("Enter description: ");
        String description = scanner.nextLine();

        Contact newContact = new Contact(name, surname, email, phone, description);
        contactList.add(newContact);
        printMessage("Contact created successfully.");
    }

    private static void showContacts() {
        printMessage("\n--- Contact List ---");
        if (contactList.isEmpty()) {
            printMessage("No contacts available to show.");
        } else {
            for (Contact contact : contactList) {
                printMessage(contact.printContact());
                printMessage("----------");
            }
        }
    }

    private static void searchContact(Scanner scanner) {
        printMessage("\n--- Search Contact ---");
        printMessage("Enter full name or phone number: ");
        String query = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Contact contact : contactList) {
            if (contact.getFullName().toLowerCase().contains(query) || contact.getPhone().contains(query)) {
                printMessage(contact.printContact());
                printMessage("----------");
                found = true;
            }
        }
        if (!found) {
            printMessage("No contacts found matching your search.");
        }
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

}