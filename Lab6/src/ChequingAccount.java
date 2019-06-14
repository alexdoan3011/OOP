/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 6
 * Date: Mar 13, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to initialize chequing accounts and to print or update said accounts
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since 1.8
 */
public class ChequingAccount extends BankAccount {
    /**
     * fee deducted every month (>=0)
     */
    private double fee;

    /**
     * create chequing account
     */
    public ChequingAccount() {
    }

    /**
     * this method deducts fee from a chequing account
     */
    @Override
    public void monthlyAccountUpdate() {
        if (balance >= fee) {
            balance -= fee;
        } else {
            System.out.println("Monthly update failed for account " + accNumber + ". Insufficient balance");
        }
    }// deduct balance by fee every month

    /**
     * this method is for printing a chequing account
     *
     * @return concatenated string with account number, balance and fee
     */
    @Override
    public String toString() {
        DecimalFormat dF = new DecimalFormat("0.0##");// for formatting fee
        return super.toString() + "  Fee: " + dF.format(fee);
    }

    /**
     * prompt user for monthly fee
     *
     * @return true if account added successfully false if not
     */
    public boolean addBankAccount() {
        if (!super.addBankAccount()) {
            return false;
        }
        String input = null;
        System.out.println("Enter monthly fee: ");
        boolean validMonthlyFee = false;// monthly fee input validation loop controller
        while (!validMonthlyFee) {
            validMonthlyFee = true;
            try {
                input = Assign1.spaceCheck("fee");
                if (input.equals("\\m")) {
                    return false;
                }
                fee = Double.parseDouble(input);// check if value is double
            } catch (Exception e) {
                System.out.println("Invalid input. Please re-enter fee: ");// error if not
                validMonthlyFee = false;
                continue;
            }
            if (fee < 0) {// check if monthly fee is negative
                System.out.println("Monthly fee cannot be negative. Please re-enter monthly fee: ");
                validMonthlyFee = false;
                continue;
            }
        }
        return true;
    }
}
