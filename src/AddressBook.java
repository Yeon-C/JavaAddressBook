import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
    // Object Entries;
    String FName, LName, PNumber, Email;
    ArrayList<Entries> AdBook = new ArrayList<Entries>();
    List<Entries> toRemove = new ArrayList<Entries>();
    Iterator<Entries> iter = AdBook.iterator();
    Scanner Scan = new Scanner(System.in);
    public static Scanner input = new Scanner(System.in);

    // Adding Entries
    public void addEntries() {
        boolean dupecheck = false;
        boolean repeat = true;
        while (repeat) {
            System.out.println("Enter First Name: ");
            FName = Scan.next();
            System.out.println("Enter Last Name: ");
            LName = Scan.next();
            System.out.println("Enter Phone Number: ");
            PNumber = Scan.next();
            System.out.println("Enter Email: ");
            Email = Scan.next();
            // Check for duplicate emails
            for (Entries Entries : this.AdBook) {
                if (Entries.Email.equals(Email)) {
                    dupecheck = true;
                } else {
                    dupecheck = false;
                }
            }
            if (!dupecheck) {
                AdBook.add(new Entries(FName, LName, PNumber, Email));
                System.out.println("Added a new Entry!");
                repeat = false;
            } else {
                System.out.println("Entry with same email already exists!");
                System.out.println("Please try again");
                repeat = true;
            }
        }

    }

    // Remove function uses another array list to store data to avoid
    // ConcurrentModificationException
    public void removeEntries() {
        System.out.println("");
        System.out.println("Search Options: ");
        System.out.println("1) Delete by Phone Number");
        System.out.println("2) Delete by Email");
        System.out.println("Choose Delete Option (enter the number): ");
        int option = Integer.parseInt(Scan.next());
        switch (option) {
            case 1:
                System.out.println("Enter Phone Number: ");
                String PhoneNum = Scan.next();

                for (Entries Entries : this.AdBook) {
                    if (Entries.PhoneNumber.equals(PhoneNum)) {
                        toRemove.add(Entries);
                    }
                }
                if (toRemove.size() > 0) {
                    this.AdBook.removeAll(toRemove);
                } else {
                    System.out.println("Found no match!");
                }

                System.out.println("Removed entry with Phone Number: " + PhoneNum);
                break;
            case 2:
                System.out.println("Enter Email: ");
                String EmailA = Scan.next();

                for (Entries Entries : this.AdBook) {
                    if (Entries.Email.equals(EmailA)) {
                        toRemove.add(Entries);
                    }
                }
                if (toRemove.size() > 0) {
                    this.AdBook.removeAll(toRemove);
                } else {
                    System.out.println("Found no match!");
                }

                System.out.println("Removed entry with email: " + EmailA);
                break;

        }
    }

    // Prints the Address Book
    public void printBook() {
        if (this.AdBook.size() > 0) {
            for (Entries Entries : this.AdBook) {
                System.out.println("**********");
                System.out.println("First Name: " + Entries.FirstName);
                System.out.println("Last Name: " + Entries.LastName);
                System.out.println("Phone Number: " + Entries.PhoneNumber);
                System.out.println("Email: " + Entries.Email);
                System.out.println("**********");
                System.out.println("");
            }
        } else {
            System.out.println("Book is Empty!");
        }

    }

    // Method to simplify search method
    public void printEntry(Entries Entries) {
        System.out.println("**********");
        System.out.println("First Name: " + Entries.FirstName);
        System.out.println("Last Name: " + Entries.LastName);
        System.out.println("Phone Number: " + Entries.PhoneNumber);
        System.out.println("Email: " + Entries.Email);
        System.out.println("**********");
    }

    // Searches AddressBook Entries by certain criteria
    public void searchEntries() {
        boolean exists = false;
        System.out.println("Search Options: ");
        System.out.println("1) First Name");
        System.out.println("2) Last Name");
        System.out.println("3) Phone Number");
        System.out.println("4) Email Address");
        System.out.println("");
        System.out.println("Choose Search Option: ");
        try {
            int option = Integer.parseInt(Scan.next());

            System.out.println("Enter Your Search: ");
            String Search = Scan.next();

            switch (option) {
                case 1:
                    for (Entries Entries : this.AdBook) {
                        if (Entries.FirstName.startsWith(Search)) {
                            printEntry(Entries);
                            exists = true;
                        }
                    }
                    if (!exists) {
                        System.out.println("");
                        System.out.println("Entry does not exist!");
                    }
                    break;
                case 2:
                    for (Entries Entries : this.AdBook) {
                        if (Entries.LastName.startsWith(Search)) {
                            printEntry(Entries);
                            exists = true;
                        }
                    }
                    if (!exists) {
                        System.out.println("");
                        System.out.println("Entry does not exist!");
                    }
                    break;
                case 3:
                    for (Entries Entries : this.AdBook) {
                        if (Entries.PhoneNumber.equals(Search)) {
                            printEntry(Entries);
                            exists = true;
                        }
                    }
                    if (!exists) {
                        System.out.println("");
                        System.out.println("Entry does not exist!");
                    }
                    break;
                case 4:
                    for (Entries Entries : this.AdBook) {
                        if (Entries.Email.equals(Search)) {
                            printEntry(Entries);
                            exists = true;
                        }
                    }
                    if (!exists) {
                        System.out.println("");
                        System.out.println("Entry does not exist!");
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.print("Your selection can only be an integer!");
        }
        if (this.AdBook.size() == 0) {
            System.out.println("");
            System.out.println("Book is Empty!");
        }
    }

    // Clears entire AddressBook
    public void clearBook() {
        this.AdBook.clear();
        System.out.println("Address Book has been cleared!");
    }

    public void saveFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Book.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.AdBook);
            out.close();
            fileOut.close();
            System.out.println("");
            System.out.printf("Saved the Address Book! (File Name is Book.ser)");
            System.out.println("");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void loadFile() {
        try {
            FileInputStream fileIn = new FileInputStream("Book.ser");
            ObjectInputStream out2 = new ObjectInputStream(fileIn);
            // Ignore this warning
            this.AdBook = (ArrayList<Entries>) out2.readObject();

            out2.close();
            fileIn.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
        System.out.println("");
        System.out.println("Loaded the Address Book!");
        System.out.println("");
    }

    public static void main(String[] args) {
        AddressBook AdBook1 = new AddressBook();
        boolean stop = false;

        System.out.println("Welcome to the Address Book Program");
        System.out.println("Please choose an option below: ");

        while (!stop) {
            System.out.println("");
            System.out.println("1) Add Entry");
            System.out.println("2) Remove Entry");
            System.out.println("3) Search Entry");
            System.out.println("4) Print Address Book");
            System.out.println("5) Clear Address Book");
            System.out.println("6) Save Address Book");
            System.out.println("7) Load Address Book");
            System.out.println("8) Quit Program");
            System.out.println("");

            System.out.println("Please choose what you'd like to do with the database (enter the number): ");
            try {
                int choice = Integer.parseInt(input.next());

                switch (choice) {
                    case 1:
                        AdBook1.addEntries();
                        break;
                    case 2:
                        AdBook1.removeEntries();
                        break;
                    case 3:
                        AdBook1.searchEntries();
                        break;
                    case 4:
                        AdBook1.printBook();
                        break;
                    case 5:
                        AdBook1.clearBook();
                        break;
                    case 6:
                        AdBook1.saveFile();
                        break;
                    case 7:
                        AdBook1.loadFile();
                        break;
                    case 8:
                        stop = true;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("");
                System.out.print("Your selection can only be an integer!");
            }
        }

        System.out.println("Thank you for using the AddressBook program!");

    }
}