/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 4
 * Date: Feb 3, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to initialize chequing accounts and to print or update said accounts
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since JDK 1.8
 */
public class ChequingAccount extends BankAccount {
    /**
     * fee deducted every month
     */
    private double fee;

    /**
     * create chequing account
     *
     * @param fee     fee deducted every month
     * @param balance account balance
     */
    public ChequingAccount(double fee, double balance) {
        this.balance = balance;
        this.fee = fee;
    }

    /**
     * this method deducts fee from a chequing account
     */
    @Override
    public void calculateAndUpdateBalance() {
        balance -= fee;
    }

    /**
     * this method is for printing a chequing account
     *
     * @return concatenated string with account number, balance and fee
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.000");
        return "Chequing account:\t#" + super.toString() + "\t  Fee: " + df.format(fee);
    }
}
