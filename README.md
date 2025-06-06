Capstone1

The Ledger application is a command-line tool designed to help users track their financial transactions, specifically deposits and payments.
It provides an interface for adding new transactions, viewing a detailed ledger, and generating various financial reports. 
The application uses transaction data through a CSV file, ensuring that information is saved between sessions.



The home screen presents the user with the following options:

Main Menu
Choose an option:
1) Add Deposit
2) Make Payment (Debit)
3) Ledger
4) Exit

Ledger
Choose an option:
A) All - Display all entries
D) Deposits - Display only deposits
P) Payments - Display only payments
R) Reports - Go to the reports screen
H) Home - Go back to the home page

Reports
Month To Date
Previous Month
Year To Date
Previous Year
Search by Vendor
Back - Go back to the ledger page H) Home - Go back to the home page



Interesting piece of code

One interesting piece of code in this project is DateTime Format-  is a formatter that will consistently format dates and times in the "yyyy-MM-dd HH:mm:ss" pattern within the class. This is a common practice to ensure consistent date/time formatting throughout your code.
Ex:
 private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

Error Handling-  Learning to anticipate and handle potential problems (like file errors) is a crucial but a confusing aspect of programming. I use Gemini to help explain and revise any errors after exhausting attempts at resolving in debugger without success.



Screenshots:

 Main Menu
Ledger Menu
Reports Menu

 

![Screenshot 2025-05-02 113815](https://github.com/user-attachments/assets/1d9f115d-11cf-466d-be2f-92b023384a76)
