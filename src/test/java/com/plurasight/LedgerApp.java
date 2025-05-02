package com.plurasight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class LedgerApp {

    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Transactions> ledger = new ArrayList<>();
    private static final String TRANSACTIONS_FILE = "datafiles.transactions.csv";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        loadTransactions();

        boolean running = true;
        //keeps program running until user decides to exit
        while (running) {
            displayMainMenu();
            int selectedMenuOption = input.nextInt();
            input.nextLine(); // Consume newline

            switch (selectedMenuOption) {
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    viewLedger();
                    break;
                case 4:
                    System.out.println("Done");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid menu option. Select option.");
                    // the program has menu selections for the following:  addDeposit(), makePayment(), viewLedger(), or exit the loop
            }
        }
        saveTransactions(); // Save transactions when exiting the application
        input.close();
    }

    public record Transactions(LocalDateTime transTime, double amount, String vendor, String description) {

        public String getFormattedDate() {
            return transTime.format(DATE_TIME_FORMATTER);
        }
    }

    public static void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;

            // Skip the header line if the file is not empty
            if ((line = reader.readLine()) != null) {
                // Assuming the header is "Date|Vendor|Description|Amount"
            }

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    try {
                        LocalDateTime transTime = LocalDateTime.parse(parts[0].trim(), DATE_TIME_FORMATTER);
                        String vendor = parts[1].trim();
                        String description = parts[2].trim();
                        double amount = Double.parseDouble(parts[3].trim());
                        ledger.add(new Transactions(transTime, amount, vendor, description));
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Transactions file not found. A new one will be created.");
        } catch (IOException e) {
            System.err.println("Error reading transactions file: " + e.getMessage());
        }
    }

    public static void saveTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            // Write header
            writer.write("Date|Vendor|Description|Amount");
            writer.newLine();

            for (Transactions transaction : ledger) {
                writer.write(String.format("%s|%s|%s|%.2f",
                        transaction.getFormattedDate(),
                        transaction.vendor(),
                        transaction.description(),
                        transaction.amount()));
                writer.newLine();
            }
            System.out.println("Transactions saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }


    public static void displayMainMenu() {
        System.out.println("\nCoffee Shop Invoice");
        System.out.println("Enter 1 to add Deposit");
        System.out.println("Enter 2 to make Payment");
        System.out.println("Enter 3 to View Ledger");
        System.out.println("Enter 4 to Exit");
        System.out.print("Enter an option: ");
    }

    public static void addDeposit() {
        System.out.println("\n=== Add Deposit ===");

        System.out.print("Enter product description: ");
        String description = input.nextLine();

        System.out.print("Enter vendor name: ");
        String vendor = input.nextLine();

        System.out.print("Enter the amount to deposit: ");
        double amount = input.nextDouble();
        input.nextLine(); // Consume newline

        Transactions newDeposit = new Transactions(LocalDateTime.now(), amount, vendor, description);
        ledger.add(newDeposit);
        System.out.println("Deposit added.");

        promptReturnToMenu();
    }

    public static void makePayment() {
        System.out.println("\n=== Make Payment ===");

        System.out.print("Enter payment amount: ");
        double amount = input.nextDouble();
        input.nextLine(); // Consume newline

        System.out.print("Enter vendor: ");
        String vendor = input.nextLine();

        System.out.print("Enter description: ");
        String description = input.nextLine();

        // Making the amount negative for payment
        Transactions newPayment = new Transactions(LocalDateTime.now(), -amount, vendor, description);
        ledger.add(newPayment);
        System.out.println("Payment made.");

        promptReturnToMenu();
    }

    public static void viewLedger() {
        boolean viewing = true;

        while (viewing) {
            System.out.println("\n=== Ledger Menu ===");
            System.out.println("    1) Enter 1 To Display All Entries");
            System.out.println("    2) Enter 2 To Display All Deposits");
            System.out.println("    3) Enter 3 To Display All Payments");
            System.out.println("    4) Enter 4 To Display All Reports");
            System.out.println("    5) Enter 5 to Return to Home Screen");
            System.out.print("    Select an option: ");

            int choice = input.nextInt();
            input.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    printAllTransactions();
                    break;
                case 2:
                    printDepositsOnly();
                    break;
                case 3:
                    printPaymentsOnly();
                    break;
                case 4:
                    viewReports();
                    break;
                case 5:
                    viewing = false; //exit
                    break;
                default:
                    System.out.println("Invalid menu option. Try again.");
            }
        }
    }

    public static void viewReports() {
        boolean viewingReports = true;

        while (viewingReports) {
            System.out.println("\n=== Reports Menu ===");
            System.out.println("    1) Enter 1 To View Month To Date");
            System.out.println("    2) Enter 2 To View Previous Month");
            System.out.println("    3) Enter 3 To View Year To Date");
            System.out.println("    4) Enter 4 To View Previous Year");
            System.out.println("    5) Enter 5 To View Search by Vendor");
            System.out.println("    0) Enter 0 To Go Back to Ledger Menu");
            System.out.print("  Select an option: ");

            int reportChoice = input.nextInt();
            input.nextLine(); // consume leftover newline

            switch (reportChoice) {
                case 1:
                    showMonthToDate();
                    break;
                case 2:
                    showPreviousMonth();
                    break;
                case 3:
                    showYearToDate();
                    break;
                case 4:
                    showPreviousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    viewingReports = false; // exit the reports menu
                    break;
                default:
                    System.out.println("Error Invalid Selection. Enter Option: ");
            }
        }
    }

    public static void showMonthToDate() {
        System.out.println("\n=== Month To Date ===");
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        boolean found = false;
        for (Transactions t : ledger) {
            if (t.transTime().getMonthValue() == currentMonth && t.transTime().getYear() == currentYear) {
                printTransaction(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for the current month.");
        }
    }

    public static void showPreviousMonth() {
        System.out.println("\n=== Previous Month ===");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime previousMonthDateTime = now.minusMonths(1);
        int previousMonth = previousMonthDateTime.getMonthValue();
        int previousYear = previousMonthDateTime.getYear();

        boolean found = false;
        for (Transactions t : ledger) {
            if (t.transTime().getMonthValue() == previousMonth && t.transTime().getYear() == previousYear) {
                printTransaction(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for the previous month.");
        }
    }

    public static void showYearToDate() {
        System.out.println("\n=== Year To Date ===");
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();

        boolean found = false;
        for (Transactions t : ledger) {
            if (t.transTime().getYear() == currentYear) {
                printTransaction(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for the current year.");
        }
    }

    public static void showPreviousYear() {
        System.out.println("\n=== Previous Year ===");
        LocalDateTime now = LocalDateTime.now();
        int previousYear = now.minusYears(1).getYear();

        boolean found = false;
        for (Transactions t : ledger) {
            if (t.transTime().getYear() == previousYear) {
                printTransaction(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for the previous year.");
        }
    }

    public static void searchByVendor() {
        System.out.print("Enter vendor name to search: ");
        String vendor = input.nextLine().toLowerCase();

        System.out.println("\n=== Search Results for Vendor: " + vendor + " ===");
        boolean found = false;
        for (Transactions t : ledger) {
            if (t.vendor().toLowerCase().contains(vendor)) {
                printTransaction(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for vendor: " + vendor);
        }
    }

    public static void printAllTransactions() {
        System.out.println("\n=== All Transactions ===");
        if (ledger.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        for (Transactions t : ledger) {
            printTransaction(t);
            //   Prints all transactions stored in the ledger
        }
    }

    public static void printDepositsOnly() {
        System.out.println("\n=== Deposits Only ===");
        boolean found = false;
        for (Transactions t : ledger) {
            if (t.amount() > 0) { // Deposits are positive
                printTransaction(t);
                found = true;
            }
            //   Prints only transactions where the amount is greater than zero (deposits).
        }
        if (!found) {
            System.out.println("No deposits recorded.");
        }
    }

    public static void printPaymentsOnly() {
        System.out.println("\n=== Payments Only ===");
        boolean found = false;
        for (Transactions t : ledger) {
            if (t.amount() < 0) { // Payments are negative
                printTransaction(t);
                found = true;
                //   Prints only transactions where the amount is less than zero (payments).
            }
        }
        if (!found) {
            System.out.println("No payments recorded.");
        }
    }

    public static void printTransaction(Transactions t) {
        System.out.printf("%s | %-15s | %-30s | $%.2f\n",
                t.getFormattedDate(),
                t.vendor(),
                t.description(),
                t.amount());
        //  Helper method that prints a single Transactions object in a formatted way.
    }

    public static void promptReturnToMenu() {
        System.out.println("\nPress ENTER to return to the Home Screen...");
        input.nextLine();
    }

    // Removed the problematic saveTransaction method and integrated saving into the main exit flow.
    // The original method had hardcoded file "file.txt" and was not saving the ledger data.
}