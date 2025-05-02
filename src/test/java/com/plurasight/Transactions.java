package com.plurasight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transactions {
    LocalDateTime transTime;
    String description;
    String vendor;
    double amount;

    public Transactions(LocalDateTime transTime, double amount, String vendor, String description) {
        this.transTime = transTime;
        this.amount = amount;
        this.vendor = vendor;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransTime(LocalDateTime transTime) {
        this.transTime = transTime;
    }

    public LocalDateTime getTransTime() {
        return transTime;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        return transTime.format(formatter);
    }


    @Override
    public String toString() {
        return String.format("%s | %-15s | %-25s | $%.2f", getFormattedDate(), vendor, description, amount);
    }

    public static void main(String[] args) {
        List<Transactions> transactions = new ArrayList<>();

        // Add transactions from the provided data
        transactions.add(createTransaction("2024-09-15", "08:00:23", "Espresso Machine Cleaner", "Urnex", -25.50));
        transactions.add(createTransaction("2024-03-01", "11:30:45", "Grinder Cleaning Tablets", "Grindz", -18.75));
        transactions.add(createTransaction("2024-12-10", "14:00:00", "Milk Frother Cleaner", "Rinza", -32.00));
        transactions.add(createTransaction("2024-01-20", "09:15:00", "Barista Cloths (Pack of 12)", "Microfiber Solutions", -15.00));
        transactions.add(createTransaction("2024-05-05", "16:45:12", "Floor Cleaner", "Easy Clean", -40.00));
        transactions.add(createTransaction("2025-02-18", "10:00:00", "Bucket and Mop уборка", " уборка", -22.00));
        transactions.add(createTransaction("2024-07-08", "13:00:00", "Cleaning Brushes Set", "Bristle Bros", -12.50));
        transactions.add(createTransaction("2024-11-03", "17:20:18", "Sanitizing Spray", "Sani-Spritz", -30.00));
        transactions.add(createTransaction("2025-04-01", "12:00:00", "Trash Bags (Box of 100)", "TrashCo", -20.00));
        transactions.add(createTransaction("2024-06-22", "19:00:00", "Dish Soap (Gallon)", "Soapy Suds", -10.00));

        // Print the transactions
        for (Transactions transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static Transactions createTransaction(String date, String time, String description, String vendor, double amount) {
        LocalDateTime transTime = LocalDateTime.parse(date + "T" + time);
        return new Transactions(transTime, amount, vendor, description);
    }
}