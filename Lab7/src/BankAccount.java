/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 7
 * Date: Mar 19, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to store and give info related to all bank accounts
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since 1.8
 */
public abstract class BankAccount {
    /**
     * automatic account number assignment for importing
     */
    private static long autoAccNumber = 1;
    /**
     * account balance
     **/
    double balance;
    /**
     * account identification number
     */
    long accNumber;
    /**
     * object contains account holder information
     */
    private Person accHolder;

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
        DecimalFormat dF = new DecimalFormat("0.0##");// for formatting balance output
        return "AccountNumber: " + accNumber + "  Name: " + accHolder.getFirstName() + " " + accHolder.getLastName() + "  Phone Number: " + accHolder.getPhoneNumber() + "  Email Address: " + accHolder.getEmailAddress() + "  Balance: " + dF.format(balance);// mutual output for all accounts
    }

    /**
     * file output formatting for all accounts
     *
     * @return account detail output formatting
     */
    public String toOutput() {
        DecimalFormat dF = new DecimalFormat("0.0#");// for formatting balance output
        return accHolder.getFirstName() + " " + accHolder.getLastName() + " " + accHolder.getPhoneNumber() + " " + accHolder.getEmailAddress() + " " + dF.format(balance);
    }

    /**
     * performs monthly balance update for all accounts
     */
    abstract void monthlyAccountUpdate();

    /**
     * add to balance by the parameterized amount
     *
     * @param amt amount to add to balance
     */
    void deposit(double amt) throws IllegalArgumentException {
        if (amt < 0) {// throw exception for negative amount
            throw new TransactionIllegalArgumentException(accNumber, "d", amt, balance);
        }
        balance += amt;
    }

    /**
     * deduct balance by the parameterized amount
     *
     * @param amt amount to deduct balance
     */
    void withdraw(double amt) throws IllegalArgumentException {
        if (amt > balance) {// throw exception for insufficient balance
            throw new TransactionIllegalArgumentException(accNumber, "w", amt, balance);
        }
        if (amt < 0) {// throw exception for negative amount
            throw new TransactionIllegalArgumentException(accNumber, "w", amt, balance);
        }
        balance -= amt;
    }

    /**
     * prompt user to input account number, first name, last name, phone number, email address and opening balance. Validate all inputs.
     *
     * @return true if account added successfully false if not
     */
    protected boolean addBankAccount() {
        String input;
        System.out.println("Enter account number: ");
        if (Bank.temp.size() != 0 || Bank.importDisabled) {
            System.out.println("Generating unique incremented account number");
            while (Bank.findAccount(autoAccNumber, 0) != -1) {// if account number in use, increment autoAccNumber by 1
                autoAccNumber++;
            }
            accNumber = autoAccNumber;
            System.out.println("[" + accNumber + "]");
        } else {
            boolean validAccNum = false;// account number input validation loop controller
            while (!validAccNum) {
                validAccNum = true;
                try {
                    input = Assign1.spaceCheck("account number");
                    if (input.equals("\\m")) {
                        return false;
                    }
                    accNumber = Integer.parseInt(input);// check if value is integer
                } catch (Exception e) {
                    System.out.println("Invalid input. Please re-enter account number: ");// error if not
                    validAccNum = false;
                    continue;
                }
                if (accNumber < 0) {// check so that value for account number is not negative
                    System.out.println("Account number cannot be negative. Please re-enter account number: ");
                    validAccNum = false;
                    continue;
                }
                if (accNumber == 0) {// check so that value for account number is not zero
                    System.out.println("Account number cannot be zero. Please re-enter account number: ");
                    validAccNum = false;
                    continue;
                }
                if (Long.toString(accNumber).length() > 8) {// check so that value for account number is not zero
                    System.out.println("Account number cannot have more than 8 digits. Please re-enter account number: ");
                    validAccNum = false;
                    continue;
                }
                if (Bank.findAccount(accNumber, 1) != -1) {// check so that value of account number is not equal to any of account number stored
                    System.out.println("Account number already exists. Please re-enter account number: ");
                    validAccNum = false;
                }
            }
        }
        String firstName;
        String lastName;
        String phoneNumber;
        String emailAddress = "";
        System.out.println("Enter first name of account holder: ");
        if (Bank.temp.size() != 0 || Bank.importDisabled) {// if there is data to import and if import is not disabled
            firstName = Bank.temp.get(Bank.counter);
            System.out.println("[" + Bank.temp.get(Bank.counter) + "]");
            Bank.counter++;
        } else {
            firstName = input = Assign1.spaceCheck("first name");
            if (input.equals("\\m")) {
                return false;
            }
        }
        System.out.println("Enter last name of account holder: ");
        if (Bank.temp.size() != 0 || Bank.importDisabled) {// if there is data to import and if import is not disabled
            lastName = Bank.temp.get(Bank.counter);
            System.out.println("[" + Bank.temp.get(Bank.counter) + "]");
            Bank.counter++;
        } else {
            lastName = input = Assign1.spaceCheck("last name");
            if (input.equals("\\m")) {
                return false;
            }
        }
        System.out.println("Enter phone number: ");
        if (Bank.temp.size() != 0 || Bank.importDisabled) {// if there is data to import and if import is not disabled
            phoneNumber = Bank.temp.get(Bank.counter);
            System.out.println("[" + Bank.temp.get(Bank.counter) + "]");
            Bank.counter++;
        } else {
            boolean validPhoneNum = false;// phone number input validation loop controller
            String tempString = input = Assign1.spaceCheck("phone number");// this string temporarily stores phone number input for checking
            if (input.equals("\\m")) {
                return false;
            }
            while (!validPhoneNum) {
                validPhoneNum = true;
                for (char c : tempString.toCharArray()) {// checking if all characters in the temporary string are digits
                    if (!Character.isDigit(c)) {
                        System.out.println("Enter numbers only: ");
                        tempString = input = Assign1.spaceCheck("phone number");
                        if (input.equals("\\m")) {
                            return false;
                        }
                        validPhoneNum = false;
                        break;
                    }
                }
            }
            phoneNumber = tempString;
        }
        if (Bank.temp.size() != 0 || Bank.importDisabled) {// if there is data to import and if import is not disabled
            emailAddress = Bank.temp.get(Bank.counter);
            System.out.println("Enter email address: ");
            System.out.println("[" + Bank.temp.get(Bank.counter) + "]");// print to simulate human input
            Bank.counter++;
        } else {
            boolean at;
            boolean dot;
            do {
                if (emailAddress.isEmpty())
                    System.out.println("Enter email address: ");
                else
                    System.out.println("Enter valid email address: ");
                emailAddress = input = Assign1.spaceCheck("email address");
                if (input.equals("\\m")) {
                    return false;
                }
                at = false;
                dot = false;
                int pos = 0;
                for (int j = 1; j < emailAddress.length(); j++) {// checking if email entered with @ in front of .
                    if (emailAddress.charAt(j) == '@') {// check for @
                        at = true;
                        pos = j;
                        break;
                    }
                }
                for (int j = (pos + 2); j < emailAddress.length() - 1; j++) {// check for . from position of @ plus 2 to before the last character
                    if (at && emailAddress.charAt(j) == '.') {
                        dot = true;
                        break;
                    }
                }
            } while (!(at && dot));
        }
        accHolder = new Person(firstName, lastName, phoneNumber, emailAddress);// assigning accHolder object with info
        System.out.println("Enter opening balance: ");
        if (Bank.temp.size() != 0 || Bank.importDisabled) {// if there is data to import and if import is not disabled
            balance = Double.parseDouble(Bank.temp.get(Bank.counter));
            System.out.println("[" + Bank.temp.get(Bank.counter) + "]");// print to simulate human input
            Bank.counter++;
        } else {
            boolean validOpeningBalance = false;// opening balance input controller
            while (!validOpeningBalance) {
                validOpeningBalance = true;
                try {
                    input = Assign1.spaceCheck("opening balance");
                    if (input.equals("\\m")) {
                        return false;
                    }
                    balance = Double.parseDouble(input);
                    if (balance < 0) {
                        System.out.println("Balance cannot be negative. Please re-enter opening balance: ");
                        validOpeningBalance = false;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please re-enter opening balance: ");
                    validOpeningBalance = false;
                }
            }
        }
        return true;
    }
}
