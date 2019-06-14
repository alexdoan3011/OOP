/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 6
 * Date: Mar 13, 2019
 */

import java.util.ArrayList;

/**
 * the purpose of this class is to manipulate bank accounts, including: displaying single, displaying all, updating by amount, monthly updating, adding new account
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since 1.8
 */
public class Bank {
    /**
     * arrayList contains all bank accounts
     */
    private static ArrayList<BankAccount> accounts = new ArrayList<>();

    /**
     * return the index of an account in the account array given its account number
     *
     * @param accNo account number of the account to get index of. Second parameter indicates whether a new account is being added or not
     * @return
     */
    public static int findAccount(int... accNo) {
        int index = -1;
        ArrayList<Integer> usedAccNum = new ArrayList<>();// creating array storing all account numbers
        for (int i = 0; i < accounts.size() - accNo[1]; i++) {// initializing usedAccNum
            usedAccNum.add(accounts.get(i).accNumber);
        }
        for (int i = 0; i < usedAccNum.size(); i++) {// check if user input exist in usedAccNum array
            int unwrap = usedAccNum.get(i);// unwrapping Integer account number to int
            if (accNo[0] == unwrap) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * invoke updateBalance after validating input. Rollback update when overdrawn is detected, throw error
     *
     * @return message before exiting to main menu
     */
    public String updateAccount() {
        String input = null;
        String message = "";// contains the return message
        if (accounts.size() > 0) {// check if there is any account to update
            int index = findAccount();// ask user for account number to update
            if (index == -1) {// check if user chooses to exit during account lookup
                return "";// exit the method, return to selection screen
            }
            boolean valid = false;// overdrawn loop controller
            double amt = 0;// amount of balance update to account
            while (!valid) {// overdrawn loop. Loop when overdrawn
                valid = true;
                System.out.println("Enter amount to deposit/withdraw (positive number to deposit, negative number to withdraw): ");
                boolean validInput = false;// input validation controller
                while (!validInput) {// input validation loop. Loop when invalid input
                    validInput = true;
                    try {
                        input = Assign1.spaceCheck("amount to deposit/withdraw");
                        if (input.equals("\\m")) {
                            return "";
                        }
                        amt = Double.parseDouble(input);
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please re-enter amount to deposit/withdraw: ");
                        validInput = false;// restart the input validation loop
                    }
                }
                accounts.get(index).updateBalance(amt);// invoke updateBalance method for account with amount entered
                if (accounts.get(index).balance < 0) {// if overdrawn, rollback update, throw error, restart loop
                    accounts.get(index).updateBalance(-amt);// rollback update
                    System.out.println("Insufficient fund. Account balance is " + accounts.get(index).balance);
                    valid = false;// restart the overdrawn loop
                }
            }
        } else {
            message = "No account to update. Re-enter your choice: ";// error thrown when there is no account to update. Exit the method, go back to main menu
        }
        return message;
    }

    /**
     * for adding new account. Check if after adding account, number of accounts does not exceed maxSize. Prompt user for account type, check input.
     * Increase numAccount by 1 if add successfully.
     *
     * @return true if new account added successfully, false otherwise
     */
    public boolean addAccount() {
        String input = null;
        System.out.println("Enter details of account holder " + (accounts.size() + 1));
        System.out.println("=================================");
        System.out.println("Enter account type (s for savings, c for chequing): ");
        boolean valid = false;// input validation loop controller
        String choice = "";// contain user selection for account adding type. s for saving, c for chequing
        while (!valid) {
            valid = true;
            if (!choice.isEmpty()) {// check if choice has been inputted before
                System.out.println("Invalid input. Please re-enter valid option: ");// only display on the second loop forward
            }
            input = Assign1.spaceCheck("valid option");
            if (input.equals("\\m")) {
                return false;
            }
            choice = (input).toLowerCase();

            if (!choice.equals("s") && !choice.equals("c")) {
                valid = false;// restart loop
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
            System.err.println("Account not added");// return error if not
            return false;
        }
    }

    /**
     * display information for a specific account
     *
     * @return account information or error message
     */
    public String displayAccount() {
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
     * this method prompts user for account number and return the index of that account in the array
     *
     * @return account index
     */
    public int findAccount() {
        String input;
        int accNo;// storing user input
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
                System.out.println("Invalid input. Please re-enter account number: ");
            }
        }
        return index;
    }

    /**
     * monthly account update method
     */
    public void monthlyUpdate() {
        if (accounts.size() > 0) {// check if there is account(s)
            for (int i = 0; i < accounts.size(); i++) {// do monthly update on all accounts
                accounts.get(i).monthlyAccountUpdate();
            }
        } else {
            System.out.println("No account to update. Please re-enter your choice: ");// error message when there is no account
        }
    }

    /**
     * print header and info of all accounts
     */
    public void printAccountDetails() {
        if (accounts.size() > 0) {// check if there is account(s)
            System.out.println("\n\nBanking System");// header
            System.out.println("********************");// header
            System.out.println("Number of Account holders: " + accounts.size());// header
            for (int i = 0; i < accounts.size(); i++)
                System.out.println(accounts.get(i).toString());// print info of all accounts
        } else {
            System.out.println("No account to display. Please re-enter your choice: ");// error message when there is no account
        }
    }
}
