/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
 */

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.text.DecimalFormat;
import java.util.Optional;

/**
 * The purpose of this class is to store and give info related to all bank accounts
 *
 * @author Van Nam Doan
 * @version 1.2
 * @since 1.8
 */
public abstract class BankAccount {
    /**
     * object contains account holder information
     */
    Person accHolder;
    /**
     * account balance
     **/
    double balance;
    /**
     * account identification number
     */
    long accNumber;

    /**
     * place holder default constructor for BankAccount
     */
    BankAccount() {
    }

    /**
     * this method is to be evoked by its subclass methods with the same name
     *
     * @return concatenated string with account number and balance
     */
    public String toString() {
        DecimalFormat dfPrintBalance = new DecimalFormat("0.0##");// for formatting balance output
        return "Account number: " + accNumber + "  Name: " + accHolder.getFirstName() + " " + accHolder.getLastName() + "  Phone Number: " + accHolder.getPhoneNumber() + "  Email Address: " + accHolder.getEmailAddress() + "  Balance: " + dfPrintBalance.format(balance);// mutual output for all accounts
    }

    /**
     * file output formatting for all accounts
     *
     * @return account detail output formatting
     */
    public String toOutput() {
        DecimalFormat dfOutputBalance = new DecimalFormat("0.0#");// for formatting balance output
        return accNumber + " " + accHolder.getFirstName() + " " + accHolder.getLastName() + " " + accHolder.getPhoneNumber() + " " + accHolder.getEmailAddress() + " " + dfOutputBalance.format(balance);
    }

    /**
     * performs monthly balance update for all accounts
     */
    abstract String monthlyAccountUpdate();

    /**
     * add to balance by the parameterized amount
     *
     * @param amt amount to add to balance
     */
    void deposit(double amt) {
        balance += amt;
    }

    /**
     * deduct balance by the parameterized amount
     *
     * @param amt amount to deduct balance
     */
    void withdraw(double amt) {
        balance -= amt;
    }

    /**
     * import account number, first name, last name, phone number, email address and opening balance
     *
     * @return true if account added successfully
     */
    protected boolean addBankAccount() {
        String firstName = "";
        String lastName = "";
        String phoneNumber = "";
        String emailAddress = "";
        if (Bank.getTemp().size() != 0 || !Bank.isImportDisabled()) {
            if (InputFilter.validAccNum(Bank.getTemp().get(Bank.getCounter()))) {
                long accNumberTemp = Long.parseLong(Bank.getTemp().get(Bank.getCounter()));
                int indexTemp = Bank.findAccount(accNumberTemp, 0);// look for account number duplication
                if (indexTemp >= 0) {// if found
                    Bank.getImportLog().add("Account number found in database");
                    /* Create a confirmation alert window */
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Duplicate account number found in import data\nOverwrite account " + accNumberTemp + "?", ButtonType.OK, ButtonType.CANCEL);
                    alert.setTitle("System info");
                    alert.setHeaderText(null);
                    Optional<ButtonType> overwrite = alert.showAndWait();
                    if (overwrite.isPresent()) {
                        if (overwrite.get() == ButtonType.CANCEL) {
                            Bank.getImportLog().add("User chose to retain old data");
                            int limit = 0;
                            while ((!Bank.getTemp().get(Bank.getCounter()).equalsIgnoreCase("s") || !Bank.getTemp().get(Bank.getCounter()).equalsIgnoreCase("s")) &&
                                    limit < 10) {
                                Bank.setCounter(Bank.getCounter() + 1);
                                limit++;
                            }
                            Bank.setCounter(Bank.getCounter() - 2);// lining up the import
                            Bank.getAccounts().remove(Bank.getAccounts().size() - 1);
                            return false;// user chose CANCEL
                        }
                    }
                    Bank.getImportLog().add("Overwriting...");
                    Bank.getAccounts().remove(indexTemp);
                    Bank.getAccounts().trimToSize();
                }
                Bank.getImportLog().add("Account number: ");
                accNumber = accNumberTemp;
            } else {
                Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                Bank.disableImport();// disabling import
                return false;
            }
            Bank.getImportLog().add("[" + accNumber + "]");
            Bank.setCounter(Bank.getCounter() + 1);
        }
        Bank.getImportLog().add("First name of account holder: ");
        if (Bank.getTemp().size() != 0 || !Bank.isImportDisabled()) {// if there is data to import and if import is not disabled
            firstName = Bank.getTemp().get(Bank.getCounter());
            Bank.getImportLog().add("[" + Bank.getTemp().get(Bank.getCounter()) + "]");
            Bank.setCounter(Bank.getCounter() + 1);
        }
        Bank.getImportLog().add("Last name of account holder: ");
        if (Bank.getTemp().size() != 0 || !Bank.isImportDisabled()) {// if there is data to import and if import is not disabled
            lastName = Bank.getTemp().get(Bank.getCounter());
            Bank.getImportLog().add("[" + Bank.getTemp().get(Bank.getCounter()) + "]");
            Bank.setCounter(Bank.getCounter() + 1);
        }
        Bank.getImportLog().add("Phone number: ");
        if (Bank.getTemp().size() != 0 || !Bank.isImportDisabled()) {// if there is data to import and if import is not disabled
            phoneNumber = Bank.getTemp().get(Bank.getCounter());
            if (InputFilter.validPhoneNum(phoneNumber)) {
                Bank.getImportLog().add("[" + Bank.getTemp().get(Bank.getCounter()) + "]");
                Bank.setCounter(Bank.getCounter() + 1);
            } else {
                Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                Bank.disableImport();// disabling import
                return false;
            }
        }
        if (Bank.getTemp().size() != 0 || !Bank.isImportDisabled()) {// if there is data to import and if import is not disabled
            emailAddress = Bank.getTemp().get(Bank.getCounter());
            if (InputFilter.validEmailAddress(emailAddress)) {
                Bank.getImportLog().add("Email address: ");
                Bank.getImportLog().add("[" + Bank.getTemp().get(Bank.getCounter()) + "]");// print to simulate human input
                Bank.setCounter(Bank.getCounter() + 1);
            } else {
                Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                Bank.disableImport();// disabling import
                return false;
            }
        }
        accHolder = new Person(firstName, lastName, phoneNumber, emailAddress);// assigning accHolder object with info
        Bank.getImportLog().add("Opening balance: ");
        if (Bank.getTemp().size() != 0 || !Bank.isImportDisabled()) {// if there is data to import and if import is not disabled
            if (InputFilter.validBalance(Bank.getTemp().get(Bank.getCounter()))) {
                balance = Double.parseDouble(Bank.getTemp().get(Bank.getCounter()));
                Bank.getImportLog().add("[" + Bank.getTemp().get(Bank.getCounter()) + "]");// print to simulate human input
                Bank.setCounter(Bank.getCounter() + 1);
            } else {
                Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                Bank.disableImport();// disabling import
                return false;
            }
        }
        return true;
    }
}
