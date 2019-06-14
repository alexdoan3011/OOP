/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 4
 * Date: Feb 3, 2019
 */

import java.text.DecimalFormat;
import java.util.Random;

/**
 * The purpose of this class is to store and give info related to all bank accounts
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since JDK 1.8
 */
public abstract class BankAccount {
    /**
     * The number of accounts created
     **/
    protected static int numAccounts;
    /**
     * account balance
     **/
    protected double balance;
    /**
     * account identification number
     */
    protected long accountNo;

    public BankAccount() {
        numAccounts += 1;// keep track of how many accounts have been created
        Random Random = new Random();
        while (this.accountNo == 0)// randomly generating account number, not letting value = 0
            this.accountNo = Random.nextLong();
        if (this.accountNo < 0)// if account number is <0, make it >0
            this.accountNo = -1 * this.accountNo;
    }

    /**
     * this method returns account balance
     *
     * @return account balance
     */
    public double getBalance() {// redundant, never used
        return balance;
    }

    /**
     * this method is to be evoked by its subclass methods with the same name
     *
     * @return concatenated string with account number and balance
     */
    public String toString() {
        DecimalFormat dfBalance = new DecimalFormat("0.000");// for formatting balance output
        DecimalFormat dfAccNum = new DecimalFormat("0000000000000000000");// for formatting account number output
        String s = dfAccNum.format(accountNo) + "\t  Balance: " + dfBalance.format(balance);// mutual output for all accounts
        return s;
    }

    /**
     * performs monthly balance update for all accounts
     */
    abstract void calculateAndUpdateBalance();
}
