/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
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
 * @version 1.2
 * @since 1.8
 */
public class Bank {
    /**
     * this temporary ArrayList object stores all import data
     */
    private static ArrayList<String> temp;
    /**
     * this counter points to the current import data being imported
     */
    private static int counter = 0;
    /**
     * this boolean disable import function when it is true
     */
    private static boolean importDisabled = false;
    /**
     * arrayList contains all bank accounts
     */
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    /**
     * arrayList of strings contains log messages
     */
    private static ArrayList<String> importLog = new ArrayList<>();
    /**
     * this formatter writes output to bankoutput.txt file
     */
    private static PrintWriter output;
    /**
     * this scanner object reads from bankinput.txt file
     */
    private static Scanner input;
    /**
     * detected path for import file
     */
    private static String inputPath;

    /**
     * whether import is disabled or not
     *
     * @return import disabled?
     */
    static boolean isImportDisabled() {
        return importDisabled;
    }

    /**
     * disable import
     */
    static void disableImport() {
        Bank.importDisabled = true;
    }

    /**
     * return the index of an account in the account array given its account number
     *
     * @param accNo account number of the account to get index of. Second parameter
     *              indicates whether a new account is being added or not
     * @return index of account with account number equal to first element of
     * parameter array
     */
    static int findAccount(long... accNo) {// second element of array equal 0 if program is not in process of adding
        /* account, 1 if it is */
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
    private static void openInputFile() {
        String pathAbs;// temporary absolute path
        String pathAlt;// temporary alternate path
        String pathRev;// temporary relative path
        Path pathAbsolute = Paths.get(pathAbs = new File("bankData.txt").getAbsolutePath());// use absolute path of bankData.txt
        Path pathAbsoluteAlt = Paths.get(pathAlt = new File("Lab9\\bankData.txt").getAbsolutePath());// use alternative absolute
        /* path of bankData.txt for intellij */
        Path pathRelative = Paths.get(pathRev = "bankData.txt");// use relative path of bankData.txt
        try {
            if (Files.exists(pathAbsolute)) {// try looking all 3 different paths for the input file
                input = new Scanner(pathAbsolute);
                inputPath = pathAbs;
            } else if (Files.exists(pathRelative)) {
                input = new Scanner(pathRelative);
                inputPath = pathRev;
            } else if (Files.exists(pathAbsoluteAlt)) {
                input = new Scanner(pathAbsoluteAlt);
                inputPath = pathAlt;
            } else {// throws error when file not found
                importLog.add("Import data not found. Import disabled");
                importDisabled = true;// disable import if can't find file
            }
        } catch (IOException ioException) {
            importLog.add("Error opening import data file. Import disabled");
            importDisabled = true;// disable import if can't read file
        }
    }

    /**
     * this method reads the import data file and records the data in temp. Call
     * closeInputFile() when done. Delete all data in temp when data is bad
     */
    private static void readRecords() {
        if (!importDisabled) {
            temp = new ArrayList<>();
            try {
                try {
                    while (input.hasNext()) {
                        temp.add(input.next());// add all elements of the input file, separated by white space, into the temp arrayList
                    }
                } catch (NoSuchElementException noSuchElementException) {// check for file reading error
                    importLog.add("Import data file corrupted. Import disabled");
                    importDisabled = true;// disable import if can't read file
                    temp.clear();// remove all bad data in temp array
                } catch (IllegalStateException illegalStateException) {
                    importLog.add("Error opening import data file. Import disabled");
                    importDisabled = true;// disable import if can't read file
                    temp.clear();// remove all bad data in temp array
                }
            } catch (NullPointerException nullPointerException) {
                importLog.add("Error opening import data file. Import disabled");
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
     * monthly account update method
     */
    static ArrayList<String> monthlyUpdate() {
        ArrayList<String> monthlyUpdateLog = new ArrayList<>();
        if (accounts.size() > 0) {// check if there is account(s)
            for (BankAccount account : accounts) {// do monthly update on all accounts
                monthlyUpdateLog.add(accounts.get(accounts.indexOf(account)).monthlyAccountUpdate());
            }
        } else {
            monthlyUpdateLog.add("No account to update"); // print when there is no account
        }
        return monthlyUpdateLog;
    }

    /**
     * print header and info of all accounts
     */
    static void printAccountDetails(boolean writeToInput) {
        openOutputFile(writeToInput);// open output file for editing
        if (accounts.size() > 0) {// check if there is account(s)
            for (BankAccount element : accounts) {
                if (element instanceof SavingsAccount)// output "S" or "C" corresponding to bank account type at the start of each line
                    output.printf("%s ", "s");
                else
                    output.printf("%s ", "c");
                output.printf("%s%n", element.toOutput());
            }
            closeOutputFile();
        }
    }

    /**
     * import data from file
     */
    static void importData(boolean importAll) {
        if (!importDisabled) {
            openInputFile();// read import file every time main menu runs
            readRecords();
        }
        if (!importDisabled) {
            if (temp.size() > 0) {// if there is data to import
                if (!(counter < temp.size())) {// check if data has already been imported
                    temp.clear();// remove all import data for this run
                    importLog.add("No data to import");
                    importDisabled = true;
                } else {
                    if (importAll) {
                        importLog.add("Importing all data");
                        while (counter < temp.size()) {
                            addAccount();// import account
                        }

                    } else {
                        importLog.add("Importing one data entry");
                        addAccount();// import account
                    }
                }
            }
        }
    }

    /**
     * for adding new account. Check if after adding account, number of accounts
     * does not exceed maxSize. Prompt user for account type, check input. Increase
     * numAccount by 1 if add successfully.
     */
    private static void addAccount() {
        if (temp == null) {
            temp = new ArrayList<>();
        }
        int sizeBeforeRun = accounts.size();// record the size of account array before running the method
        int sizeAfterRun;// record the size of account array after running the method
        if (importDisabled) {
            counter = temp.size();
        } else {
            try {// catching any import error
                importLog.add("Account holder " + (accounts.size() + 1));
                importLog.add("***");
                importLog.add("Account type: ");
                String choice = "";// contain user selection for account adding type. s for saving, c for chequing
                if (temp.size() != 0 && !importDisabled) {
                    choice = temp.get(counter).toLowerCase();
                    if (choice.equals("s")) {
                        importLog.add("[Saving]");
                    } else if (choice.equals("c")) {
                        importLog.add("[Chequing]");
                    } else {
                        importLog.add("Import error detected. Import disabled");// import file might be corrupted
                        importDisabled = true;// disabling import
                    }
                    counter++;
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
                    importLog.add("Import successful");
                    importLog.add("_____________________________________________________________________");
                } else {
                    importLog.add("Import interrupted");
                    importLog.add("_____________________________________________________________________");
                }
            } catch (Exception e) {
                importLog.add("Import error detected. Import disabled");// import file might be corrupted
                importDisabled = true;// disabling import
            }
        }
        sizeAfterRun = accounts.size();
        if (importDisabled && sizeAfterRun > sizeBeforeRun) {// if a new account has been added during erroneous run, delete that
            // account
            accounts.remove(sizeBeforeRun);
            temp.clear();// delete all compromised import data
            importLog.add("Erroneous data deleted. Retaining data without error");
        }
    }

    /**
     * prepare output file for writing
     */
    private static void openOutputFile(boolean writeToInput) {
        try {
            FileWriter writer;
            if (writeToInput) {
                writer = new FileWriter(inputPath);
            } else {
                writer = new FileWriter("bankoutput.txt");
            }
            output = new PrintWriter(writer);
        } catch (IOException iOException) {
            Controller.popUpError("Output file not found. Output onscreen only");
        } catch (SecurityException securityException) {
            Controller.popUpError("Write permission denied. Output onscreen only");
        }
    }

    /**
     * close output file when finished outputting, also for when restart output from top of file
     */
    private static void closeOutputFile() {
        if (output != null)
            output.close();
    }

    /**
     * get account arrayList stored in Bank class
     *
     * @return account arrayList
     */
    static ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    /**
     * get import log arrayList stored in Bank class
     *
     * @return import log arrayList
     */
    static ArrayList<String> getImportLog() {
        return importLog;
    }

    /**
     * get temp arrayList stored in Bank class
     *
     * @return temp arrayList
     */
    static ArrayList<String> getTemp() {
        return temp;
    }

    /**
     * get counter stored in Bank class
     *
     * @return counter
     */
    static int getCounter() {
        return counter;
    }

    /**
     * set counter stored in Bank class
     *
     * @param counter counter
     */
    static void setCounter(int counter) {
        Bank.counter = counter;
    }
}

