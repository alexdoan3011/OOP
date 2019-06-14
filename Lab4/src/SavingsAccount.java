/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 4
 * Date: Feb 3, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to initialize saving accounts and to print or update said accounts
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since JDK 1.8
 */
public class SavingsAccount extends BankAccount {
    /**
     * annual interest rate
     */
    private double interestRate;

    /**
     * create saving account
     *
     * @param interestRate annual interest rate
     * @param balance      account balance
     */
    public SavingsAccount(double interestRate, double balance) {
        this.balance = balance;
        this.interestRate = interestRate;
    }

    /**
     * this method updates saving account balance with interest added
     */
    @Override
    public void calculateAndUpdateBalance() {
        double interestMonth = interestRate / 12;
        balance = balance + balance * interestMonth;
    }

    /**
     * this method is for printing a saving account
     *
     * @return concatenated string with account number, balance and interest rate
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.000");
        return "Saving account:\t\t#" + super.toString() + "\t  Interest rate: " + df.format(interestRate);
    }
}
