/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 5
 * Date: Feb 26, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to initialize saving accounts and to print or update said accounts
 *
 * @author Van Nam Doan
 * @version 1.0
 * @since 1.8
 */
public class SavingsAccount extends BankAccount {
    /**
     * annual interest rate (0,1)
     */
    private double interestRate;
    /**
     * minimum balance for saving account (>=0)
     */
    private double minimumBalance;

    /**
     * create saving account
     */
    public SavingsAccount() {
    }

    /**
     * this method updates saving account balance with interest added
     */
    @Override
    public void monthlyAccountUpdate() {
        if (balance > minimumBalance) {
            double interestMonth = interestRate / 12;// calculate interest rate per month from annual interest rate
            balance = balance + balance * interestMonth;// add monthly interest into balance
        } else {
            System.out.println("Monthly update failed for account " + accNumber + ". Insufficient balance");
        }
    }

    /**
     * this method is for printing a saving account
     *
     * @return concatenated string with account number, balance and interest rate
     */
    @Override
    public String toString() {
        DecimalFormat dF = new DecimalFormat("0.0##");// for formatting minimum balance and interest rate
        return super.toString() + "  Minimum Balance: " + dF.format(minimumBalance) + "  Interest Rate: " + dF.format(interestRate);
    }

    /**
     * prompt user for minimum balance and interest rate
     *
     * @return true if account added successfully false if not
     */
    public boolean addBankAccount() {
        if (!super.addBankAccount()) {
            return false;
        }
        String input = null;
        System.out.println("Enter minimum balance: ");
        boolean validMinimumBalance = false;// minimum balance input validation loop controller
        while (!validMinimumBalance) {
            validMinimumBalance = true;
            try {
                input = Assign1.spaceCheck("minimum balance");
                if (input.equals("\\m")) {
                    return false;
                }
                minimumBalance = Double.parseDouble(input);// check if value is double
            } catch (Exception e) {
                System.out.println("Invalid input. Please re-enter minimum balance: ");// error if not
                validMinimumBalance = false;
                continue;
            }
            if (minimumBalance < 0) {// check if minimum balance is negative
                System.out.println("Minimum balance cannot be negative. Please re-enter minimum balance: ");
                validMinimumBalance = false;
                continue;
            }
        }
        System.out.println("Enter interest rate (should be a number in (0,1)): ");
        boolean validInterestRate = false;// interest rate input validity check loop controller
        while (!validInterestRate) {
            validInterestRate = true;
            try {
                input = Assign1.spaceCheck("interest rate");
                if (input.equals("\\m")) {
                    return false;
                }
                interestRate = Double.parseDouble(input);// check if value is integer
            } catch (Exception e) {
                System.out.println("Invalid input. Please re-enter interest rate: ");// error if not
                validInterestRate = false;
                continue;
            }
            if (interestRate >= 1 || interestRate <= 0) {// check if input is in the range of (0,1)
                System.out.println("Interest rate should be a number in (0,1). Please re-enter interest rate: ");
                validInterestRate = false;
            }
        }
        return true;
    }
}
