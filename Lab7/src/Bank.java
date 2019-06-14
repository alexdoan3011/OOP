
/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 7
 * Date: Mar 19, 2019
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * the purpose of this class is to manipulate bank accounts, including:
 * displaying single, displaying all, updating by amount, monthly updating,
 * adding new account
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since 1.8
 */
public class Bank {
    /**
     * this temporary ArrayList object stores all import data
     */
    static ArrayList<String> temp;
    /**
     * this counter points to the current import data being imported
     */
    static int counter = 0;
    /**
     * this boolean disable import function when it is true
     */
    static boolean importDisabled = false;
    /**
     * this formatter writes output to bankoutput.txt file
     */
    private static PrintWriter output;
    /**
     * arrayList contains all bank accounts
     */
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    /**
     * this scanner object reads from bankinput.txt file
     */
    private static Scanner input;

    /**
     * return the index of an account in the account array given its account number
     *
     * @param accNo account number of the account to get index of. Second parameter
     *              indicates whether a new account is being added or not
     * @return index of account with account number equal to first element of
     * parameter array
     */
    static int findAccount(long... accNo) {// second element of array equal 0 if program is not in process of adding
        // account, 1 if it is
        int index = -1;
        ArrayList<Long> usedAccNum = new ArrayList<>();// creating array storing all account numbers
        for (int i = 0; i < accounts.size() - accNo[1]; i++) {// not checking the account currently being created
            usedAccNum.add(accounts.get(i).accNumber);// initializing usedAccNum
        }
        for (int i = 0; i < usedAccNum.size(); i++) {// check if user input exist in usedAccNum array
            long unwrap = usedAccNum.get(i);// unwrapping Integer account number to int
            if (accNo[0] == unwrap) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * this method looks for import data file in predefined path
     */
    static void openInputFile() {
        Path pathAbsolute = Paths.get(new File("bankinput.txt").getAbsolutePath());// use absolute path of bankinput.txt
        Path pathAbsoluteAlt = Paths.get(new File("Lab7\\bankinput.txt").getAbsolutePath());// use alternative absolute
        // path of bankinput.txt for
        // intellij
        Path pathRelative = Paths.get("bankinput.txt");// use relative path of bankinput.txt
        try {
            if (Files.exists(pathAbsolute)) {// try looking all 3 different paths for the input file
                input = new Scanner(pathAbsolute);
            } else if (Files.exists(pathRelative)) {
                input = new Scanner(pathRelative);
            } else if (Files.exists(pathAbsoluteAlt)) {
                input = new Scanner(pathAbsoluteAlt);
            } else {// throws error when file not found
                System.err.println("Import data not found. Import disabled");
                importDisabled = true;// disable import if can't find file
            }
        } catch (IOException ioException) {
            System.err.println("Error opening import data file. Import disabled");
            importDisabled = true;// disable import if can't read file
        }
    }

    /**
     * this method reads the import data file and records the data in temp. Call
     * closeInputFile() when done. Delete all data in temp when data is bad
     */
    static void readRecords() {
        if (!importDisabled) {
            temp = new ArrayList<>();
            try {
                try {
                    while (input.hasNext()) {
                        temp.add(input.next());// add all elements of the input file, separated by white space, into the temp arrayList
                    }
                } catch (NoSuchElementException noSuchElementException) {// check for file reading error
                    System.err.println("Import data file corrupted. Import disabled");
                    importDisabled = true;// disable import if can't read file
                    temp.clear();// remove all bad data in temp array
                } catch (IllegalStateException illegalStateException) {
                    System.err.println("Error opening import data file. Import disabled");
                    importDisabled = true;// disable import if can't read file
                    temp.clear();// remove all bad data in temp array
                }
            } catch (NullPointerException nullPointerException) {
                System.err.println("Error opening import data file. Import disabled");
                importDisabled = true;// disabling import
            }
            closeInputFile();
        }
    }

    /**
     * close input method when there is no more data to read
     */
    private static void closeInputFile() {
        if (input != null) {// avoid nullPointerException
            input.close();
        }
    }

    /**
     * invoke updateBalance after validating input. Rollback update when overdrawn
     * is detected, throw error
     *
     * @return message before exiting to main menu
     */
    String updateAccount() {
        String input;
        String message = "";// contains the return message
        if (accounts.size() > 0) {// check if there is any account to update
            int index = findAccount();// ask user for account number to update
            if (index == -1) {// check if user chooses to exit during account lookup
                return "";// exit the method, return to selection screen
            }
            double amt = 0;// amount of balance update to account
            System.out.println("Would you like to withdraw or deposit?");
            System.out.println("w: Withdraw");
            System.out.println("d: Deposit");
            String choice = "";
            boolean validChoice = false;
            while (!validChoice) {
                validChoice = true;
                choice = Assign1.spaceCheck("choice");// asking user to withdraw or deposit
                switch (choice.toLowerCase()) {
                    case "w":
                        choice = "withdraw";// choice now turned to a full string for easy user prompting
                        break;
                    case "d":
                        choice = "deposit";
                        break;
                    case "\\m":// end the method, return to main menu
                        return "";
                    default:
                        validChoice = false;// loop the input method with invalid input
                        System.out.println("Invalid input. Please re-enter choice: ");
                }
            }
            System.out.println("Enter amount to " + choice + ": ");// prompt for amount
            boolean validAmount = false;// amount input validation controller
            while (!validAmount) {// input validation loop. Loop when invalid input
                validAmount = true;
                try {
                    input = Assign1.spaceCheck("amount to " + choice);
                    if (input.equals("\\m")) {// end the method, return to main menu
                        return "";
                    }
                    amt = Double.parseDouble(input);
                } catch (Exception e) {
                    System.out.println("Invalid input. Please re-enter amount to " + choice + ": ");
                    validAmount = false;// restart the input validation loop
                }
            }
            boolean updateSuccessful = false;// indicator for when the update is successful
            switch (choice) {
                case "withdraw":
                    try {
                        accounts.get(index).withdraw(amt);// this method throws TransactionIllegalArgumentException if amt is negative or bigger than balance
                        updateSuccessful = true;
                    } catch (TransactionIllegalArgumentException transactionIllegalArgumentException) {
                        System.err.println(transactionIllegalArgumentException);
                    }
                    break;
                case "deposit":
                    try {
                        accounts.get(index).deposit(amt);// this method throws TransactionIllegalArgumentException if amt is negative
                        updateSuccessful = true;
                    } catch (TransactionIllegalArgumentException transactionIllegalArgumentException) {
                        System.err.println(transactionIllegalArgumentException);
                    }
                    break;
            }
            if (updateSuccessful) {// display successful message, as well as the account details after update
                message = "Update operation for account " + accounts.get(index).accNumber + " successful:\n";
                message += accounts.get(index);
            }
        } else {
            message = "No account to update. Re-enter your choice: ";// error thrown when there is no account to update.
            // exit the method, go back to main menu
        }
        return message;
    }

    /**
     * for adding new account. Check if after adding account, number of accounts
     * does not exceed maxSize. Prompt user for account type, check input. Increase
     * numAccount by 1 if add successfully.
     *
     * @return true if new account added successfully, false otherwise
     */
    boolean addAccount() {
        if (temp == null) {
            temp = new ArrayList<>();
        }
        int sizeBeforeRun = accounts.size();// record the size of account array before running the method
        int sizeAfterRun;// record the size of account array after running the method
        try {// catching any import error
            String input;
            System.out.println("Enter details of account holder " + (accounts.size() + 1));
            System.out.println("=================================");
            System.out.println("Enter account type (s for savings, c for chequing): ");
            boolean valid = false;// input validation loop controller
            String choice = "";// contain user selection for account adding type. s for saving, c for chequing
            if (temp.size() != 0 && !importDisabled) {
                choice = temp.get(counter).toLowerCase();
                System.out.println("[" + Bank.temp.get(Bank.counter) + "]");
                counter++;
            } else {
                while (!valid) {
                    valid = true;
                    if (!choice.isEmpty()) {// check if choice has been inputted before
                        System.out.println("Invalid input. Please re-enter valid option: ");// only display on the
                        // second loop forward
                    }
                    input = Assign1.spaceCheck("valid option");
                    if (input.equals("\\m")) {
                        return false;// exit method
                    }
                    choice = (input).toLowerCase();

                    if (!choice.equals("s") && !choice.equals("c")) {
                        valid = false;// restart loop
                    }
                }
            }
            switch (choice) {
                case "s": {
                    accounts.add(new SavingsAccount());// create new saving account object
                    break;
                }
                case "c": {
                    accounts.add(new ChequingAccount());// create new chequing account object
                    break;
                }
            }
            if (accounts.get(accounts.size() - 1).addBankAccount()) {
                return true;
            } else {
                accounts.remove(accounts.size() - 1);
                switch (choice) {// customize error display
                    case "s": {
                        System.err.print("Saving");
                        break;
                    }
                    case "c": {
                        System.err.print("Chequing");
                        break;
                    }
                }
                System.err.println(" account adding operation interrupted");// return error if not
                return false;
            }
        } catch (Exception e) {
            System.err.println("Import error detected. Import disabled");// import file might be corrupted
            importDisabled = true;// disabling import
            sizeAfterRun = accounts.size();
            if (sizeAfterRun > sizeBeforeRun) {// if a new account has been added during erroneous run, delete that
                // account
                accounts.remove(sizeBeforeRun);
            }
            try {
                temp.clear();// delete all compromised import data
                System.out.println("Erroneous data deleted. Retaining data without error");
            } catch (NullPointerException nullPointerException) {
                System.out.println("Retaining data without error");
            }
            return false;
        }
    }

    /**
     * display information for a specific account
     *
     * @return account information or error message
     */
    String displayAccount() {
        int index;// index of account
        if (accounts.size() > 0) {// check if there is any account
            index = findAccount();// look for account from user input
            if (index == -1) {
                return "";// message when user chooses to exit method
            }
            return accounts.get(index).toString();
        } else {
            return "No account to display. Re-enter your choice: ";// message when there is no account
        }
    }

    /**
     * this method prompts user for account number and return the index of that
     * account in the array
     *
     * @return account index
     */
    private int findAccount() {
        if (accounts.size() == 1) {
            return 0;
        }
        String input;
        long accNo;// storing user input
        int index = -1;
        boolean validAccNum = false;// account number input validation controller
        System.out.println("Enter account number: ");
        while (!validAccNum) {// account number input validation
            validAccNum = true;
            try {
                input = Assign1.spaceCheck("account number");
                if (input.equals("\\m")) {
                    return -1;
                }
                accNo = Integer.parseInt(input);
                if (accNo < 0) {
                    System.out.println("Account number cannot be negative. Please re-enter account number: ");
                    validAccNum = false;
                    continue;
                }
                index = findAccount(accNo, 0);
                if ((index == -1) && accNo != 0) {
                    System.out.println("Could not find account. Please re-enter account number: ");
                    validAccNum = false;
                } else if (accNo == 0) {
                    System.out.println("Account number cannot be zero. Please re-enter account number: ");
                    validAccNum = false;
                }
            } catch (Exception e) {
                validAccNum = false;// restart loop
                System.out.println("Invalid input. Please re-enter account number: ");
            }
        }
        return index;
    }

    /**
     * monthly account update method
     */
    void monthlyUpdate() {
        if (accounts.size() > 0) {// check if there is account(s)
            for (BankAccount account : accounts) {// do monthly update on all accounts
                accounts.get(accounts.indexOf(account)).monthlyAccountUpdate();
            }
        } else {
            System.out.println("No account to update. Please re-enter your choice: ");// error message when there is no
            // account
        }
    }

    /**
     * print header and info of all accounts
     */
    void printAccountDetails() {
        openOutputFile();// open output file for editing
        if (accounts.size() > 0) {// check if there is account(s)
            System.out.println("\n\nBanking System");// header
            System.out.println("********************");// header
            System.out.println("Number of Account holders: " + accounts.size());// header
            for (BankAccount element : accounts) {
                System.out.println(element);
                if (element instanceof SavingsAccount)// output "S" or "C" corresponding to bank account type at the start of each line
                    output.printf("%s ", "S");
                else
                    output.printf("%s ", "C");
                output.printf("%s%n", element.toOutput());
            }
            closeOutputFile();
        } else {
            System.out.println("No account to display. Please re-enter your choice: ");// error message when there is no
            // account
        }
    }

    /**
     * prepare output file for writing
     */
    private void openOutputFile() {
        try {
            FileWriter writer = new FileWriter("bankoutput.txt");
            output = new PrintWriter(writer);
        } catch (IOException iOException) {
            System.err.println("Output file not found. Output onscreen only");
        } catch (SecurityException securityException) {
            System.err.println("Write permission denied. Output onscreen only");
        }
    }

    /**
     * close output file when finished outputting, also for when restart output from top of file
     */
    private void closeOutputFile() {
        if (output != null)
            output.close();
    }
}
